package org.unbiquitous.ubiengine.engine;

import org.unbiquitous.ubiengine.util.ComponentContainer;

/**
 * Class to be extended by implementing logic for operations of input,
 * update and rendering.
 * @author Pimenta
 *
 */
public abstract class GameState {
  /**
   * Game states interested in pop events must have a public class
   * member that implements this interface.
   * @author Pimenta
   *
   */
  public interface Args {
    
  }
  
  /**
   * Use in game states to manage singleton instances.
   */
  protected ComponentContainer components;
  
  /**
   * Method to implement input.
   */
  public abstract void input();
  
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
  public abstract void wakeup(Args args);
  
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
  protected void pop(Args args) {
    game.pop(args);
  }
  
  /**
   * Don't call this method.
   */
  public GameState setComponents(ComponentContainer coms) {
    game = coms.get(UosGame.class);
    components = coms;
    return this;
  }
  
  private UosGame game;
}
