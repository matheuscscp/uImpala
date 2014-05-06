package org.unbiquitous.uImpala.engine.asset;

import org.unbiquitous.uImpala.engine.io.Screen;
import org.unbiquitous.uImpala.util.Color;
import org.unbiquitous.uImpala.util.Corner;

/**
 * Class to implement image rendering.
 * @author Pimenta
 *
 */
public abstract class Sprite {
  /**
   * @return Image width.
   */
  public abstract int getWidth();
  
  /**
   * @return Image height.
   */
  public abstract int getHeight();
  
  /**
   * Render the image.
   * @param screen Screen on which the image will be rendered.
   * @param x Coordinate x of the center of the clipping rectangle.
   * @param y Coordinate y of the center of the clipping rectangle.
   */
  public void render(Screen screen, float x, float y) {
    render(screen, x, y, Corner.CENTER, 1.0f, 0.0f, 1.0f, 1.0f, Color.white);
  }
  
  /**
   * Render the image.
   * @param screen Screen on which the image will be rendered.
   * @param x Coordinate x of the corner of the clipping rectangle.
   * @param y Coordinate y of the corner of the clipping rectangle.
   * @param corner Corner of clipping rectangle. Null is considered Corner.CENTER.
   */
  public void render(Screen screen, float x, float y, Corner corner) {
    render(screen, x, y, corner, 1.0f, 0.0f, 1.0f, 1.0f, Color.white);
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
    render(screen, x, y, corner, opacity, 0.0f, 1.0f, 1.0f, Color.white);
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
    render(screen, x, y, corner, opacity, angle, 1.0f, 1.0f, Color.white);
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
    render(screen, x, y, corner, opacity, angle, scaleX, scaleY, Color.white);
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
  public abstract void render(Screen screen, float x, float y, Corner corner, float opacity, float angle, float scaleX, float scaleY, Color color);
  
  /**
   * Sets the clipping rectangle as the whole image.
   */
  public abstract void resetClip();
  
  /**
   * Sets a clipping rectangle.
   * @param x Coordinate x of rectangle origin.
   * @param y Coordinate y of rectangle origin.
   * @param w Rectangle width.
   * @param h Rectangle height.
   */
  public abstract void clip(float x, float y, float w, float h);
}
