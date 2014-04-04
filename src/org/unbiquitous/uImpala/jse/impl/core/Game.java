package org.unbiquitous.uImpala.jse.impl.core;

import org.unbiquitous.uImpala.engine.core.GameSettings;
import org.unbiquitous.uImpala.jse.impl.asset.AssetManager;
import org.unbiquitous.uImpala.jse.impl.time.Time;

public final class Game extends org.unbiquitous.uImpala.engine.core.Game {
  public static void run(GameSettings settings) {
    run(Game.class.getName(), settings);
  }
  
  protected AssetManager createAssetManager() {
    return new AssetManager();
  }
  
  protected void initImpl() {
    Time.init();
  }
}
