package org.unbiquitous.ubiengine.engine.resources.video.texture;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import org.unbiquitous.ubiengine.engine.resources.video.Screen;

public class Text {
  private Screen screen;
  private String text;
  private Font font;
  private Color color;
  private Dimension dimension;
  private float alpha;

  public Text(Screen screen, String text, Font font, Color color) {
    this.screen = screen;
    this.text = text;
    this.font = (font == null ? new Font(Font.MONOSPACED, Font.BOLD, 20) : font);
    this.color = (color == null ? Color.WHITE : color);
    dimension = screen.getTextSize(this.text, this.font);
    alpha = 1.0f;
  }
  
  public void render() {
    screen.renderText(0, 0, false, alpha, text, font, color);
  }
  
  public void render(int x, int y, boolean center) {
    screen.renderText(x, y, center, alpha, text, font, color);
  }
  
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
    dimension = screen.getTextSize(this.text, this.font);
  }

  public Font getFont() {
    return font;
  }

  public void setFont(Font font) {
    this.font = (font == null ? new Font(Font.MONOSPACED, Font.BOLD, 20) : font);
    dimension = screen.getTextSize(this.text, this.font);
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
