package org.unbiquitous.ubiengine.game.state;

@SuppressWarnings("serial")
public class ChangeState extends Exception {
  
  protected GameStateArgs args;
  
  public ChangeState(GameStateArgs args) {
    this.args = args;
  }
  
  public GameStateArgs getArgs() {
    return args;
  }
}
