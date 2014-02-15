package org.unbiquitous.ubiengine.engine.core;

import org.unbiquitous.ubiengine.util.ComponentContainer;

/**
 * Engine's private use. Use one of the two subclasses.
 * @see AbstractState
 * @see ContainerState
 * @author Pimenta
 *
 */
public abstract class GameState {
  /**
   * Use to manage singleton instances.
   */
  protected ComponentContainer components;
  
  /**
   * Method to implement update.
   */
  protected abstract void update();
  
  /**
   * Method to implement rendering.
   */
  protected abstract void render();
  
  /**
   * Handle a pop from the stack of game states.
   * @param args Arguments passed from the state popped.
   */
  protected abstract void wakeup(Object... args);
  
  /**
   * Method to close whatever is necessary.
   */
  protected abstract void close();
  
  /**
   * Use this method to build assets.
   * @param key Class of the asset to be created.
   * @param args Arguments to be passed to the constructor.
   * @return Asset reference.
   */
  protected <T> T build(Class<T> key, Object... args) {
    return components.get(UosGame.class).build(key, args);
  }
  
  /**
   * Engine's private use.
   */
  protected GameState setComponents(ComponentContainer coms) {
    components = coms;
    return this;
  }
}
