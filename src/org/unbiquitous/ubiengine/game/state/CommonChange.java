package org.unbiquitous.ubiengine.game.state;

import java.lang.reflect.InvocationTargetException;

import org.unbiquitous.ubiengine.util.ComponentContainer;

@SuppressWarnings("serial")
public class CommonChange extends ChangeState {
  
  private Class<?> state;
  
  public CommonChange(GameStateArgs args, Class<?> state) {
    super(args);
    this.state = state;
  }
  
  public GameState newInstance(ComponentContainer components) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    GameState game_state = null;
    if (state != null) {
      game_state = (GameState) state.getDeclaredConstructor(ComponentContainer.class, GameStateArgs.class).newInstance(components, args);
      state = null;
    }
    return game_state;
  }
}
