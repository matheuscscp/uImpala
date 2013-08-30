package org.unbiquitous.ubiengine.util;

import java.util.HashMap;

public final class ComponentContainer {
  private HashMap<Class<?>, Object> components = new HashMap<Class<?>, Object>();
  
  @SuppressWarnings("unchecked")
  public <T> T get(Class<T> key) {
    return (T) components.get(key);
  }
  
  public void put(Class<?> key, Object value) {
    components.put(key, value);
  }
}
