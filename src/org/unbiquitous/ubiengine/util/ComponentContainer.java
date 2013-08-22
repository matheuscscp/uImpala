package org.unbiquitous.ubiengine.util;

import java.util.HashMap;

public final class ComponentContainer {
  private HashMap<Class<?>, Object> singletons = new HashMap<Class<?>, Object>();
  
  @SuppressWarnings("unchecked")
  public <T> T get(Class<T> key) {
    return (T) singletons.get(key);
  }
  
  public void put(Class<?> key, Object value) {
    singletons.put(key, value);
  }
}
