package org.unbiquitous.uImpala.engine.asset;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.unbiquitous.uImpala.engine.io.Screen;
import org.unbiquitous.uImpala.util.Corner;

/**
 * Class to implement text rendering.
 * @author Pimenta
 *
 */
public class Text {
  /**
   * Constructor to load a font from a file.
   * @param assets Object to load the font.
   * @param path Font path.
   * @param text String text.
   */
  public Text(AssetManager assets, String fontPath, String text) {
    awtFont = assets.getFont(fontPath).deriveFont(24f);
    this.text = text;
    init();
  }
  
  /**
   * Assigment constructor.
   * @param font Java font.
   * @param text String text.
   */
  public Text(Font font, String text) {
    awtFont = font;
    this.text = text;
    init();
  }
  
  /**
   * Set text to render.
   * @param text String text.
   */
  public void setText(String text) {
    this.text = text;
    setSize();
  }
  
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
    render(screen, x, y, Corner.CENTER, 0.0f, 1.0f, 1.0f, 1.0f);
  }
  
  /**
   * Render the text.
   * @param screen Screen on which the text will be rendered.
   * @param x Coordinate x of the corner of the drawn text.
   * @param y Coordinate y of the corner of the drawn text.
   * @param corner Corner of drawn text.
   */
  public void render(Screen screen, float x, float y, Corner corner) {
    render(screen, x, y, corner, 0.0f, 1.0f, 1.0f, 1.0f);
  }
  
  /**
   * Render the text.
   * @param screen Screen on which the text will be rendered.
   * @param x Coordinate x of the corner of the drawn text.
   * @param y Coordinate y of the corner of the drawn text.
   * @param corner Corner of drawn text.
   * @param angle Angle of rotation in degrees.
   */
  public void render(Screen screen, float x, float y, Corner corner, float angle) {
    render(screen, x, y, corner, angle, 1.0f, 1.0f, 1.0f);
  }
  
  /**
   * Render the text.
   * @param screen Screen on which the text will be rendered.
   * @param x Coordinate x of the corner of the drawn text.
   * @param y Coordinate y of the corner of the drawn text.
   * @param corner Corner of drawn text.
   * @param angle Angle of rotation in degrees.
   * @param scaleX Scale the text in the horizontal axis. 1.0f means original size.
   * @param scaleY Scale the text in the vertical axis. 1.0f means original size.
   */
  public void render(Screen screen, float x, float y, Corner corner, float angle, float scaleX, float scaleY) {
    render(screen, x, y, corner, angle, scaleX, scaleY, 1.0f);
  }
  
  /**
   * Render the text.
   * @param screen Screen on which the text will be rendered.
   * @param x Coordinate x of the corner of the drawn text.
   * @param y Coordinate y of the corner of the drawn text.
   * @param corner Corner of drawn text.
   * @param angle Angle of rotation in degrees.
   * @param scaleX Scale the text in the horizontal axis. 1.0f means original size.
   * @param scaleY Scale the text in the vertical axis. 1.0f means original size.
   * @param opacity The opacity. 1.0f means opaque, 0.0f means transparent.
   */
  public void render(Screen screen, float x, float y, Corner corner, float angle, float scaleX, float scaleY, float opacity) {
    color.a = opacity;
    
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
    GL11.glRotatef(angle, 0f, 0f, 1.0f);
    GL11.glScalef(scaleX, scaleY, 0.0f);
    GL11.glTranslatef(-halfWidth, -halfHeight, 0.0f);
    
    ttfFont.drawString(0.0f, 0.0f, text, color);
  }
  
  /**
   * Set options. Null arguments won't be considered.
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
    if (changed) {
      ttfFont = new TrueTypeFont(awtFont, this.antiAlias);
      setSize();
    }
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  private void init() {
    antiAlias = true;
    color = Color.white;
    ttfFont = new TrueTypeFont(awtFont, antiAlias);
    setSize();
  }
  
  private void setSize() {
    width = ttfFont.getWidth(text);
    height = ttfFont.getHeight(text);
    halfWidth = width/2f;
    halfHeight = height/2f;
  }
  
  private Font awtFont;
  private TrueTypeFont ttfFont;
  private String text;
  private boolean antiAlias;
  private Color color;
  private int width, height;
  private float halfWidth, halfHeight;
}
