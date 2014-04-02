package org.unbiquitous.uImpala.engine.jse.impl;

import org.unbiquitous.uImpala.engine.io.Screen;
import org.unbiquitous.uImpala.util.Corner;

public class Sprite extends org.unbiquitous.uImpala.engine.asset.Sprite {
  protected Sprite(Texture tex) {
    texture = tex;
    width = texture.getImageWidth();
    height = texture.getImageHeight();
    resetClip();
  }
  
  public void setColor(org.unbiquitous.uImpala.util.Color color) {
    texture.setColor(color);
  }
  
  public void render(Screen screen, float x, float y, Corner corner, float opacity, float angle, float scaleX, float scaleY) {
    texture.render(screen, x, y, corner, opacity, angle, scaleX, scaleY);
  }
  
  public void clip(float x, float y, float w, float h) {
    texture.clip(x, y, w, h);
  }
  
  private Texture texture;
}
