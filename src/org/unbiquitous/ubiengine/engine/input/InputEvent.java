package org.unbiquitous.ubiengine.engine.input;

/**
 * Base class for input events
 * @author Pimenta
 *
 */
public abstract class InputEvent {
  public InputEvent(int t) {
    type = t;
  }
  
  public int type() {
    return type;
  }
  
  private int type;
}
