package org.unbiquitous.ubiengine.util.observer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Class to support context stacking.
 * 
 * @author Matheus
 */
public final class ObservationStack {
  private int level = 0;
  private SubjectDevice subject = new SubjectDevice(this, "push", "pop");
  
  /** Returns the stack level. */
  public int getLevel() {
    return level;
  }
  
  /**
   * Method to increase the stack level by one and broadcast a push event.
   * 
   * @throws Throwable
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  public void push() throws Throwable, InvocationTargetException, IllegalAccessException {
    subject.broadcast(new Event("push"));
    ++level;
  }
  
  /**
   * Method to decrease the stack level by one and broadcast a pop event.
   * 
   * @throws Throwable
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  public void pop() throws Throwable, InvocationTargetException, IllegalAccessException {
    if (level == 0)
      throw new Error("Trying to pop empty observer stack");
    --level;
    subject.broadcast(new Event("pop"));
  }
  
  /**
   * Connects a method for push stack event and a method for pop stack event.
   * 
   * @param push_method Observer method to stack push event.
   * @param pop_method Observer method to stack pop event.
   * @throws MissingEventType
   */
  public void connect(Method push_method, Method pop_method) throws MissingEventType {
    subject.connect("push", push_method);
    subject.connect("pop", pop_method);
  }
  
  /**
   * Connects an observer object to the stack push and pop stack events.
   * 
   * @param observer Observer object.
   * @param push_method Method for push event.
   * @param pop_method Method for pop event.
   * @throws MissingEventType
   */
  public void connect(Object observer, Method push_method, Method pop_method) throws MissingEventType {
    subject.connect("push", observer, push_method);
    subject.connect("pop", observer, pop_method);
  }
  
  /**
   * Disconnect a method from stack push event and a method from stack pop event.
   * 
   * @param push_method Observer method to stop observing push event.
   * @param pop_method Observer method to stop observing pop event.
   * @throws MissingEventType
   */
  public void disconnect(Method push_method, Method pop_method) throws MissingEventType {
    subject.disconnect("push", push_method);
    subject.disconnect("pop", pop_method);
  }
  
  /**
   * Disconnect an observer object from push and pop stack events.
   * 
   * @param observer Observer object.
   * @throws MissingEventType
   */
  public void disconnect(Object observer) throws MissingEventType {
    subject.disconnect("push", observer);
    subject.disconnect("pop", observer);
  }
}
