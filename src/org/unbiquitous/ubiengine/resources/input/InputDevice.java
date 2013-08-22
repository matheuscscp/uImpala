package org.unbiquitous.ubiengine.resources.input;

import java.lang.reflect.Method;

import org.unbiquitous.ubiengine.util.observer.Event;
import org.unbiquitous.ubiengine.util.observer.MissingEventType;
import org.unbiquitous.ubiengine.util.observer.ObservationStack;
import org.unbiquitous.ubiengine.util.observer.Subject;
import org.unbiquitous.ubiengine.util.observer.SubjectDevice;

public abstract class InputDevice implements Subject {
  
  public static String UNPLUGGED = "UNPLUGGED";
  
  protected SubjectDevice subject;
  
  public InputDevice(ObservationStack observation_stack) {
    subject = new SubjectDevice(observation_stack, UNPLUGGED);
  }
  
  public void connect(String event_type, Method handler)
      throws MissingEventType {
    subject.connect(event_type, handler);
  }

  public void connect(String event_type, Object observer, Method handler)
      throws MissingEventType {
    subject.connect(event_type, observer, handler);
  }

  public void disconnect(Method handler) {
    subject.disconnect(handler);
  }

  public void disconnect(String event_type, Method handler)
      throws MissingEventType {
    subject.disconnect(event_type, handler);
  }

  public void disconnect(Object observer) {
    subject.disconnect(observer);
  }

  public void disconnect(String event_type, Object observer)
      throws MissingEventType {
    subject.disconnect(event_type, observer);
  }
  
  protected boolean plugged = false;
  
  public boolean isPlugged() {
    return plugged;
  }
  
  public void plug() {
    plugged = true;
  }
  
  public void unplug() throws Throwable {
    if (plugged)
      subject.broadcast(new Event(UNPLUGGED));
    plugged = false;
  }
}
