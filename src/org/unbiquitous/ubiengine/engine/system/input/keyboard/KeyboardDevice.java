package org.unbiquitous.ubiengine.engine.system.input.keyboard;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.unbiquitous.ubiengine.engine.system.input.InputSource;
import org.unbiquitous.ubiengine.util.observer.Event;
import org.unbiquitous.ubiengine.util.observer.Observations;

public class KeyboardDevice extends InputSource {
  
  public static final String KEYDOWN  = "KEYDOWN";
  public static final String KEYUP    = "KEYUP";

  public static final class KeyDownEvent extends Event {
    private int unicode_char;
    
    public KeyDownEvent(int unicode_char) {
      this.unicode_char = unicode_char;
    }
    
    public int getUnicodeChar() {
      return unicode_char;
    }
  }

  public static final class KeyUpEvent extends Event {
    private int unicode_char;
    
    public KeyUpEvent(int unicode_char) {
      this.unicode_char = unicode_char;
    }
    
    public int getUnicodeChar() {
      return unicode_char;
    }
  }

  private Queue<Event> events = new LinkedList<Event>();
  
  private HashMap<Integer, Boolean> key_pressed = new HashMap<Integer, Boolean>();

  public KeyboardDevice() {
    subject = new Observations(KEYDOWN, KEYUP);
  }
  
  public void update() {
    if (!active) {
      while (!events.isEmpty())
        events.remove();
      return;
    }
    
    while (!events.isEmpty()) {
      Event event = events.poll();
      if (event instanceof KeyDownEvent) {
        key_pressed.put(Integer.valueOf(((KeyDownEvent) event).getUnicodeChar()), true);
        subject.broadcast(KEYDOWN, event);
      }
      else if (event instanceof KeyUpEvent) {
        key_pressed.put(Integer.valueOf(((KeyUpEvent) event).getUnicodeChar()), false);
        subject.broadcast(KEYUP, event);
      }
    }
  }

  public boolean isKeyPressed(int unicode_char) {
    Boolean check = key_pressed.get(Integer.valueOf(unicode_char));
    if (check == null)
      return false;
    return check.booleanValue();
  }
  
  public void forceKeyPressed(int unicode_char) {
    events.add(new KeyDownEvent(unicode_char));
  }

  public void forceKeyReleased(int unicode_char) {
    events.add(new KeyUpEvent(unicode_char));
  }
}
