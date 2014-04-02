package org.unbiquitous.uImpala.engine.asset;

/**
 * Class to animate a sprite sheet.
 * @author Pimenta
 *
 */
public abstract class Animation extends Sprite {
  /**
   * Pauses the animation.
   */
  public abstract void pause();
  
  /**
   * Resumes the animation.
   */
  public abstract void resume();
  
  /**
   * Gets the current frame rate.
   * @return Frame rate in frames per second.
   */
  public float getFPS() {
    return fps;
  }
  
  /**
   * Sets the current frame rate.
   * @param fps Frame rate in frames per second.
   */
  public void setFPS(float fps) {
    this.fps = fps;
  }
  
  /**
   * Gets the current frame.
   * @return Frame in the interval [0, frames).
   */
  public float getFrame() {
    return frame;
  }
  
  /**
   * Sets the current frame.
   * @param frame Frame in the interval [0, frames).
   */
  public void setFrame(float frame) {
    this.frame = frame;
  }
  
  protected float fps, frame;
}
