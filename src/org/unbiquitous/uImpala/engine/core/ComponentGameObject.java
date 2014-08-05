package org.unbiquitous.uImpala.engine.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import org.unbiquitous.uImpala.util.Pair;

public abstract class ComponentGameObject extends GameObject {
  private HashSet<GameObjectComponent> addedComponents = new HashSet<GameObjectComponent>();
  private HashSet<Class<? extends GameObjectComponent>> removedComponents = new HashSet<Class<? extends GameObjectComponent>>();
  private HashMap<Class<? extends GameObjectComponent>, GameObjectComponent> components = new HashMap<Class<? extends GameObjectComponent>, GameObjectComponent>();
  private HashMap<String, Object> fields = new HashMap<String, Object>();
  private HashMap<String, Pair<Boolean, HashSet<Class<? extends GameObjectComponent>>>> hooks = new HashMap<String, Pair<Boolean, HashSet<Class<? extends GameObjectComponent>>>>();
  private GameRenderers renderers = new GameRenderers();
  
  /**
   * Method to add a component to this game object.
   * @param component The new component.
   */
  public void addComponent(GameObjectComponent component) {
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
    Object tmp = fields.get(field);
    if (tmp == null) {
      tmp = defaultValue;
    }
    return (T)tmp;
  }
  
  /**
   * Method to write a value to a field of this game object.
   * After updated the value.
   * @param field
   * @param value
   */
  public void write(String field, Object value) {
    Pair<Boolean, HashSet<Class<? extends GameObjectComponent>>> compClasses = hooks.get(field);
    if (compClasses != null) {
      // already locked
      if (compClasses.first) {
        throw new Error("Something tried to write the field \"" + field + "\" while locked.");
      }
      else {
        fields.put(field, value);
        compClasses.first = true;
        for (Class<? extends GameObjectComponent> compClass : compClasses.second) {
          components.get(compClass).handle(field, value);
        }
        compClasses.first = false;
      }
    }
  }
  
  protected void hook(String field, Class<? extends GameObjectComponent> componentClass) {
    if (components.get(componentClass) != null) {
      Pair<Boolean, HashSet<Class<? extends GameObjectComponent>>> compClasses = hooks.get(field);
      if (compClasses == null) {
        compClasses = new Pair<Boolean, HashSet<Class<? extends GameObjectComponent>>>(new Boolean(false), new HashSet<Class<? extends GameObjectComponent>>());
        hooks.put(field, compClasses);
      }
      compClasses.second.add(componentClass);
    }
  }
  
  protected void unhook(String field, Class<? extends GameObjectComponent> componentClass) {
    Pair<Boolean, HashSet<Class<? extends GameObjectComponent>>> compClasses = hooks.get(field);
    if (compClasses != null) {
      compClasses.second.remove(componentClass);
    }
  }
  
  protected void putRenderer(int z, Runnable renderer) {
    renderers.put(z, renderer);
  }
  
  protected void update() {
    for (GameObjectComponent comp : addedComponents) {
      comp.object = this;
      components.put(comp.getClass(), comp);
    }
    addedComponents.clear();
    
    for (Entry<Class<? extends GameObjectComponent>, GameObjectComponent> entry : components.entrySet()) {
      entry.getValue().update();
    }
    
    for (Class<? extends GameObjectComponent> compClass : removedComponents) {
      GameObjectComponent component = components.remove(compClass);
      if (component != null) {
        component.destroy();
      }
      for (Entry<String, Pair<Boolean, HashSet<Class<? extends GameObjectComponent>>>> entry : hooks.entrySet()) {
        entry.getValue().second.remove(compClass);
      }
    }
    removedComponents.clear();
  }
  
  protected void render(GameRenderers renderers) {
    renderers.absorb(this.renderers);
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
