package org.unbiquitous.ubiengine.engine.asset;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import org.unbiquitous.ubiengine.engine.system.screen.Window;

public class Text {
  private Window window;
  private String text;
  private Font font;
  private Color color;
  private Dimension dimension;
  private float alpha;

  public Text(Window window, String text, Font font, Color color) {
    this.window = window;
    this.text = text;
    this.font = (font == null ? new Font(Font.MONOSPACED, Font.BOLD, 20) : font);
    this.color = (color == null ? Color.WHITE : color);
    dimension = window.getTextSize(this.text, this.font);
    alpha = 1.0f;
  }
  
  public void render() {
    window.renderText(0, 0, false, alpha, text, font, color);
  }
  
  public void render(int x, int y, boolean center) {
    window.renderText(x, y, center, alpha, text, font, color);
  }
  
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
    dimension = window.getTextSize(this.text, this.font);
  }

  public Font getFont() {
    return font;
  }

  public void setFont(Font font) {
    this.font = (font == null ? new Font(Font.MONOSPACED, Font.BOLD, 20) : font);
    dimension = window.getTextSize(this.text, this.font);
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = (color == null ? Color.WHITE : color);
  }

  public int getWidth() {
    return (int) dimension.getWidth();
  }
  
  public int getHeight() {
    return (int) dimension.getHeight();
  }
  
  public float getAlpha() {
    return alpha;
  }
  
  public void setAlpha(float alpha) {
    this.alpha = alpha;
  }
}
