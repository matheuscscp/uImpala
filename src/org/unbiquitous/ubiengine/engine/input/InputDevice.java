package org.unbiquitous.ubiengine.engine.input;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Queue;

import org.unbiquitous.ubiengine.util.observer.Subject;
import org.unbiquitous.ubiengine.util.observer.SubjectDevice;

public abstract class InputDevice implements Subject {
  /**
   * Flag to tell if the device is ready for use.
   */
  protected boolean ready = false;
  
  /**
   * Event queue to be polled in <code>update()</code>.
   */
  protected Queue<InputEvent> events = new LinkedList<InputEvent>();
  
  /**
   * Query if the device is ready for use.
   * @return Returns true if is ready.
   */
  public boolean ready() {
    return ready;
  }
  
  /**
   * Engine's private use.
   */
  public void ready(boolean r) {
    ready = r;
  }
  
  /**
   * Method to update the device state and broadcast events.
   */
  protected abstract void update();
  
  /**
   * Use to broadcast events.
   */
  protected SubjectDevice subject;
  
  public void connect(Integer event_type, Method handler) {
    subject.connect(event_type, handler);
  }
  
  public void connect(Integer event_type, Object observer, Method handler) {
    subject.connect(event_type, observer, handler);
  }
  
  public void disconnect(Method handler) {
    subject.disconnect(handler);
  }
  
  public void disconnect(Integer event_type, Method handler) {
    subject.disconnect(event_type, handler);
  }
  
  public void disconnect(Object observer) {
    subject.disconnect(observer);
  }
  
  public void disconnect(Integer event_type, Object observer) {
    subject.disconnect(event_type, observer);
  }
}
