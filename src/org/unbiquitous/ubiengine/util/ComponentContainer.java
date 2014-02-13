package org.unbiquitous.ubiengine.util;

import java.util.HashMap;

/**
 * Class to hold all single instance objects per application.
 * 
 * @author Matheus
 */
public final class ComponentContainer {
  private HashMap<Class<?>, Object> components = new HashMap<Class<?>, Object>();
  
  /** @param key The Class of the object is the key for the single instance. */
  @SuppressWarnings("unchecked")
  public <T> T get(Class<T> key) {
    return (T)components.get(key);
  }
  
  /**
   * Method to put the instance of a single instance object.
   * 
   * @param key The Class of the object is the key for the single instance.
   * @param value The single instance object reference.
   */
  public <T> void put(Class<T> key, T value) {
    components.put(key, value);
  }
  
  /**
   * Erases the component.
   * @param key The key to the component.
   */
  public <T> void erase(Class<T> key) {
    components.remove(key);
  }
}
