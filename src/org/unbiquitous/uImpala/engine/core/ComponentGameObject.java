package org.unbiquitous.uImpala.engine.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public abstract class ComponentGameObject extends GameObject {
  private HashSet<GameObjectComponent> addedComponents = new HashSet<GameObjectComponent>();
  private HashSet<Class<? extends GameObjectComponent>> removedComponents = new HashSet<Class<? extends GameObjectComponent>>();
  private HashMap<Class<? extends GameObjectComponent>, GameObjectComponent> components = new HashMap<Class<? extends GameObjectComponent>, GameObjectComponent>();
  private HashMap<String, Object> fields = new HashMap<String, Object>();
  private HashMap<String, HashSet<Class<? extends GameObjectComponent>>> hooks = new HashMap<String, HashSet<Class<? extends GameObjectComponent>>>();
  private GameRenderers renderers = new GameRenderers();
  
  public void addComponent(GameObjectComponent component) {
    addedComponents.add(component);
  }
  
  public void removeComponent(Class<? extends GameObjectComponent> componentClass) {
    removedComponents.add(componentClass);
  }
  
  @SuppressWarnings("unchecked")
  public <T> T read(String field, T defaultValue) {
    Object tmp = fields.get(field);
    if (tmp == null) {
      tmp = defaultValue;
    }
    return (T)tmp;
  }
  
  public void write(String field, Object value) {
    fields.put(field, value);
    HashSet<Class<? extends GameObjectComponent>> compClasses = hooks.get(field);
    if (compClasses != null) {
      for (Class<? extends GameObjectComponent> compClass : compClasses) {
        components.get(compClass).handle(field, value);
      }
    }
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
      for (Entry<String, HashSet<Class<? extends GameObjectComponent>>> entry : hooks.entrySet()) {
        entry.getValue().remove(compClass);
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
