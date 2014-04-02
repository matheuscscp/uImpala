package org.unbiquitous.uImpala.jse.impl.asset;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.unbiquitous.uImpala.engine.io.Screen;
import org.unbiquitous.uImpala.util.Corner;

public class Text extends org.unbiquitous.uImpala.engine.asset.Text {
  protected Text(Font font, String text) {
    awtFont = font;
    this.text = text;
    init();
  }
  
  public void setText(String text) {
    this.text = text;
    setSize();
  }
  
  public void render(Screen screen, float x, float y, Corner corner, float opacity, float angle, float scaleX, float scaleY) {
    color.a = opacity;
    
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
    GL11.glRotatef(angle, 0f, 0f, 1.0f);
    GL11.glScalef(scaleX, scaleY, 0.0f);
    GL11.glTranslatef(-halfWidth, -halfHeight, 0.0f);
    
    ttfFont.drawString(0.0f, 0.0f, text, color);
  }
  
  public void options(Integer style, Float size, Boolean antiAlias, org.unbiquitous.uImpala.util.Color color) {
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
      this.color = new Color(color.r, color.g, color.b, color.a);
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
  private float halfWidth, halfHeight;
}
