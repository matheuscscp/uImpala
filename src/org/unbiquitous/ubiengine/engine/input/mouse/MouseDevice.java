package org.unbiquitous.ubiengine.engine.input.mouse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.unbiquitous.ubiengine.engine.input.InputDevice;
import org.unbiquitous.ubiengine.util.mathematics.geometry.Rectangle;
import org.unbiquitous.ubiengine.util.observer.Event;
import org.unbiquitous.ubiengine.util.observer.Observations;

public class MouseDevice extends InputDevice {
  
  public static final String MOUSEDOWN    = "MOUSEDOWN";
  public static final String MOUSEUP      = "MOUSEUP";
  public static final String MOUSEMOTION  = "MOUSEMOTION";
  
  public static final int LEFT_BUTTON    = java.awt.event.MouseEvent.BUTTON1;
  public static final int MIDDLE_BUTTON  = java.awt.event.MouseEvent.BUTTON2;
  public static final int RIGHT_BUTTON   = java.awt.event.MouseEvent.BUTTON3;

  public static final class MouseDownEvent extends Event {
    private int button;
    
    public MouseDownEvent(int button) {
      this.button = button;
    }
    
    public int getButton() {
      return button;
    }
  }

  public static final class MouseUpEvent extends Event {
    private int button;
    
    public MouseUpEvent(int button) {
      this.button = button;
    }
    
    public int getButton() {
      return button;
    }
  }
  
  public static final class MouseMotionEvent extends Event {
    private int x, y;
    
    public MouseMotionEvent(int x, int y) {
      this.x = x;
      this.y = y;
    }
    
    public int getX() {
      return x;
    }
    
    public int getY() {
      return y;
    }
  }

  private Queue<Event> events = new LinkedList<Event>();
  
  private HashMap<Integer, Boolean> mouse_pressed = new HashMap<Integer, Boolean>();
  private int mouse_x;
  private int mouse_y;
  private int mousedown_x;
  private int mousedown_y;
  
  public MouseDevice() {
    mouse_x = 0;
    mouse_y = 0;
    mousedown_x = 0;
    mousedown_y = 0;
    subject = new Observations(MOUSEDOWN, MOUSEUP, MOUSEMOTION);
  }

  public void update() {
    if (!active) {
      while (!events.isEmpty())
        events.remove();
      return;
    }
    
    while (!events.isEmpty()) {
      Event event = events.poll();
      if (event instanceof MouseDownEvent) {
        mousedown_x = mouse_x;
        mousedown_y = mouse_y;
        mouse_pressed.put(Integer.valueOf(((MouseDownEvent) event).getButton()), true);
        subject.broadcast(MOUSEDOWN, event);
      }
      else if (event instanceof MouseUpEvent) {
        mouse_pressed.put(Integer.valueOf(((MouseUpEvent) event).getButton()), false);
        subject.broadcast(MOUSEUP, event);
      }
      else if (event instanceof MouseMotionEvent) {
        mouse_x = ((MouseMotionEvent) event).getX();
        mouse_y = ((MouseMotionEvent) event).getY();
        subject.broadcast(MOUSEMOTION, event);
      }
    }
  }

  public boolean isMousePressed(int button) {
    Boolean check = mouse_pressed.get(Integer.valueOf(button));
    if (check == null)
      return false;
    return check.booleanValue();
  }

  public int mouseX() {
    return mouse_x;
  }
  
  public int mouseY() {
    return mouse_y;
  }

  public int mouseDownX() {
    return mousedown_x;
  }
  
  public int mouseDownY() {
    return mousedown_y;
  }

  public void forceMousePressed(int button) {
    events.add(new MouseDownEvent(button));
  }

  public void forceMouseReleased(int button) {
    events.add(new MouseUpEvent(button));
  }
  
  public void forceMouseMotion(int x, int y) {
    events.add(new MouseMotionEvent(x, y));
  }
  
  public boolean isMouseInside(Rectangle rect) {
    return (mouse_x <= rect.getX() + rect.getW() && mouse_x >= rect.getX() &&
            mouse_y <= rect.getY() + rect.getH() && mouse_y >= rect.getY());
  }
  
  public boolean isMouseDownInside(Rectangle rect) {
    return (mousedown_x <= rect.getX() + rect.getW() && mousedown_x >= rect.getX() &&
            mousedown_y <= rect.getY() + rect.getH() && mousedown_y >= rect.getY());
  }
}
