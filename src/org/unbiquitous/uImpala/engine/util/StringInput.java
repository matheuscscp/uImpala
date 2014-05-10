package org.unbiquitous.uImpala.engine.util;

import java.awt.event.KeyEvent;

import org.unbiquitous.uImpala.engine.core.GameObject;
import org.unbiquitous.uImpala.engine.core.GameRenderers;
import org.unbiquitous.uImpala.engine.io.KeyboardEvent;
import org.unbiquitous.uImpala.engine.io.KeyboardSource;
import org.unbiquitous.uImpala.engine.time.Stopwatch;
import org.unbiquitous.uImpala.util.observer.Event;
import org.unbiquitous.uImpala.util.observer.Observation;
import org.unbiquitous.uImpala.util.observer.Subject;

/**
 * This class handles keyboard events to get text input.
 * @author Pimenta
 *
 */
public class StringInput extends GameObject {
  /**
   * A constructor to set the source from where the object must retrieve input.
   * @param keyb Keyboard source.
   */
  public StringInput(KeyboardSource keyb) {
    keyboard = keyb;
    connect();
  }
  
  /**
   * Access method to current state of input text.
   * @return Input text.
   */
  public String getInput() {
    return input;
  }
  
  /**
   * Set the current state of input text.
   * @param i Input text.
   */
  public void setInput(String i) {
    input = i;
  }
  
  /**
   * Activate input events.
   */
  public void connect() {
    keyboard.connect(KeyboardSource.EVENT_KEY_DOWN, keyDown);
    keyboard.connect(KeyboardSource.EVENT_KEY_UP, keyUp);
  }
  
  /**
   * Deactivate input events.
   */
  public void disconnect() {
    character = '\0';
    keyboard.disconnect(KeyboardSource.EVENT_KEY_DOWN, keyDown);
    keyboard.connect(KeyboardSource.EVENT_KEY_UP, keyUp);
  }
  
  protected void update() {
    if (character != '\0' && stopwatch.time() > delay) {
      if (key != 8/*backspace*/)
        input += character;
      else if (input.length() > 0)
        input = input.substring(0, input.length() - 1);
      delay = 1000/KEYS_PER_SEC;
      stopwatch.start();
    }
  }
  
  protected void render(GameRenderers renderers) {
    
  }
  
  protected void wakeup(Object... args) {
    
  }
  
  protected void destroy() {
    disconnect();
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  private static final long HOLD_DELAY = 500;
  private static final int KEYS_PER_SEC = 30;
  
  private String input = "";
  private int key;
  private char character = '\0';
  private Stopwatch stopwatch = new Stopwatch();
  private long delay = HOLD_DELAY;
  
  private KeyboardSource keyboard;
  private Observation keyDown = new Observation(this, "keyDown");
  private Observation keyUp = new Observation(this, "keyUp");
  
  private static boolean isPrintableChar(char c) {
    Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
    return  
      !Character.isISOControl(c) &&
      c != KeyEvent.CHAR_UNDEFINED &&
      block != null &&
      block != Character.UnicodeBlock.SPECIALS
    ;
  }
  
  protected void keyDown(Event event, Subject subject) {
    if (frozen)
      return;
    KeyboardEvent ev = (KeyboardEvent)event;
    int k = ev.getKey();
    char c = ev.getCharacter();
    if (k == 8/*backspace*/) {
      if (input.length() == 0)
        return;
      input = input.substring(0, input.length() - 1);
    }
    else if (isPrintableChar(c))
      input += c;
    else
      return;
    key = k;
    character = c;
    delay = HOLD_DELAY;
    stopwatch.start();
  }
  
  protected void keyUp(Event event, Subject subject) {
    if (((KeyboardEvent)event).getKey() == key)
      character = '\0';
    else {
      delay = HOLD_DELAY;
      stopwatch.start();
    }
  }
}
