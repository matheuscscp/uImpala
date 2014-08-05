package org.unbiquitous.uImpala.engine.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class ComponentGameObject extends GameObject {
  private static class GameObjectField {
    public boolean locked;
    public Object raw;
    public GameObjectField(Object raw) {
      locked = false;
      this.raw = raw;
    }
  }
  
  private HashSet<GameObjectComponent> addedComponents = new HashSet<GameObjectComponent>();
  private HashSet<Class<? extends GameObjectComponent>> removedComponents = new HashSet<Class<? extends GameObjectComponent>>();
  private HashMap<Class<? extends GameObjectComponent>, GameObjectComponent> components = new HashMap<Class<? extends GameObjectComponent>, GameObjectComponent>();
  private HashMap<String, GameObjectField> fields = new HashMap<String, GameObjectField>();
  private HashMap<String, HashSet<Class<? extends GameObjectComponent>>> hooks = new HashMap<String, HashSet<Class<? extends GameObjectComponent>>>();
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
   * @param componentClass The component class to be removed.
   */
  public void removeComponent(Class<? extends GameObjectComponent> componentClass) {
    removedComponents.add(componentClass);
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
    GameObjectField tmp = fields.get(field);
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
    GameObjectField goField = fields.get(field);
    if (goField == null) {
      goField = new GameObjectField(value);
      fields.put(field, goField);
    }
    if (goField.locked) {
      throw new Error("Something tried to write the field \"" + field + "\" while locked.");
    }
    goField.raw = value;
    goField.locked = true;
    HashSet<Class<? extends GameObjectComponent>> compClasses = hooks.get(field);
    if (compClasses == null) {
      compClasses = new HashSet<Class<? extends GameObjectComponent>>();
      hooks.put(field, compClasses);
    }
    for (Class<? extends GameObjectComponent> compClass : compClasses) {
      components.get(compClass).handle(field, value);
    }
    goField.locked = false;
  }
  
  protected void hook(String field, Class<? extends GameObjectComponent> componentClass) {
    if (components.get(componentClass) != null) {
      HashSet<Class<? extends GameObjectComponent>> compClasses = hooks.get(field);
      if (compClasses == null) {
        compClasses = new HashSet<Class<? extends GameObjectComponent>>();
        hooks.put(field, compClasses);
      }
      compClasses.add(componentClass);
    }
  }
  
  protected void unhook(String field, Class<? extends GameObjectComponent> componentClass) {
    HashSet<Class<? extends GameObjectComponent>> compClasses = hooks.get(field);
    if (compClasses != null) {
      compClasses.remove(componentClass);
    }
  }
  
  protected void putRenderer(int z, Runnable renderer) {
    renderers.put(z, renderer);
  }
  
  protected void update() {
    for (GameObjectComponent comp : addedComponents) {
      components.put(comp.getClass(), comp);
    }
    addedComponents.clear();
    
    renderers = new GameRenderers();
    for (Entry<Class<? extends GameObjectComponent>, GameObjectComponent> entry : components.entrySet()) {
      entry.getValue().update();
    }
    
    for (Class<? extends GameObjectComponent> compClass : removedComponents) {
      GameObjectComponent component = components.remove(compClass);
      if (component != null) {
        component.destroy();
      }
      for (Entry<String, HashSet<Class<? extends GameObjectComponent>>> entry : hooks.entrySet()) {
        entry.getValue().remove(compClass);
      }
    }
    removedComponents.clear();
  }
  
  protected void render(GameRenderers renderers) {
    renderers.copy(this.renderers);
  }
  
  protected void wakeup(Object... args) {
    for (Entry<Class<? extends GameObjectComponent>, GameObjectComponent> entry : components.entrySet()) {
      entry.getValue().wakeup(args);
    }
  }
  
  protected void destroy() {
    for (Entry<Class<? extends GameObjectComponent>, GameObjectComponent> entry : components.entrySet()) {
      entry.getValue().destroy();
    }
  }
}
