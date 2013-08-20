package org.unbiquitous.ubiengine.game;

import java.lang.Object;
import java.util.Map;

public final class Settings {
  private Map<String, Object> settings;
  
  public Settings(Map<String, Object> settings) {
    this.settings = settings;
  }
  
  public Object get(String key) {
    return settings.get(key);
  }
  
  public void put(String key, Object value) {
    settings.put(key, value);
  }
}
