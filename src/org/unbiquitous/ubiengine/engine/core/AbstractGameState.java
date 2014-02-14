package org.unbiquitous.ubiengine.engine.core;

import org.unbiquitous.ubiengine.util.ComponentContainer;

/**
 * Class to be extended by implementing logic for operations of input,
 * update and rendering.
 * @author Pimenta
 *
 */
public abstract class AbstractGameState extends GameState {
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
