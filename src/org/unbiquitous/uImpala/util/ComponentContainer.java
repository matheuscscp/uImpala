package org.unbiquitous.uImpala.util;

import java.util.HashMap;

/**
 * Class to hold singletons.
 * 
 * @author Matheus
 */
@SuppressWarnings("serial")
public class ComponentContainer extends HashMap<Class<?>, Object> {
  /**
   * Overriding to add type cast.
   * @param key Key object.
   * @return Casted value object.
   */
  @SuppressWarnings("unchecked")
  public <T> T get(Class<T> key) {
    return (T)super.get(key);
  }
}
