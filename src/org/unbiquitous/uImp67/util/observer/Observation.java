package org.unbiquitous.uImp67.util.observer;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Immutable class to hold information about an observation (pair
 * object-method, or a static method).
 * @author Pimenta
 *
 */
public final class Observation {
  /**
   * Create an object observation.
   * @param object Object.
   * @param method Method.
   */
  public Observation(Object object, String method) {
    if (object == null)
      throw new Error("Cannot create object observation with null object");
    try {
      this.method = object.getClass().getDeclaredMethod(method, Event.class, Subject.class);
    } catch (Exception e) {
      throw new Error(e);
    }
    if (Modifier.isStatic(this.method.getModifiers()))
      throw new Error("Cannot create object observation with static method");
    this.object = object;
    this.method.setAccessible(true);
  }
  
  /**
   * Create a method observation.
   * @param clazz Method's class.
   * @param method Method.
   */
  public Observation(Class<?> clazz, String method) {
    if (clazz == null)
      throw new Error("Cannot create method observation with null class");
    try {
      this.method = clazz.getDeclaredMethod(method, Event.class, Subject.class);
    } catch (Exception e) {
      throw new Error(e);
    }
    if (!Modifier.isStatic(this.method.getModifiers()))
      throw new Error("Cannot create method observation with non-static method");
    this.object = null;
    this.method.setAccessible(true);
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  private Object object;
  private Method method;
  
  protected void notifyEvent(Event event, Subject subject) {
    try {
      method.invoke(object, event, subject);
    } catch (Exception e) {
      throw new Error(e);
    }
  }
  
  public boolean equals(Object obs) {
    Observation other = (Observation)obs;
    return object == other.object && method.equals(other.method);
  }
  
  public int hashCode() {
    int code = method.hashCode();
    if (object != null)
      code += object.hashCode();
    return code;
  }
}
