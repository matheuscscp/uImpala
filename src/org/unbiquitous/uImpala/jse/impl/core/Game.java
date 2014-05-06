package org.unbiquitous.uImpala.jse.impl.core;

import org.unbiquitous.uImpala.engine.core.GameSettings;
import org.unbiquitous.uImpala.jse.impl.asset.AssetManager;
import org.unbiquitous.uImpala.jse.impl.io.Screen;
import org.unbiquitous.uImpala.jse.impl.io.Speaker;
import org.unbiquitous.uImpala.jse.impl.time.Time;

public class Game extends org.unbiquitous.uImpala.engine.core.Game {
  public static void run(GameSettings settings) {
    run(Game.class.getName(), settings);
  }
  
  protected void initImpl() {
    AssetManager.initImpl();
    Time.initImpl();
    Screen.initImpl();
    Speaker.initImpl();
  }
}
