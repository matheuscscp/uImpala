package org.unbiquitous.ubiengine.engine.system.io;

/**
 * Base class for input events.
 * @author Pimenta
 *
 */
public abstract class InputEvent {
  /**
   * Assignment constructor.
   * @param t Event type.
   */
  public InputEvent(int t) {
    type = t;
  }
  
  /**
   * Access method to event type.
   * @return Event type.
   */
  public int type() {
    return type;
  }
  
  private int type;
}
