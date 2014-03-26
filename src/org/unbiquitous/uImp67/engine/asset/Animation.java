package org.unbiquitous.uImp67.engine.asset;

import org.unbiquitous.uImp67.engine.io.Screen;
import org.unbiquitous.uImp67.engine.time.Time;

/**
 * Class to animate a sprite sheet.
 * @author Pimenta
 *
 */
public class Animation extends Sprite {
  /**
   * Constructor to load the sprite sheet and set class parameters.
   * @param assets Object to load the image.
   * @param path Image path.
   * @param frames Amount of frames to divide the image.
   * @param fps Frame rate in frames per second.
   */
  public Animation(AssetManager assets, String path, int frames, float fps) {
    super(assets, path);
    this.frames = frames;
    this.fps = fps;
    frame = 0;
    frameInt = 0;
    clipWidth = getWidth()/frames;
    clipHeight = getHeight();
    lastTime = Time.get();
    running = true;
    clip(0, 0, clipWidth, clipHeight);
  }
  
  /**
   * Pauses the animation.
   */
  public void pause() {
    if (!running)
      return;
    pauseTime = Time.get();
    running = false;
  }
  
  /**
   * Resumes the animation.
   */
  public void resume() {
    if (running)
      return;
    lastTime += Time.get() - pauseTime;
    running = true;
  }
  
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
  
  public void render(float x, float y, Screen screen, float opacity) {
    update();
    super.render(x, y, screen, opacity);
  }
  
  public void render(Screen screen, float x, float y, float angle, float scaleX, float scaleY, float opacity) {
    update();
    super.render(screen, x, y, angle, scaleX, scaleY, opacity);
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  private void update() {
    if (!running)
      return;
    
    long now = Time.get();
    float dt = (now - lastTime)/1000f;
    lastTime = now;
    
    frame += fps*dt;
    frame -= frames*Math.floor(frame/frames);
    if (frameInt != (int)frame) {
      frameInt = (int)frame;
      clip(clipWidth*frameInt, 0, clipWidth, clipHeight);
    }
  }
  
  private int frames, frameInt, clipWidth, clipHeight;
  private long lastTime, pauseTime;
  private float fps, frame;
  private boolean running;
}
