package org.unbiquitous.ubiengine.engine.io;

import org.unbiquitous.ubiengine.util.observer.Event;

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
