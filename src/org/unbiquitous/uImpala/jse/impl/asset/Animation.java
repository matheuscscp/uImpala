package org.unbiquitous.uImpala.jse.impl.asset;

import org.unbiquitous.uImpala.engine.io.Screen;
import org.unbiquitous.uImpala.engine.time.Time;
import org.unbiquitous.uImpala.util.Corner;

public class Animation extends org.unbiquitous.uImpala.engine.asset.Animation {
  protected Animation(Texture tex, int frames, float fps) {
    // sprite stuff
    texture = tex;
    width = texture.getImageWidth();
    height = texture.getImageHeight();
    resetClip();
    
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
  
  public void setColor(org.unbiquitous.uImpala.util.Color color) {
    texture.setColor(color);
  }
  
  public void render(Screen screen, float x, float y, Corner corner, float opacity, float angle, float scaleX, float scaleY) {
    update();
    texture.render(screen, x, y, corner, opacity, angle, scaleX, scaleY);
  }
  
  public void clip(float x, float y, float w, float h) {
    texture.clip(x, y, w, h);
  }
  
  public void pause() {
    if (!running)
      return;
    pauseTime = Time.get();
    running = false;
  }
  
  public void resume() {
    if (running)
      return;
    lastTime += Time.get() - pauseTime;
    running = true;
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
  
  // sprite stuff
  private Texture texture;
  
  private int frames, frameInt, clipWidth, clipHeight;
  private long lastTime, pauseTime;
  private boolean running;
}
