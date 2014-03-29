package org.unbiquitous.uImpala.engine.io;

import org.unbiquitous.uImpala.util.math.Rectangle;

/**
 * Class for mouse resource.
 * @author Pimenta
 *
 */
public final class MouseSource extends InputResource {
  /**
   * Broadcasted when mouse pointer moves.
   */
  public static final int EVENT_MOUSE_MOTION = IOResource.LAST_EVENT + 1;
  
  /**
   * Broadcasted when a button is pressed.
   */
  public static final int EVENT_BUTTON_DOWN  = IOResource.LAST_EVENT + 2;
  
  /**
   * Broadcasted when a button is released.
   */
  public static final int EVENT_BUTTON_UP    = IOResource.LAST_EVENT + 3;
  
  /**
   * The last event of this class.
   */
  public static final int LAST_EVENT         = EVENT_BUTTON_UP;
  
  /**
   * Constructor to allocate an array of flags, for buttons.
   * Also setup events.
   * @param butts Amount of buttons.
   */
  protected MouseSource(int butts) {
    observations.addEvents(EVENT_MOUSE_MOTION, EVENT_BUTTON_DOWN, EVENT_BUTTON_UP);
    x = 0; y = 0;
    downX = 0; downY = 0;
    buttons = new boolean[butts];
    for (int i = 0; i < butts; i++)
      buttons[i] = false;
  }
  
  protected void update() {
    while (events.size() > 0) {
      MouseEvent event = (MouseEvent)events.poll();
      switch (event.type) {
        case EVENT_MOUSE_MOTION:
          x = event.getX();
          y = event.getY();
          observations.broadcast(EVENT_MOUSE_MOTION, event);
          break;
          
        case EVENT_BUTTON_DOWN:
          buttons[event.getButton()] = true;
          downX = event.getX();
          downY = event.getY();
          observations.broadcast(EVENT_BUTTON_DOWN, event);
          break;
          
        case EVENT_BUTTON_UP:
          buttons[event.getButton()] = false;
          observations.broadcast(EVENT_BUTTON_UP, event);
          break;
          
        default:
          throw new Error("Invalid mouse event");
      }
    }
  }
  
  public void close() {
    
  }
  
  public boolean isUpdating() {
    return true;
  }
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public int getDownX() {
    return downX;
  }
  
  public int getDownY() {
    return downY;
  }
  
  public int getDeltaX() {
    return x - downX;
  }
  
  public int getDeltaY() {
    return y - downY;
  }
  
  public boolean isInside(Rectangle rect) {
    return rect.isPointInside(x, y);
  }
  
  public boolean isDownInside(Rectangle rect) {
    return rect.isPointInside(downX, downY);
  }
  
  public boolean getButton(int butt) {
    return buttons[butt];
  }
  
  private int x, y, downX, downY;
  private boolean[] buttons;
}
