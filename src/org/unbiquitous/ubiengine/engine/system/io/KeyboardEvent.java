package org.unbiquitous.ubiengine.engine.system.io;

/**
 * Class to hold data about keyboard events.
 * @author Pimenta
 *
 */
public final class KeyboardEvent extends InputEvent {
  /**
   * Assignment constructor.
   * @param t Event type.
   */
  public KeyboardEvent(int t, int k) {
    super(t);
    key = k;
  }
  
  public int getKey() {
    return key;
  }
  
  protected int key;
}
