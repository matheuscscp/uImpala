package org.unbiquitous.ubiengine.resources.input.keyboard;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.unbiquitous.ubiengine.resources.input.InputDevice;
import org.unbiquitous.ubiengine.util.observer.Event;
import org.unbiquitous.ubiengine.util.observer.ObservationStack;

public class KeyboardDevice extends InputDevice {
  
  public static final String KEYDOWN  = "KEYDOWN";
  public static final String KEYUP    = "KEYUP";
  
  public static final class KeyEvent extends Event {
    private int unicodeChar;
    
    public KeyEvent(String type, int unicodeChar) {
      super(type);
      this.unicodeChar = unicodeChar;
    }
    
    public int getUnicodeChar() {
      return unicodeChar;
    }
  }

  private Queue<KeyEvent> events = new LinkedList<KeyEvent>();
  
  private HashMap<Integer, Boolean> key_pressed = new HashMap<Integer, Boolean>();

  public KeyboardDevice(ObservationStack observation_stack) {
    super(observation_stack);
    subject.addEvents(KEYDOWN, KEYUP);
  }
  
  public void update() throws Throwable {
    KeyEvent event;
    while (!events.isEmpty()) {
      event = events.poll();
      key_pressed.put(Integer.valueOf(event.getUnicodeChar()), event.getType().equals(KEYDOWN));
      subject.broadcast(event);
    }
  }

  public boolean isKeyPressed(int unicodeChar) {
    Boolean check = key_pressed.get(Integer.valueOf(unicodeChar));
    if (check == null)
      return false;
    return check.booleanValue();
  }
  
  public void forceKeyPressed(int unicodeChar) {
    events.add(new KeyEvent(KEYDOWN, unicodeChar));
  }

  public void forceKeyReleased(int unicodeChar) {
    events.add(new KeyEvent(KEYUP, unicodeChar));
  }
}
