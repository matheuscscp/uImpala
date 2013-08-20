package org.unbiquitous.ubiengine.game.state;

import org.unbiquitous.ubiengine.util.SingletonContainer;

public abstract class State {
  protected SingletonContainer singletons;
  
  public State(SingletonContainer singletons) {
    this.singletons = singletons;
  }
  
  public abstract void input();
  public abstract void update();
  public abstract void render();
}
