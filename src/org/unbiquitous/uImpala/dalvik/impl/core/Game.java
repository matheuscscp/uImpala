package org.unbiquitous.uImpala.dalvik.impl.core;

import org.unbiquitous.uImpala.dalvik.impl.asset.AssetManager;
import org.unbiquitous.uImpala.dalvik.impl.io.Screen;
import org.unbiquitous.uImpala.dalvik.impl.io.Speaker;
import org.unbiquitous.uImpala.dalvik.impl.time.Time;
import org.unbiquitous.uImpala.engine.core.GameSettings;

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
