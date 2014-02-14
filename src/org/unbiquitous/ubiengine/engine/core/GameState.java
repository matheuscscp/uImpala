package org.unbiquitous.ubiengine.engine.core;

import org.unbiquitous.ubiengine.util.ComponentContainer;

/**
 * Abstract class to help the engine manage game states.
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
  public abstract void update();
  
  /**
   * Method to implement rendering.
   */
  public abstract void render();
  
  /**
   * Handle a pop from the stack of game states.
   * @param args Arguments passed from the state popped.
   */
  public abstract void wakeup(Object... args);
  
  /**
   * Method to close whatever is necessary.
   */
  public abstract void close();
  
  /**
   * Use this method to build assets.
   * @param key Class of the asset to be created.
   * @param args Arguments to be passed to the constructor.
   * @return Asset reference.
   */
  protected <T> T build(Class<T> key, Object... args) {
    return game.build(key, args);
  }
  // ===========================================================================
  // nothings else matters from here to below
  // ===========================================================================
  public GameState setComponents(ComponentContainer coms) {
    components = coms;
    game = coms.get(UosGame.class);
    return this;
  }
  
  private UosGame game;
}
