package org.unbiquitous.uImpala.engine.io;

import org.unbiquitous.uImpala.util.observer.Event;

/**
 * Base class for input events.
 * @author Pimenta
 *
 */
class InputEvent extends Event {
  /**
   * Event type.
   */
  protected int type;
  
  /**
   * Assignment constructor.
   * @param t Event type.
   */
  public InputEvent(int t) {
    type = t;
  }
}
