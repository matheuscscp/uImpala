package org.unbiquitous.uImpala.engine.asset;

import org.unbiquitous.uImpala.engine.io.Screen;
import org.unbiquitous.uImpala.engine.time.Time;
import org.unbiquitous.uImpala.util.Color;
import org.unbiquitous.uImpala.util.Corner;

/**
 * Class to animate a sprite sheet.
 * @author Pimenta
 *
 */
public class Animation {
  /**
   * Constructor.
   * @param sprite Sprite to render.
   * @param frames Number of frames.
   * @param fps Number of frames per second.
   */
  protected Animation(Sprite sprite, int frames, float fps) {
    this.sprite = sprite;
    this.frames = frames;
    this.fps = fps;
    frame = 0;
    frameInt = 0;
    clipWidth = sprite.getWidth()/frames;
    clipHeight = sprite.getHeight();
    lastTime = Time.get();
    running = true;
    sprite.clip(0, 0, clipWidth, clipHeight);
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
  
  /**
   * Render the image.
   * @param screen Screen on which the image will be rendered.
   * @param x Coordinate x of the center of the clipping rectangle.
   * @param y Coordinate y of the center of the clipping rectangle.
   */
  public void render(Screen screen, float x, float y) {
    render(screen, x, y, Corner.CENTER, 1.0f, 0.0f, 1.0f, 1.0f, Color.WHITE);
  }
  
  /**
   * Render the image.
   * @param screen Screen on which the image will be rendered.
   * @param x Coordinate x of the corner of the clipping rectangle.
   * @param y Coordinate y of the corner of the clipping rectangle.
   * @param corner Corner of clipping rectangle. Null is considered Corner.CENTER.
   */
  public void render(Screen screen, float x, float y, Corner corner) {
    render(screen, x, y, corner, 1.0f, 0.0f, 1.0f, 1.0f, Color.WHITE);
  }
  
  /**
   * Render the image.
   * @param screen Screen on which the image will be rendered.
   * @param x Coordinate x of the corner of the clipping rectangle.
   * @param y Coordinate y of the corner of the clipping rectangle.
   * @param corner Corner of clipping rectangle. Null is considered Corner.CENTER.
   * @param opacity The opacity. 1.0f means opaque, 0.0f means transparent.
   */
  public void render(Screen screen, float x, float y, Corner corner, float opacity) {
    render(screen, x, y, corner, opacity, 0.0f, 1.0f, 1.0f, Color.WHITE);
  }
  
  /**
   * Render the image.
   * @param screen Screen on which the image will be rendered.
   * @param x Coordinate x of the corner of the clipping rectangle.
   * @param y Coordinate y of the corner of the clipping rectangle.
   * @param corner Corner of clipping rectangle. Null is considered Corner.CENTER.
   * @param opacity The opacity. 1.0f means opaque, 0.0f means transparent.
   * @param angle Angle of rotation in degrees.
   */
  public void render(Screen screen, float x, float y, Corner corner, float opacity, float angle) {
    render(screen, x, y, corner, opacity, angle, 1.0f, 1.0f, Color.WHITE);
  }
  
  /**
   * Render the image.
   * @param screen Screen on which the image will be rendered.
   * @param x Coordinate x of the corner of the clipping rectangle.
   * @param y Coordinate y of the corner of the clipping rectangle.
   * @param corner Corner of clipping rectangle. Null is considered Corner.CENTER.
   * @param opacity The opacity. 1.0f means opaque, 0.0f means transparent.
   * @param angle Angle of rotation in degrees.
   * @param scaleX Scale the image in the horizontal axis. 1.0f means original size.
   * @param scaleY Scale the image in the vertical axis. 1.0f means original size.
   */
  public void render(Screen screen, float x, float y, Corner corner, float opacity, float angle, float scaleX, float scaleY) {
    render(screen, x, y, corner, opacity, angle, scaleX, scaleY, Color.WHITE);
  }
  
  /**
   * Render the image.
   * @param screen Screen on which the image will be rendered.
   * @param x Coordinate x of the corner of the clipping rectangle.
   * @param y Coordinate y of the corner of the clipping rectangle.
   * @param corner Corner of clipping rectangle. Null is considered Corner.CENTER.
   * @param opacity The opacity. 1.0f means opaque, 0.0f means transparent.
   * @param angle Angle of rotation in degrees.
   * @param scaleX Scale the image in the horizontal axis. 1.0f means original size.
   * @param scaleY Scale the image in the vertical axis. 1.0f means original size.
   * @param color Color to multiply texture pixels.
   */
  public void render(Screen screen, float x, float y, Corner corner, float opacity, float angle, float scaleX, float scaleY, Color color) {
    update();
    sprite.render(screen, x, y, corner, opacity, angle, scaleX, scaleY, color);
  }
  
  /**
   * Get the sprite used to render a sprite sheet.
   * @return Sprite used to render a sprite sheet.
   */
  public Sprite getSprite() {
    return sprite;
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
      sprite.clip(clipWidth*frameInt, 0, clipWidth, clipHeight);
    }
  }
  
  private Sprite sprite;
  private float fps, frame;
  private int frames, frameInt, clipWidth, clipHeight;
  private long lastTime, pauseTime;
  private boolean running;
}
