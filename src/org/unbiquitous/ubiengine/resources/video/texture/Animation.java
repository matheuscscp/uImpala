package org.unbiquitous.ubiengine.resources.video.texture;

import org.unbiquitous.ubiengine.resources.time.DeltaTime;
import org.unbiquitous.ubiengine.resources.video.Screen;

public class Animation extends Sprite {
  protected DeltaTime deltatime;
  protected int rows, cols;
  protected float fps;
  protected int frames;
  protected float frame;
  protected int int_frame;
  protected int frame_w, frame_h;

  public Animation(Screen screen, String filename, DeltaTime deltatime, int rows, int cols, float fps, int frames) {
    super(screen, filename);
    this.deltatime = deltatime;
    this.rows = (rows > 0 ? rows : 1);
    this.cols = (cols > 0 ? cols : 1);
    this.fps = (fps > 0 ? fps : 1);
    this.frames = (frames > 0 && frames <= this.rows*this.cols ? frames : 1);
    frame = 0;
    int_frame = 0;
    frame_w = w/this.cols;
    frame_h = h/this.rows;
    clip((int_frame%cols)*frame_w, (int_frame/cols)*frame_h, frame_w, frame_h);
  }

  public Animation(Screen screen, int w, int h, boolean use_alpha, DeltaTime deltatime, int rows, int cols, float fps, int frames) {
    super(screen, w, h, use_alpha);
    this.deltatime = deltatime;
    this.rows = (rows > 0 ? rows : 1);
    this.cols = (cols > 0 ? cols : 1);
    this.fps = (fps > 0 ? fps : 1);
    this.frames = (frames > 0 && frames <= this.rows*this.cols ? frames : 1);
    frame = 0;
    int_frame = 0;
    frame_w = w/this.cols;
    frame_h = h/this.rows;
    clip((int_frame%cols)*frame_w, (int_frame/cols)*frame_h, frame_w, frame_h);
  }
  
  public void update() {
    frame += fps*deltatime.getRealDT();
    frame -= frames*Math.floor(frame/frames);
    if ((int) frame != int_frame) {
      int_frame = (int) frame;
      clip((int_frame%cols)*frame_w, (int_frame/cols)*frame_h, frame_w, frame_h);
    }
  }
  
  public int getRows() {
    return rows;
  }
  
  public int getcols() {
    return cols;
  }
  
  public float getFPS() {
    return fps;
  }
  
  public void setFPS(float fps) {
    this.fps = (fps > 0 ? fps : 1);
  }
  
  public int getFrames() {
    return frames;
  }
  
  public void setFrames(int frames) {
    this.frames = (frames > 0 && frames <= this.rows*this.cols ? frames : 1);
    this.frame = 0;
  }
  
  public float getFrame() {
    return frame;
  }
  
  public void setFrame(float frame) {
    if (frame >= 0 && frame <= frames)
      this.frame = frame;
  }
}
