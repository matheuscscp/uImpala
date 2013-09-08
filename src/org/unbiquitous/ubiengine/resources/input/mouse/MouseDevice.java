package org.unbiquitous.ubiengine.resources.input.mouse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.unbiquitous.ubiengine.resources.input.InputDevice;
import org.unbiquitous.ubiengine.util.observer.Event;
import org.unbiquitous.ubiengine.util.observer.SubjectDevice;

public class MouseDevice extends InputDevice {
  
  public static final String MOUSEDOWN    = "MOUSEDOWN";
  public static final String MOUSEUP      = "MOUSEUP";
  public static final String MOUSEMOTION  = "MOUSEMOTION";

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
  
  public MouseDevice() {
    subject = new SubjectDevice(MOUSEDOWN, MOUSEUP, MOUSEMOTION);
  }

  public void update() throws Exception {
    while (!events.isEmpty()) {
      Event event = events.poll();
      if (event instanceof MouseEvent)
        mouse_pressed.put(Integer.valueOf(((MouseEvent) event).getButton()), event.getType().equals(MOUSEDOWN));
      else {
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

  public void forceMousePressed(int button) {
    events.add(new MouseEvent(MOUSEDOWN, button));
  }

  public void forceMouseReleased(int button) {
    events.add(new MouseEvent(MOUSEUP, button));
  }
  
  public void forceMouseMotion(int x, int y) {
    events.add(new MouseMotionEvent(x, y));
  }
}
