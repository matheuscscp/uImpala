package org.unbiquitous.uImpala.jse.impl.asset;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.unbiquitous.uImpala.engine.io.Screen;
import org.unbiquitous.uImpala.util.Color;
import org.unbiquitous.uImpala.util.Corner;

public class Sprite extends org.unbiquitous.uImpala.engine.asset.Sprite {
  protected Sprite(Texture tex) {
    texture = tex;
    widthTexture = texture.getTextureWidth();
    heightTexture = texture.getTextureHeight();
    width = texture.getImageWidth();
    height = texture.getImageHeight();
    resetClip();
  }
  
  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }
  
  public void render(Screen screen, float x, float y, Corner corner, float opacity, float angle, float scaleX, float scaleY, Color color) {
    texture.bind();
    
    GL11.glColor4f(color.r, color.g, color.b, opacity);
    
    // check corner
    if (corner == null)
      corner = Corner.CENTER;
    switch (corner) {
      case TOP_LEFT:
        x += halfWidth;
        y += halfHeight;
        break;
        
      case TOP_RIGHT:
        x -= halfWidth;
        y += halfHeight;
        break;
        
      case BOTTOM_LEFT:
        x += halfWidth;
        y -= halfHeight;
        break;
        
      case BOTTOM_RIGHT:
        x -= halfWidth;
        y -= halfHeight;
        break;
        
      default:
        break;
    }
    
    // setup matrix
    GL11.glLoadIdentity();
    GL11.glTranslatef(x, y, 0.0f);
    GL11.glRotatef(angle, 0.0f, 0.0f, 1.0f);
    GL11.glScalef(scaleX, scaleY, 0.0f);
    
    // render
    GL11.glBegin(GL11.GL_QUADS);
      GL11.glTexCoord2f(texCoordX0, texCoordY0);
      GL11.glVertex2f(vertexCoordX0, vertexCoordY0);
      GL11.glTexCoord2f(texCoordX1, texCoordY0);
      GL11.glVertex2f(vertexCoordX1, vertexCoordY0);
      GL11.glTexCoord2f(texCoordX1, texCoordY1);
      GL11.glVertex2f(vertexCoordX1, vertexCoordY1);
      GL11.glTexCoord2f(texCoordX0, texCoordY1);
      GL11.glVertex2f(vertexCoordX0, vertexCoordY1);
    GL11.glEnd();
  }
  
  public void resetClip() {
    clip(0, 0, width, height);
  }
  
  public void clip(float x, float y, float w, float h) {
    halfWidth = w/2;
    halfHeight = h/2;
    texCoordX0 = x/widthTexture; texCoordX1 = (x + w)/widthTexture;
    texCoordY0 = y/heightTexture; texCoordY1 = (y + h)/heightTexture;
    vertexCoordX0 = -halfWidth; vertexCoordX1 = halfWidth;
    vertexCoordY0 = -halfHeight; vertexCoordY1 = halfHeight;
  }
  
  private Texture texture;
  private float widthTexture, heightTexture;
  private float texCoordX0, texCoordX1;
  private float texCoordY0, texCoordY1;
  private float vertexCoordX0, vertexCoordX1;
  private float vertexCoordY0, vertexCoordY1;
  private int width, height;
  private float halfWidth, halfHeight;
}
