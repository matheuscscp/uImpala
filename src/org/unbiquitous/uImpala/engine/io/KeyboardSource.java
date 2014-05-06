package org.unbiquitous.uImpala.engine.io;

import org.unbiquitous.uImpala.engine.core.GameComponents;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.adaptabitilyEngine.NotifyException;
import org.unbiquitous.uos.core.adaptabitilyEngine.UosEventListener;
import org.unbiquitous.uos.core.driverManager.DriverData;
import org.unbiquitous.uos.core.messageEngine.messages.Notify;

public class KeyboardSource extends InputResource implements UosEventListener {
  /**
   * Broadcasted when a key is pressed.
   */
  public static final int EVENT_KEY_DOWN = IOResource.LAST_EVENT + 1;
  
  /**
   * Broadcasted when a key is released.
   */
  public static final int EVENT_KEY_UP   = IOResource.LAST_EVENT + 2;
  
  /**
   * The last event of this class.
   */
  public static final int LAST_EVENT     = EVENT_KEY_UP;
  
  /**
   * Constructor to allocate an array of flags, for keys.
   * Also setup events.
   * @param ks Amount of keys.
   */
  public KeyboardSource(int ks, DriverData driver) {
    observations.addEvents(EVENT_KEY_DOWN, EVENT_KEY_UP);
    keys = new boolean[ks];
    for (int i = 0; i < ks; i++)
      keys[i] = false;
    
    dead = false;
    updating = driver == null;
    this.driver = driver;
    registerThread = null;
    unregisterThread = null;
    
    gateway = GameComponents.get(Gateway.class);
  }
  
  public void start() {
    if (driver == null || registerThread != null || dead)
      return;
    
    final UosEventListener listener = this;
    registerThread = new Thread(new Runnable() {
      public void run() {
        try {
          gateway.register(
            listener,
            driver.getDevice(),
            driver.getDriver().getName(),
            driver.getInstanceID(),
            "EVENT_KEY_DOWN",
            null
          );
          gateway.register(
            listener,
            driver.getDevice(),
            driver.getDriver().getName(),
            driver.getInstanceID(),
            "EVENT_KEY_UP",
            null
          );
          synchronized (dead) {
            synchronized (updating) {
              if (!dead) {
                updating = true;
                observations.broadcast(EVENT_STARTED_UPDATING);
              }
            }
          }
        } catch (NotifyException e1) {
        }
        registerThread = null;
      }
    });
    
    // wait if unregister is in progress
    if (unregisterThread != null) {
      try {
        unregisterThread.join();
      } catch (InterruptedException e) {
      }
    }
    
    registerThread.start();
  }
  
  public void stop() {
    if (driver == null || unregisterThread != null || dead)
      return;
    
    final UosEventListener listener = this;
    unregisterThread = new Thread(new Runnable() {
      public void run() {
        try {
          gateway.unregister(listener);
        } catch (NotifyException e1) {
        }
        unregisterThread = null;
      }
    });
    
    // wait if register is in progress
    if (registerThread != null) {
      try {
        registerThread.join();
      } catch (InterruptedException e) {
      }
    }
    
    updating = false;
    observations.broadcast(EVENT_STOPPED_UPDATING);
    
    unregisterThread.start();
  }
  
  protected void update() {
    if (!updating)
      return;
    
    while (events.size() > 0) {
      KeyboardEvent event = (KeyboardEvent)events.poll();
      switch (event.type) {
        case EVENT_KEY_DOWN:
          keys[event.getKey()] = true;
          observations.broadcast(EVENT_KEY_DOWN, event);
          break;
          
        case EVENT_KEY_UP:
          keys[event.getKey()] = false;
          observations.broadcast(EVENT_KEY_UP, event);
          break;
          
        default:
          throw new Error("Invalid keyboard event");
      }
    }
  }
  
  public void close() {
    
  }
  
  public boolean isUpdating() {
    return updating && !dead;
  }
  
  public boolean getKey(int k) {
    return keys[k];
  }
  
  /**
   * Engine's private use.
   */
  public void handleEvent(Notify event) {
    int key = ((Integer)event.getParameter("unicodeChar")).intValue();
    events.add(new KeyboardEvent(
      event.getEventKey().equals("EVENT_KEY_DOWN") ? EVENT_KEY_DOWN : EVENT_KEY_UP,
      key,
      (char)key
    ));
  }
  
  /**
   * Engine's private use.
   */
  public void add(KeyboardEvent event) {
    events.add(event);
  }
  
  /**
   * Engine's private use.
   */
  protected void kill() {
    synchronized (dead) {
      synchronized (updating) {
        dead = true;
        updating = false;
      }
    }
    observations.broadcast(EVENT_STOPPED_UPDATING);
  }
  
  /**
   * Engine's private use.
   */
  protected String id() {
    if (driver == null)
      return null;
    return driver.getDevice().getName() + driver.getInstanceID();
  }
  
  private Gateway gateway;
  private boolean[] keys;
  private Boolean updating, dead;
  private DriverData driver;
  private Thread registerThread, unregisterThread;
}
