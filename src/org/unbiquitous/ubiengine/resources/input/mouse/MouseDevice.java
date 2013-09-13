package org.unbiquitous.ubiengine.resources.input.mouse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.unbiquitous.ubiengine.resources.input.InputDevice;
import org.unbiquitous.ubiengine.util.mathematics.geometry.Rectangle;
import org.unbiquitous.ubiengine.util.observer.Event;
import org.unbiquitous.ubiengine.util.observer.SubjectDevice;

public class MouseDevice extends InputDevice {
  
  public static final String MOUSEDOWN    = "MOUSEDOWN";
  public static final String MOUSEUP      = "MOUSEUP";
  public static final String MOUSEMOTION  = "MOUSEMOTION";
  
  public static final int LEFT_BUTTON    = java.awt.event.MouseEvent.BUTTON1;
  public static final int MIDDLE_BUTTON  = java.awt.event.MouseEvent.BUTTON2;
  public static final int RIGHT_BUTTON   = java.awt.event.MouseEvent.BUTTON3;

  public static final class MouseEvent extends Event {
    private int button;
    
    public MouseEvent(String type, int button) {
      super(type);
      this.button = button;
    }
    
    public int getButton() {
      return button;
    }
  }
  
  public static final class MouseMotionEvent extends Event {
    private int x, y;
    
    public MouseMotionEvent(int x, int y) {
      super(MOUSEMOTION);
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
    subject = new SubjectDevice(MOUSEDOWN, MOUSEUP, MOUSEMOTION);
  }

  public void update() throws Exception {
    if (!plugged) {
      while (!events.isEmpty())
        events.remove();
      return;
    }
    
    while (!events.isEmpty()) {
      Event event = events.poll();
      if (event instanceof MouseEvent) {
        mousedown_x = mouse_x;
        mousedown_y = mouse_y;
        mouse_pressed.put(Integer.valueOf(((MouseEvent) event).getButton()), event.getType().equals(MOUSEDOWN));
      }
      else if (event instanceof MouseMotionEvent) {
        mouse_x = ((MouseMotionEvent) event).getX();
        mouse_y = ((MouseMotionEvent) event).getY();
      }
      subject.broadcast(event);
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
    events.add(new MouseEvent(MOUSEDOWN, button));
  }

  public void forceMouseReleased(int button) {
    events.add(new MouseEvent(MOUSEUP, button));
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
