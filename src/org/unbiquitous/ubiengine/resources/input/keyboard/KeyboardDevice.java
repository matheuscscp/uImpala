package org.unbiquitous.ubiengine.resources.input.keyboard;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.unbiquitous.ubiengine.resources.input.InputDevice;
import org.unbiquitous.ubiengine.util.observer.Event;
import org.unbiquitous.ubiengine.util.observer.SubjectDevice;

public class KeyboardDevice extends InputDevice {
  
  public static final String KEYDOWN  = "KEYDOWN";
  public static final String KEYUP    = "KEYUP";
  
  public static final class KeyEvent extends Event {
    private int unicode_char;
    
    public KeyEvent(String type, int unicode_char) {
      super(type);
      this.unicode_char = unicode_char;
    }
    
    public int getUnicodeChar() {
      return unicode_char;
    }
  }

  private Queue<KeyEvent> events = new LinkedList<KeyEvent>();
  
  private HashMap<Integer, Boolean> key_pressed = new HashMap<Integer, Boolean>();

  public KeyboardDevice() {
    subject = new SubjectDevice(KEYDOWN, KEYUP);
  }
  
  public void update() throws Exception {
    if (!plugged) {
      while (!events.isEmpty())
        events.remove();
      return;
    }
    
    KeyEvent event;
    while (!events.isEmpty()) {
      event = events.poll();
      key_pressed.put(Integer.valueOf(event.getUnicodeChar()), event.getType().equals(KEYDOWN));
      subject.broadcast(event);
    }
  }

  public boolean isKeyPressed(int unicode_char) {
    Boolean check = key_pressed.get(Integer.valueOf(unicode_char));
    if (check == null)
      return false;
    return check.booleanValue();
  }
  
  public void forceKeyPressed(int unicode_char) {
    events.add(new KeyEvent(KEYDOWN, unicode_char));
  }

  public void forceKeyReleased(int unicode_char) {
    events.add(new KeyEvent(KEYUP, unicode_char));
  }
}
