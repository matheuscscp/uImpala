package org.unbiquitous.ubiengine.resources.video.texture;

import java.awt.Image;
import java.awt.MediaTracker;

import javax.swing.ImageIcon;

import org.unbiquitous.ubiengine.resources.video.Screen;

public class Sprite {
  private Screen screen;
  private Image sprite;
  
  public Sprite(Screen screen, String filename) {
    ImageIcon tmp = new ImageIcon(filename);
    if (tmp.getImageLoadStatus() == MediaTracker.ERRORED)
      throw new Error("Image '" + filename + "' not found");
    this.screen = screen;
    sprite = tmp.getImage();
  }
  
  public void render() {
    screen.renderImage(sprite, 0, 0, false);
  }
  
  public void render(int x, int y, boolean center) {
    screen.renderImage(sprite, x, y, center);
  }
}
