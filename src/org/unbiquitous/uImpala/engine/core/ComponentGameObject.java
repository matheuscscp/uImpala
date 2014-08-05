package org.unbiquitous.uImpala.engine.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class ComponentGameObject extends GameObject {
  private static class Field {
    public boolean locked;
    public Object raw;
    public Field(Object raw) {
      locked = false;
      this.raw = raw;
    }
  }
  
  private HashSet<GameObjectComponent> addedComponents = new HashSet<GameObjectComponent>();
  private HashSet<String> removedComponents = new HashSet<String>();
  private HashMap<String, GameObjectComponent> components = new HashMap<String, GameObjectComponent>();
  private HashMap<String, Field> fields = new HashMap<String, Field>();
  private HashMap<String, HashSet<String>> hooks = new HashMap<String, HashSet<String>>();
  private GameRenderers renderers = new GameRenderers();
  
  /**
   * Constructor to assign a set of game object components.
   * @param components Set of game object components to assign.
   */
  public ComponentGameObject(GameObjectComponent... components) {
    for (GameObjectComponent comp : components) {
      addComponent(comp);
    }
  }
  
  /**
   * Method to add a component to this game object.
   * @param component The new component.
   */
  public void addComponent(GameObjectComponent component) {
    component.object = this;
    addedComponents.add(component);
  }
  
  /**
   * Method to remove a component from this game object.
   * @param componentFamily The component family to be removed.
   */
  public void removeComponent(String componentFamily) {
    removedComponents.add(componentFamily);
  }
  
  /**
   * Method to read a field of this game object. If the field is not found,
   * a default value passed to this method will be returned.
   * @param field Field name.
   * @param defaultValue Default value to return if the field is not found.
   * @return Field value or default value.
   */
  @SuppressWarnings("unchecked")
  public <T> T read(String field, T defaultValue) {
    Field tmp = fields.get(field);
    if (tmp == null) {
      return defaultValue;
    }
    return (T)tmp.raw;
  }
  
  /**
   * Method to write a value to a field of this game object.
   * After updated the value.
   * @param field
   * @param value
   */
  public void write(String field, Object value) {
    Field goField = fields.get(field);
    if (goField == null) {
      goField = new Field(value);
      fields.put(field, goField);
    }
    if (goField.locked) {
      throw new Error("Trying to write field \"" + field + "\" while locked.");
    }
    goField.raw = value;
    goField.locked = true;
    HashSet<String> compFamilies = hooks.get(field);
    if (compFamilies == null) {
      compFamilies = new HashSet<String>();
      hooks.put(field, compFamilies);
    }
    for (String compFamily : compFamilies) {
      components.get(compFamily).handle(field, value);
    }
    goField.locked = false;
  }
  
  protected void hook(String field, String componentFamily) {
    if (components.get(componentFamily) != null) {
      HashSet<String> compFamilies = hooks.get(field);
      if (compFamilies == null) {
        compFamilies = new HashSet<String>();
        hooks.put(field, compFamilies);
      }
      compFamilies.add(componentFamily);
    }
  }
  
  protected void unhook(String field, String componentFamily) {
    HashSet<String> compFamilies = hooks.get(field);
    if (compFamilies != null) {
      compFamilies.remove(componentFamily);
    }
  }
  
  protected void putRenderer(int z, Runnable renderer) {
    renderers.put(z, renderer);
  }
  
  protected void update() {
    addComponents();
    renderers = new GameRenderers();
    for (Entry<String, GameObjectComponent> entry : components.entrySet()) {
      entry.getValue().update();
    }
    removeComponents();
  }
  
  private void addComponents() {
    for (GameObjectComponent comp : addedComponents) {
      components.put(comp.family(), comp);
    }
    addedComponents.clear();
  }
  
  private void removeComponents() {
    for (String compFamily : removedComponents) {
      GameObjectComponent component = components.remove(compFamily);
      if (component != null) {
        component.destroy();
      }
      for (Entry<String, HashSet<String>> entry : hooks.entrySet()) {
        entry.getValue().remove(compFamily);
      }
    }
    removedComponents.clear();
  }
  
  protected void render(GameRenderers renderers) {
    renderers.copy(this.renderers);
  }
  
  protected void wakeup(Object... args) {
    for (Entry<String, GameObjectComponent> entry : components.entrySet()) {
      entry.getValue().wakeup(args);
    }
  }
  
  protected void destroy() {
    for (Entry<String, GameObjectComponent> entry : components.entrySet()) {
      entry.getValue().destroy();
    }
  }
}
