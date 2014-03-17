package org.unbiquitous.uImp67.engine.asset;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.unbiquitous.uImp67.engine.io.Screen;

public class Text {
  public Text(Font font, String text, boolean antiAlias, Color color) {
    awtFont = font;
    this.text = text;
    this.antiAlias = antiAlias;
    this.color = color == null ? Color.white : color;
    ttfFont = new TrueTypeFont(awtFont, antiAlias);
  }
  
  public void setText(String text) {
    this.text = text;
  }
  
  public void render(Screen screen, float x, float y, float angle, float scaleX, float scaleY, float opacity) {
    color.a = opacity;
    
    GL11.glLoadIdentity();
    GL11.glTranslatef(x, y, 0.0f);
    GL11.glRotatef(angle, 0f, 0f, 1.0f);
    GL11.glScalef(scaleX, scaleY, 0.0f);
    GL11.glTranslatef(-ttfFont.getWidth(text)/2, -ttfFont.getHeight(text)/2, 0.0f);
    
    ttfFont.drawString(0.0f, 0.0f, text, color);
  }
  
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
