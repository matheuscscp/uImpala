package org.unbiquitous.ubiengine.engine.system.io;

/**
 * Class to hold data about mouse events.
 * @author Pimenta
 *
 */
public class MouseEvent extends InputEvent {
  /**
   * Assignment constructor.
   * @param t Type.
   * @param x Coordinate x.
   * @param y Coordinate y.
   * @param butt The related button. -1 for motion event.
   */
  public MouseEvent(int t, int x, int y, int butt) {
    super(t);
    this.x = x;
    this.y = y;
    button = butt;
  }
  
  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
  
  public int getButton() {
    return button;
  }
  
  private int x, y, button;
}
