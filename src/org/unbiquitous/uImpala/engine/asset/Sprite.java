package org.unbiquitous.uImpala.engine.asset;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.unbiquitous.uImpala.engine.io.Screen;
import org.unbiquitous.uImpala.util.Corner;

/**
 * Class to implement image rendering.
 * @author Pimenta
 *
 */
public class Sprite {
  /**
   * Loads the image.
   * @param assets Object to load the image.
   * @param path Image path.
   */
  public Sprite(AssetManager assets, String path) {
    texture = assets.getTexture(path);
    widthTexture = texture.getTextureWidth();
    heightTexture = texture.getTextureHeight();
    width = texture.getImageWidth();
    height = texture.getImageHeight();
    resetClip();
    color = Color.white;
  }
  
  /**
   * @return Image width.
   */
  public int getWidth() {
    return width;
  }
  
  /**
   * @return Image height.
   */
  public int getHeight() {
    return height;
  }
  
  /**
   * Set the color to multiply the texture.
   * @param color Color.
   */
  public void setColor(Color color) {
    this.color = color;
  }
  
  /**
   * Render the image.
   * @param screen Screen on which the image will be rendered.
   * @param x Coordinate x of the top-left corner of the clipping rectangle.
   * @param y Coordinate y of the top-left corner of the clipping rectangle.
   */
  public void render(Screen screen, float x, float y) {
    render(screen, x, y, Corner.TOP_LEFT, 0.0f, 1.0f, 1.0f, 1.0f);
  }
  
  /**
   * Render the image.
   * @param screen Screen on which the image will be rendered.
   * @param x Coordinate x of the corner of the clipping rectangle.
   * @param y Coordinate y of the corner of the clipping rectangle.
   * @param corner Corner of clipping rectangle.
   */
  public void render(Screen screen, float x, float y, Corner corner) {
    render(screen, x, y, corner, 0.0f, 1.0f, 1.0f, 1.0f);
  }
  
  /**
   * Render the image.
   * @param screen Screen on which the image will be rendered.
   * @param x Coordinate x of the corner of the clipping rectangle.
   * @param y Coordinate y of the corner of the clipping rectangle.
   * @param corner Corner of clipping rectangle.
   * @param opacity The opacity. 1.0f means opaque, 0.0f means transparent.
   */
  public void render(Screen screen, float x, float y, Corner corner, float opacity) {
    render(screen, x, y, corner, 0.0f, 1.0f, 1.0f, opacity);
  }
  
  /**
   * Render the image.
   * @param screen Screen on which the image will be rendered.
   * @param x Coordinate x of the center of the clipping rectangle.
   * @param y Coordinate y of the center corner of the clipping rectangle.
   * @param angle Angle of rotation in degrees.
   */
  public void render(Screen screen, float x, float y, float angle) {
    render(screen, x, y, Corner.CENTER, angle, 1.0f, 1.0f, 1.0f);
  }
  
  /**
   * Render the image.
   * @param screen Screen on which the image will be rendered.
   * @param x Coordinate x of the center of the clipping rectangle.
   * @param y Coordinate y of the center corner of the clipping rectangle.
   * @param angle Angle of rotation in degrees.
   * @param scaleX Scale the image in the horizontal axis. 1.0f means original size.
   * @param scaleY Scale the image in the vertical axis. 1.0f means original size.
   */
  public void render(Screen screen, float x, float y, float angle, float scaleX, float scaleY) {
    render(screen, x, y, Corner.CENTER, angle, scaleX, scaleY, 1.0f);
  }
  
  /**
   * Render the image.
   * @param screen Screen on which the image will be rendered.
   * @param x Coordinate x of the center of the clipping rectangle.
   * @param y Coordinate y of the center corner of the clipping rectangle.
   * @param angle Angle of rotation in degrees.
   * @param scaleX Scale the image in the horizontal axis. 1.0f means original size.
   * @param scaleY Scale the image in the vertical axis. 1.0f means original size.
   * @param opacity The opacity. 1.0f means opaque, 0.0f means transparent.
   */
  public void render(Screen screen, float x, float y, float angle, float scaleX, float scaleY, float opacity) {
    render(screen, x, y, Corner.CENTER, angle, scaleX, scaleY, opacity);
  }
  
  /**
   * Render the image.
   * @param screen Screen on which the image will be rendered.
   * @param x Coordinate x of the corner of the clipping rectangle.
   * @param y Coordinate y of the corner of the clipping rectangle.
   * @param corner Corner of clipping rectangle.
   * @param angle Angle of rotation in degrees.
   * @param scaleX Scale the image in the horizontal axis. 1.0f means original size.
   * @param scaleY Scale the image in the vertical axis. 1.0f means original size.
   * @param opacity The opacity. 1.0f means opaque, 0.0f means transparent.
   */
  protected void render(Screen screen, float x, float y, Corner corner, float angle, float scaleX, float scaleY, float opacity) {
    texture.bind();
    
    GL11.glColor4f(color.r, color.g, color.b, opacity);
    
    // check corner
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
  
  /**
   * Sets the clipping rectangle as the whole image.
   */
  public void resetClip() {
    clip(0, 0, width, height);
  }
  
  /**
   * Sets a clipping rectangle.
   * @param x Coordinate x of rectangle origin.
   * @param y Coordinate y of rectangle origin.
   * @param w Rectangle width.
   * @param h Rectangle height.
   */
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
  private int width, height;
  private float texCoordX0, texCoordX1;
  private float texCoordY0, texCoordY1;
  private float vertexCoordX0, vertexCoordX1;
  private float vertexCoordY0, vertexCoordY1;
  private float halfWidth, halfHeight;
  private Color color;
}
