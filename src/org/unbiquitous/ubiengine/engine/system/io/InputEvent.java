package org.unbiquitous.ubiengine.engine.system.io;

import org.unbiquitous.ubiengine.util.observer.Event;

/**
 * Base class for input events.
 * @author Pimenta
 *
 */
class InputEvent extends Event {
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
