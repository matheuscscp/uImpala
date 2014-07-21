package org.unbiquitous.uImpala.engine.core;

import org.unbiquitous.uImpala.engine.asset.AssetManager;

/**
 * Interface for a game scene.
 * @see GameObjectTreeScene
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
  private boolean frozen = false;
  
  /**
   * If true, the engine will call render() for this scene, even if
   * frozen is true.
   */
  private boolean visible = false;
  
  /**
   * Method to implement update.
   */
  protected abstract void update();
  
  /**
   * Method to implement rendering.
   */
  public abstract void render();
  
  /**
   * Handle a pop from the stack of game scenes.
   * @param args Arguments passed from the scene popped.
   */
  protected abstract void wakeup(Object... args);
  
  /**
   * Method to close whatever is necessary.
   */
  protected abstract void destroy();

	public boolean isFrozen() {
		return frozen;
	}

	protected void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public boolean isVisible() {
		return visible;
	}

	protected void setVisible(boolean visible) {
		this.visible = visible;
	}
}
