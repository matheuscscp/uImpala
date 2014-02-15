package org.unbiquitous.ubiengine.util;

import java.util.HashMap;

/**
 * Class to hold all single instance objects per application.
 * 
 * @author Matheus
 */
@SuppressWarnings("serial")
public class ComponentContainer extends HashMap<Class<?>, Object> {
  /** @param key The Class of the object is the key for the single instance. */
  @SuppressWarnings("unchecked")
  public <T> T get(Class<T> key) {
    return (T)super.get(key);
  }
}
