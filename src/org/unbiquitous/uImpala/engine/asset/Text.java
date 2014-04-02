package org.unbiquitous.uImpala.engine.asset;

import org.unbiquitous.uImpala.engine.io.Screen;
import org.unbiquitous.uImpala.util.Color;
import org.unbiquitous.uImpala.util.Corner;

/**
 * Class to implement text rendering.
 * @author Pimenta
 *
 */
public abstract class Text {
  /**
   * Set text to render.
   * @param text String text.
   */
  public abstract void setText(String text);
  
  /**
   * Gets the width of the text rectangle that will be rendered.
   * @return Width in pixels.
   */
  public int getWidth() {
    return width;
  }
  
  /**
   * Gets the height of the text rectangle that will be rendered.
   * @return Height in pixels.
   */
  public int getHeight() {
    return height;
  }
  
  /**
   * Render the text.
   * @param screen Screen on which the text will be rendered.
   * @param x Coordinate x of the center of the drawn text.
   * @param y Coordinate y of the center of the drawn text.
   */
  public void render(Screen screen, float x, float y) {
    render(screen, x, y, Corner.CENTER, 1.0f, 0.0f, 1.0f, 1.0f);
  }
  
  /**
   * Render the text.
   * @param screen Screen on which the text will be rendered.
   * @param x Coordinate x of the corner of the drawn text.
   * @param y Coordinate y of the corner of the drawn text.
   * @param corner Corner of drawn text. Null is considered Corner.CENTER.
   */
  public void render(Screen screen, float x, float y, Corner corner) {
    render(screen, x, y, corner, 1.0f, 0.0f, 1.0f, 1.0f);
  }
  
  /**
   * Render the text.
   * @param screen Screen on which the text will be rendered.
   * @param x Coordinate x of the corner of the drawn text.
   * @param y Coordinate y of the corner of the drawn text.
   * @param corner Corner of drawn text. Null is considered Corner.CENTER.
   * @param opacity The opacity. 1.0f means opaque, 0.0f means transparent.
   */
  public void render(Screen screen, float x, float y, Corner corner, float opacity) {
    render(screen, x, y, corner, opacity, 0.0f, 1.0f, 1.0f);
  }
  
  /**
   * Render the text.
   * @param screen Screen on which the text will be rendered.
   * @param x Coordinate x of the corner of the drawn text.
   * @param y Coordinate y of the corner of the drawn text.
   * @param corner Corner of drawn text. Null is considered Corner.CENTER.
   * @param opacity The opacity. 1.0f means opaque, 0.0f means transparent.
   * @param angle Angle of rotation in degrees.
   */
  public void render(Screen screen, float x, float y, Corner corner, float opacity, float angle) {
    render(screen, x, y, corner, opacity, angle, 1.0f, 1.0f);
  }
  
  /**
   * Render the text.
   * @param screen Screen on which the text will be rendered.
   * @param x Coordinate x of the corner of the drawn text.
   * @param y Coordinate y of the corner of the drawn text.
   * @param corner Corner of drawn text. Null is considered Corner.CENTER.
   * @param opacity The opacity. 1.0f means opaque, 0.0f means transparent.
   * @param angle Angle of rotation in degrees.
   * @param scaleX Scale the text in the horizontal axis. 1.0f means original size.
   * @param scaleY Scale the text in the vertical axis. 1.0f means original size.
   */
  public abstract void render(Screen screen, float x, float y, Corner corner, float opacity, float angle, float scaleX, float scaleY);
  
  /**
   * Set options. Null arguments won't be considered.
   * @param style Font style, for Java default fonts.
   * @param size Font size.
   * @param antiAlias Apply anti-aliasing when rendering.
   * @param color Color to render.
   */
  public abstract void options(Integer style, Float size, Boolean antiAlias, Color color);
  
  protected int width, height;
}
