package org.unbiquitous.ubiengine.game.state;

import org.unbiquitous.ubiengine.util.ComponentContainer;

public abstract class GameState {
  protected ComponentContainer components;
  
  public GameState(ComponentContainer components) {
    this.components = components;
  }
  
  public abstract void input();
  public abstract void update();
  public abstract void render();
}
