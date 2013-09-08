package org.unbiquitous.ubiengine.game.state;

import org.unbiquitous.ubiengine.util.ComponentContainer;

@SuppressWarnings("serial")
public class CommonChange extends ChangeState {
  
  private Class<?> state;
  
  public CommonChange(GameStateArgs args, Class<?> state) {
    super(args);
    this.state = state;
  }
  
  public GameState newInstance(ComponentContainer components) {
    GameState game_state = null;
    if (state != null) {
      try {
        game_state = (GameState) state.getDeclaredConstructor(ComponentContainer.class, GameStateArgs.class).newInstance(components, args);
      } catch (Exception e) {
      }
      state = null;
    }
    return game_state;
  }
}
