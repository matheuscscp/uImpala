package org.unbiquitous.ubiengine.engine.core;

import org.unbiquitous.ubiengine.util.ComponentContainer;

/**
 * Use this class to implement game objects of a ContainerState.
 * @author Pimenta
 *
 */
public abstract class GameObject {
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
   * Create a new game state and pass to this method to change the current
   * game state.
   * @param state The new game state.
   */
  protected void change(GameState state) {
    game.change(state.setComponents(components));
  }
  
  /**
   * Create a new game state and pass to this method to push it.
   * @param state The new game state.
   */
  protected void push(GameState state) {
    game.push(state.setComponents(components));
  }
  
  /**
   * Create arguments (or not) and pass to this method to pop this game state.
   * @param args Args to be passed to the new state in the top of the stack.
   */
  protected void pop(Object... args) {
    game.pop(args);
  }
  
  /**
   * Use this method to build assets.
   * @param key Class of the asset to be created.
   * @param args Arguments to be passed to the constructor.
   * @return Asset reference.
   */
  protected <T> T build(Class<T> key, Object... args) {
    return game.build(key, args);
  }
  
  /**
   * Use to add game objects to the parent game state.
   * @param o GameObject reference.
   */
  protected void add(GameObject o) {
    state.add(o);
  }
  
  /**
   * Flag to tell the game state if this object must be destroyed.
   */
  protected boolean destroy = false;
  // ===========================================================================
  // nothings else matters from here to below
  // ===========================================================================
  public GameObject setComponents(ComponentContainer coms, ContainerState s) {
    components = coms;
    game = coms.get(UosGame.class);
    state = s;
    return this;
  }
  
  private UosGame game;
  private ContainerState state;
}
