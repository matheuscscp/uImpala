package org.unbiquitous.uImpala.jse.impl.core;

import org.unbiquitous.uImpala.engine.core.GameSettings;
import org.unbiquitous.uImpala.jse.impl.asset.AssetManager;

public final class Game extends org.unbiquitous.uImpala.engine.core.Game {
  public static void run(GameSettings settings) {
    org.unbiquitous.uImpala.engine.core.Game.run(Game.class.getName(), settings);
  }
  
  protected AssetManager createAssetManager() {
    return new AssetManager();
  }
}
