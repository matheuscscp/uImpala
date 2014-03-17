package org.unbiquitous.uImp67.engine.asset;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.unbiquitous.uImp67.engine.io.Screen;

/**
 * Class to implement text rendering.
 * @author Pimenta
 *
 */
public class Text {
  /**
   * Assigment constructor.
   * @param font Text font.
   * @param text String text.
   */
  public Text(Font font, String text) {
    awtFont = font;
    this.text = text;
    antiAlias = true;
    color = Color.white;
    ttfFont = new TrueTypeFont(awtFont, antiAlias);
  }
  
  /**
   * Set text to render.
   * @param text String text.
   */
  public void setText(String text) {
    this.text = text;
  }
  
  /**
   * Calls the complete render method with opacity 1.0f.
   * @param x Coordinate x of the top-left corner of the text.
   * @param y Coordinate y of the top-left corner of the text.
   * @param screen Screen on which the text will be rendered.
   * @see Text#render(float, float, Screen, float)
   */
  public void render(float x, float y, Screen screen) {
    render(x, y, screen, 1.0f);
  }
  
  /**
   * Render with (x,y) being the top-left corner of the text.
   * @param x Coordinate x of the top-left corner of the text.
   * @param y Coordinate y of the top-left corner of the text.
   * @param screen Screen on which the text will be rendered.
   * @param opacity The opacity. 1.0f means opaque, 0.0f means transparent.
   * @see Text#render(float, float, Screen)
   */
  public void render(float x, float y, Screen screen, float opacity) {
    color.a = opacity;
    ttfFont.drawString(x, y, text, color);
  }
  
  /**
   * Calls the complete render method with angle 0.0f, scale 1.0f and opacity 1.0f.
   * @param screen Screen on which the text will be rendered.
   * @param x Coordinate x of the center of the text.
   * @param y Coordinate y of the center of the text.
   * @see Text#render(Screen, float, float, float)
   * @see Text#render(Screen, float, float, float, float, float)
   * @see Text#render(Screen, float, float, float, float, float, float)
   */
  public void render(Screen screen, float x, float y) {
    render(screen, x, y, 0.0f, 1.0f, 1.0f, 1.0f);
  }
  
  /**
   * Calls the complete render method with scale 1.0f and opacity 1.0f.
   * @param screen Screen on which the text will be rendered.
   * @param x Coordinate x of the center of the text.
   * @param y Coordinate y of the center of the text.
   * @param angle Angle of rotation in degrees.
   * @see Text#render(Screen, float, float)
   * @see Text#render(Screen, float, float, float, float, float)
   * @see Text#render(Screen, float, float, float, float, float, float)
   */
  public void render(Screen screen, float x, float y, float angle) {
    render(screen, x, y, angle, 1.0f, 1.0f, 1.0f);
  }
  
  /**
   * Calls the complete render method with opacity 1.0f.
   * @param screen Screen on which the text will be rendered.
   * @param x Coordinate x of the center of the text.
   * @param y Coordinate y of the center of the text.
   * @param angle Angle of rotation in degrees.
   * @param scaleX Scale the text in the horizontal axis. 1.0f means original size.
   * @param scaleY Scale the text in the vertical axis. 1.0f means original size.
   * @see Text#render(Screen, float, float)
   * @see Text#render(Screen, float, float, float)
   * @see Text#render(Screen, float, float, float, float, float, float)
   */
  public void render(Screen screen, float x, float y, float angle, float scaleX, float scaleY) {
    render(screen, x, scaleY, angle, scaleX, scaleY, 1.0f);
  }
  
  /**
   * Render the text.
   * @param screen Screen on which the text will be rendered.
   * @param x Coordinate x of the center of the text.
   * @param y Coordinate y of the center of the text.
   * @param angle Angle of rotation in degrees.
   * @param scaleX Scale the text in the horizontal axis. 1.0f means original size.
   * @param scaleY Scale the text in the vertical axis. 1.0f means original size.
   * @param opacity The opacity. 1.0f means opaque, 0.0f means transparent.
   * @see Text#render(Screen, float, float)
   * @see Text#render(Screen, float, float, float)
   * @see Text#render(Screen, float, float, float, float, float)
   */
  public void render(Screen screen, float x, float y, float angle, float scaleX, float scaleY, float opacity) {
    color.a = opacity;
    
    GL11.glLoadIdentity();
    GL11.glTranslatef(x, y, 0.0f);
    GL11.glRotatef(angle, 0f, 0f, 1.0f);
    GL11.glScalef(scaleX, scaleY, 0.0f);
    GL11.glTranslatef(-ttfFont.getWidth(text)/2, -ttfFont.getHeight(text)/2, 0.0f);
    
    ttfFont.drawString(0.0f, 0.0f, text, color);
  }
  
  /**
   * Set options.
   * @param style Font style, for Java default fonts.
   * @param size Font size.
   * @param antiAlias Apply anti-aliasing when rendering.
   * @param color Color to render.
   */
  public void options(Integer style, Float size, Boolean antiAlias, Color color) {
    boolean changed = false;
    if (style != null) {
      awtFont = awtFont.deriveFont(style);
      changed = true;
    }
    if (size != null) {
      awtFont = awtFont.deriveFont(size);
      changed = true;
    }
    if (antiAlias != null) {
      this.antiAlias = antiAlias;
      changed = true;
    }
    if (color != null)
      this.color = color;
    if (changed)
      ttfFont = new TrueTypeFont(awtFont, this.antiAlias);
  }
  
  private Font awtFont;
  private TrueTypeFont ttfFont;
  private String text;
  private boolean antiAlias;
  private Color color;
}
