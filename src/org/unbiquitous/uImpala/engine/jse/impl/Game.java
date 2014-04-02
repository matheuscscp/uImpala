package org.unbiquitous.uImpala.engine.jse.impl;

import org.unbiquitous.uImpala.engine.core.GameSettings;

public final class Game extends org.unbiquitous.uImpala.engine.core.Game {
  static {
    System.out.println("AGORA FOI");
  }
  
  public static void run(GameSettings settings) {
    org.unbiquitous.uImpala.engine.core.Game.run(Game.class.getName(), settings);
  }
}
