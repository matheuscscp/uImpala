package org.unbiquitous.ubiengine.util.observer;

import java.lang.reflect.Method;

/**
 * Interface that all subject classes must implement.
 * A SubjectDevice must be created in the subject class, like this:<br>
 * protected SubjectDevice subject = new SubjectDevice(stack, events);
 * 
 * @author Matheus
 */
public interface Subject {
  /**
   * Method to create an observation for an observer method.
   * The implementation of this method must be something like this:<br>
   * subject.connect(event_type, handler);<br>
   * <br>
   * Example of how to get a Method:<br>
   * {ClassName}.class.getDeclaredMethod("{MethodName}", Event.class))
   * 
   * @param event_type String that describes the event type.
   * @param handler Observer method.
   * @throws MissingEventType
   */
  public void connect(String event_type, Method handler) throws MissingEventType;
  
  /**
   * Method to create an observation for an observer object.
   * The implementation of this method must be something like this:<br>
   * subject.connect(event_type, observer, handler);<br>
   * <br>
   * Example of how to get a Method:<br>
   * {ClassName}.class.getDeclaredMethod("{MethodName}", Event.class))
   * 
   * @param event_type String to describe the event type.
   * @param observer Observer object.
   * @param handler Handler method for notification.
   * @throws MissingEventType
   */
  public void connect(String event_type, Object observer, Method handler) throws MissingEventType;
  
  /**
   * Destroys all observations of the observer method in the current stack level.
   * The implementation of this method must be something like this:<br>
   * subject.disconnect(handler);
   * 
   * @param handler Observer method.
   */
  public void disconnect(Method handler);
  
  /**
   * Destroy the observation of the observer method for the given event type in the current stack level.
   * The implementation of this method must be something like this:<br>
   * subject.disconnect(event_type, handler);
   * 
   * @param event_type String to describe the event type.
   * @param handler Observer method.
   * @throws MissingEventType
   */
  public void disconnect(String event_type, Method handler) throws MissingEventType;
  
  /**
   * Destroys all observations of the observer object in the current stack level.
   * The implementation of this method must be something like this:<br>
   * subject.disconnect(observer);
   * 
   * @param observer Observer object.
   */
  public void disconnect(Object observer);
  
  /**
   * Destroy the observation of the observer object for the given event type in the current stack level.
   * The implementation of this method must be something like this:<br>
   * subject.disconnect(event_type, observer);
   * 
   * @param event_type String to describe the event type.
   * @param observer Observer object.
   * @throws MissingEventType
   */
  public void disconnect(String event_type, Object observer) throws MissingEventType;
}
