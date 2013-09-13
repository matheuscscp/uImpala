package org.unbiquitous.ubiengine.resources.video.texture;

import java.awt.Image;
import java.awt.MediaTracker;

import javax.swing.ImageIcon;

import org.unbiquitous.ubiengine.resources.video.Screen;
import org.unbiquitous.ubiengine.util.mathematics.geometry.Rectangle;

public class Sprite {
  protected Screen screen;
  protected Image sprite;
  protected Rectangle clip_rect;
  protected int w, h;
  
  public Sprite(Screen screen, String filename) {
    ImageIcon tmp = new ImageIcon(filename);
    if (tmp.getImageLoadStatus() == MediaTracker.ERRORED)
      throw new Error("Image '" + filename + "' not found");
    this.screen = screen;
    sprite = tmp.getImage();
    w = sprite.getWidth(null);
    h = sprite.getHeight(null);
    clip_rect = new Rectangle(0, 0, w, h);
  }
  
  public void render() {
    screen.renderImage(0, 0, false, sprite, clip_rect);
  }
  
  public void render(int x, int y, boolean center) {
    screen.renderImage(x, y, center, sprite, clip_rect);
  }
  
  public int getWidth() {
    return w;
  }
  
  public int getHeight() {
    return h;
  }
  
  public void resetClip() {
    clip_rect = new Rectangle(0, 0, w, h);
  }
  
  public void clip(int x, int y, int w, int h) {
    clip_rect = new Rectangle(x, y, w, h);
  }
  
  public void clip(Rectangle clip_rect) {
    this.clip_rect = new Rectangle(clip_rect);
  }
  
  public Rectangle getClipRect() {
    return clip_rect;
  }
  
  public void update() {
    
  }
}
