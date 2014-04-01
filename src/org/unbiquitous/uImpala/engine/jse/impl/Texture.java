package org.unbiquitous.uImpala.engine.jse.impl;

public final class Texture implements org.unbiquitous.uImpala.engine.asset.Texture {
  private org.newdawn.slick.opengl.Texture texture;
  
  public Texture(org.newdawn.slick.opengl.Texture tex) {
    texture = tex;
  }
  
  public int getTextureWidth() {
    return texture.getTextureWidth();
  }
  
  public int getTextureHeight() {
    return texture.getTextureHeight();
  }
  
  public int getImageWidth() {
    return texture.getImageWidth();
  }
  
  public int getImageHeight() {
    return texture.getImageHeight();
  }
  
  public void bind() {
    texture.bind();
  }
  
  public void release() {
    texture.release();
  }
}
