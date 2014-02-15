package org.unbiquitous.ubiengine.engine.input;

import java.lang.reflect.Method;

import org.unbiquitous.ubiengine.util.observer.Subject;
import org.unbiquitous.ubiengine.util.observer.SubjectDevice;

public abstract class InputDevice implements Subject {
  protected boolean active = false;
  
  public boolean active() {
    return active;
  }
  
  public void active(boolean a) {
    active = a;
  }
  
  protected SubjectDevice subject;
  
  public void connect(String event_type, Method handler) {
    subject.connect(event_type, handler);
  }

  public void connect(String event_type, Object observer, Method handler) {
    subject.connect(event_type, observer, handler);
  }

  public void disconnect(Method handler) {
    subject.disconnect(handler);
  }

  public void disconnect(String event_type, Method handler) {
    subject.disconnect(event_type, handler);
  }

  public void disconnect(Object observer) {
    subject.disconnect(observer);
  }

  public void disconnect(String event_type, Object observer) {
    subject.disconnect(event_type, observer);
  }
}
