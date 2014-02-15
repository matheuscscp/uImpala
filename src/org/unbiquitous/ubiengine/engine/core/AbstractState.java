package org.unbiquitous.ubiengine.engine.core;

/**
 * Class to be extended by implementing logic for operations of update,
 * renderization, wakeup (on state pops) and close.
 * @see ContainerState
 * @author Pimenta
 *
 */
public abstract class AbstractState extends GameState {
  /**
   * Create a new game state and pass to this method to change the current
   * game state.
   * @param state The new game state.
   */
  protected void change(GameState state) {
    components.get(UosGame.class).change(state);
  }
  
  /**
   * Create a new game state and pass to this method to push it.
   * @param state The new game state.
   */
  protected void push(GameState state) {
    components.get(UosGame.class).push(state);
  }
  
  /**
   * Create arguments (or not) and pass to this method to pop this game state.
   * @param args Args to be passed to the new state in the top of the stack.
   */
  protected void pop(Object... args) {
    components.get(UosGame.class).pop(args);
  }
}
