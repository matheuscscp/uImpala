package org.unbiquitous.uImpala.engine.io;

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
    
    updating = false;
    this.driver = driver;
  }
  
  protected void update() {
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
    updating = false;
    observations.broadcast(EVENT_STOPPED_UPDATING, null);
  }
  
  public boolean isUpdating() {
    return updating;
  }
  
  public boolean getKey(int k) {
    return keys[k];
  }
  
  /**
   * Engine's private use.
   */
  public void add(KeyboardEvent event) {
    events.add(event);
  }
  
  private boolean[] keys;
  
  protected boolean updating;
  protected DriverData driver;
  
  public void handleEvent(Notify event) {
    int key = ((Integer)event.getParameter("unicodeChar")).intValue();
    events.add(new KeyboardEvent(
      event.getEventKey().equals("EVENT_KEY_DOWN") ? EVENT_KEY_DOWN : EVENT_KEY_UP,
      key,
      (char)key
    ));
  }
}
