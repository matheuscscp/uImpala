package org.unbiquitous.uImp67.engine.asset;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.unbiquitous.uImp67.engine.io.Screen;

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
   * Calls the complete render method with angle 0.0f, scale 1.0f and opacity 1.0f.
   * @param screen Screen on which the image will be rendered.
   * @param x Coordinate x of the center of the clipping rectangle.
   * @param y Coordinate y of the center of the clipping rectangle.
   * @see Sprite#render(Screen, float, float, float)
   * @see Sprite#render(Screen, float, float, float, float, float)
   * @see Sprite#render(Screen, float, float, float, float, float, float)
   */
  public void render(Screen screen, float x, float y) {
    render(screen, x, y, 0.0f, 1.0f, 1.0f, 1.0f);
  }
  
  /**
   * Calls the complete render method with scale 1.0f and opacity 1.0f.
   * @param screen Screen on which the image will be rendered.
   * @param x Coordinate x of the center of the clipping rectangle.
   * @param y Coordinate y of the center of the clipping rectangle.
   * @param angle Angle of rotation in degrees.
   * @see Sprite#render(Screen, float, float)
   * @see Sprite#render(Screen, float, float, float, float, float)
   * @see Sprite#render(Screen, float, float, float, float, float, float)
   */
  public void render(Screen screen, float x, float y, float angle) {
    render(screen, x, y, angle, 1.0f, 1.0f, 1.0f);
  }
  
  /**
   * Calls the complete render method with opacity 1.0f.
   * @param screen Screen on which the image will be rendered.
   * @param x Coordinate x of the center of the clipping rectangle.
   * @param y Coordinate y of the center of the clipping rectangle.
   * @param angle Angle of rotation in degrees.
   * @param scaleX Scale the image in the horizontal axis. 1.0f means original size.
   * @param scaleY Scale the image in the vertical axis. 1.0f means original size.
   * @see Sprite#render(Screen, float, float)
   * @see Sprite#render(Screen, float, float, float)
   * @see Sprite#render(Screen, float, float, float, float, float, float)
   */
  public void render(Screen screen, float x, float y, float angle, float scaleX, float scaleY) {
    render(screen, x, scaleY, angle, scaleX, scaleY, 1.0f);
  }
  
  /**
   * Render the image.
   * @param screen Screen on which the image will be rendered.
   * @param x Coordinate x of the center of the clipping rectangle.
   * @param y Coordinate y of the center of the clipping rectangle.
   * @param angle Angle of rotation in degrees.
   * @param scaleX Scale the image in the horizontal axis. 1.0f means original size.
   * @param scaleY Scale the image in the vertical axis. 1.0f means original size.
   * @param opacity The opacity. 1.0f means opaque, 0.0f means transparent.
   * @see Sprite#render(Screen, float, float)
   * @see Sprite#render(Screen, float, float, float)
   * @see Sprite#render(Screen, float, float, float, float, float)
   */
  public void render(Screen screen, float x, float y, float angle, float scaleX, float scaleY, float opacity) {
    texture.bind();
    
    GL11.glColor4f(color.r, color.g, color.b, opacity);
    
    GL11.glLoadIdentity();
    GL11.glTranslatef(x, y, 0.0f);
    GL11.glRotatef(angle, 0.0f, 0.0f, 1.0f);
    GL11.glScalef(scaleX, scaleY, 0.0f);
    
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
  public void clip(int x, int y, int w, int h) {
    texCoordX0 = x/widthTexture; texCoordX1 = (x + w)/widthTexture;
    texCoordY0 = y/heightTexture; texCoordY1 = (y + h)/heightTexture;
    vertexCoordX0 = -w/2; vertexCoordX1 = w/2;
    vertexCoordY0 = -h/2; vertexCoordY1 = h/2;
  }
  
  private Texture texture;
  private float widthTexture, heightTexture;
  private int width, height;
  private float texCoordX0, texCoordX1;
  private float texCoordY0, texCoordY1;
  private float vertexCoordX0, vertexCoordX1;
  private float vertexCoordY0, vertexCoordY1;
  private Color color;
}
