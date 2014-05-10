package org.unbiquitous.uImpala.engine.core;

import org.unbiquitous.uImpala.engine.asset.AssetManager;

/**
 * Interface for a game scene.
 * @see TreeScene
 * @author Pimenta
 *
 */
public abstract class GameScene {
  /**
   * Use to load assets.
   */
  protected AssetManager assets = AssetManager.create();
  
  /**
   * If true, the engine won't call update() for this scene.
   */
  protected boolean frozen = false;
  
  /**
   * If true, the engine will call render() for this scene, even if
   * frozen is true.
   */
  protected boolean visible = false;
  
  /**
   * Method to implement update.
   */
  protected abstract void update();
  
  /**
   * Method to implement rendering.
   */
  protected abstract void render();
  
  /**
   * Handle a pop from the stack of game scenes.
   * @param args Arguments passed from the scene popped.
   */
  protected abstract void wakeup(Object... args);
  
  /**
   * Method to close whatever is necessary.
   */
  protected abstract void destroy();
}
