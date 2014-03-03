package org.unbiquitous.ubiengine.engine.system.io;

public final class KeyboardSource extends InputResource {
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
  protected KeyboardSource(int ks) {
    observations.addEvents(EVENT_KEY_DOWN, EVENT_KEY_UP);
    keys = new boolean[ks];
    for (int i = 0; i < ks; i++)
      keys[i] = false;
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
    
  }
  
  public boolean isUpdating() {
    return updating;
  }
  
  public boolean getKey(int k) {
    return keys[k];
  }
  
  protected boolean updating = false;
  private boolean[] keys;
}
