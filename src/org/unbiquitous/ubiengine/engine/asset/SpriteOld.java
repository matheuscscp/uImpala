package org.unbiquitous.ubiengine.engine.asset;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.unbiquitous.ubiengine.engine.system.Window;
import org.unbiquitous.ubiengine.util.mathematics.geometry.Rectangle;

public class SpriteOld {
  protected Window window;
  protected BufferedImage sprite;
  protected BufferedImage rotozoomed;
  protected Graphics2D g2d;
  protected int w, h;
  protected Rectangle clip_rect;
  protected float alpha;
  protected float angle;
  protected float scalex, scaley;

  public SpriteOld(Window scr, String filename) {
    window = scr;
    try {
      sprite = ImageIO.read(new File(filename));
    } catch (IOException e) {
      throw new Error(e);
    }
    rotozoomed = null;
    g2d = sprite.createGraphics();
    w = sprite.getWidth();
    h = sprite.getHeight();
    clip_rect = new Rectangle(0, 0, w, h);
    alpha = 1.0f;
    angle = 0.0f;
    scalex = 1.0f;
    scaley = 1.0f;
  }

  public SpriteOld(Window window, int w, int h, boolean use_alpha) {
    this.window = window;
    sprite = new BufferedImage(
      w, h,
      use_alpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB
    );
    rotozoomed = null;
    g2d = sprite.createGraphics();
    this.w = w;
    this.h = h;
    clip_rect = new Rectangle(0, 0, w, h);
    alpha = 1.0f;
    angle = 0.0f;
    scalex = 1.0f;
    scaley = 1.0f;
  }
  
  public SpriteOld(Window window, TextOld other) {
    this.window = window;
    FontMetrics fm =
      new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
      .createGraphics().getFontMetrics(other.getFont());
    w = fm.stringWidth(other.getText());
    h = fm.getHeight();
    clip_rect = new Rectangle(0, 0, w, h);
    sprite = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    rotozoomed = null;
    g2d = sprite.createGraphics();
    alpha = 1.0f;
    angle = 0.0f;
    scalex = 1.0f;
    scaley = 1.0f;
    
    render(other, 0, 0, false);
  }
  
  public void render() {
    window.renderImage(0, 0, false, alpha, sprite, clip_rect);
  }
  
  public void render(int x, int y, boolean center) {
    if (!center) {
      if (rotozoomed != null) {
        x += ((int) clip_rect.getW() - rotozoomed.getWidth())/2;
        y += ((int) clip_rect.getH() - rotozoomed.getHeight())/2;
      }
    }
    else {
      if (rotozoomed == null) {
        x -= ((int) clip_rect.getW())/2;
        y -= ((int) clip_rect.getH())/2;
      }
      else {
        x -= rotozoomed.getWidth()/2;
        y -= rotozoomed.getHeight()/2;
      }
    }
    
    if (rotozoomed == null)
      window.renderImage(x, y, false, alpha, sprite, clip_rect);
    else
      window.renderImage(
        x, y, false, alpha, rotozoomed,
        new Rectangle(0, 0, rotozoomed.getWidth(), rotozoomed.getHeight())
      );
  }

  public void render(SpriteOld other) {
    render(other, 0, 0, false);
  }

  public void render(SpriteOld other, int x, int y, boolean center) {
    int other_rect_x = (int) other.clip_rect.getX();
    int other_rect_y = (int) other.clip_rect.getY();
    int other_rect_w = (int) other.clip_rect.getW();
    int other_rect_h = (int) other.clip_rect.getH();

    if (center) {
      x -= other_rect_w/2;
      y -= other_rect_h/2;
    }
    
    // if second corner on the left or above
    if (x + other_rect_w < 0 || y + other_rect_h < 0)
      return;
    
    int rect_x = (int) clip_rect.getX();
    int rect_y = (int) clip_rect.getY();
    int rect_w = (int) clip_rect.getW();
    int rect_h = (int) clip_rect.getH();
    
    // if first corner on the right or below
    if (x >= rect_w || y >= rect_h)
      return;
    
    int dx1 = Math.max(rect_x, rect_x + x);
    int dy1 = Math.max(rect_y, rect_y + y);
    int dx2 = Math.min(rect_x + rect_w, rect_x + x + other_rect_w);
    int dy2 = Math.min(rect_y + rect_h, rect_y + y + other_rect_h);
    int sx1 = Math.max(other_rect_x, other_rect_x - x);
    int sy1 = Math.max(other_rect_y, other_rect_y - y);
    int sx2 = Math.min(other_rect_x + other_rect_w, other_rect_x - x + rect_w);
    int sy2 = Math.min(other_rect_y + other_rect_h, other_rect_y - y + rect_h);
    
    g2d.setComposite(
      AlphaComposite.getInstance(
        AlphaComposite.SRC_OVER,
        other.alpha < 0.0f || other.alpha > 1.0f ? 1.0f : other.alpha
      )
    );
    g2d.drawImage(other.sprite, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
  }

  public void render(TextOld other) {
    render(other, 0, 0, false);
  }

  public void render(TextOld other, int x, int y, boolean center) {
    // rendering text in a temporary BufferedImage
    Font font = other.getFont();
    FontMetrics fm =
      new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
      .createGraphics().getFontMetrics(font);
    String text = other.getText();
    int w = fm.stringWidth(text);
    int h = fm.getHeight();
    BufferedImage tmp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics2D tmp_g2d = tmp.createGraphics();
    tmp_g2d.setFont(font);
    tmp_g2d.setColor(other.getColor());
    tmp_g2d.setRenderingHint(
      RenderingHints.KEY_TEXT_ANTIALIASING,
      RenderingHints.VALUE_TEXT_ANTIALIAS_ON
    );
    tmp_g2d.drawString(text, 0, (int) (0.69f*h));
    
    // create a temporary sprite with the temporary BufferedImage
    SpriteOld tmpspr = new SpriteOld(window, w, h, true);
    tmpspr.sprite = tmp;
    tmpspr.alpha = other.getAlpha();
    
    render(tmpspr, x, y, center);
  }
  
  public void rotozoom(float ang, float scx, float scy, boolean force) {
    if (ang != angle || scx != scalex || scy != scaley || force) {
      angle = ang;
      scalex = scx;
      scaley = scy;
      rotozoom();
    }
  }
  
  private void rotozoom() {
    restore();
    
    if (!isClipped())
      rotozoomed = rotozoom(sprite);
    else
      rotozoomed = rotozoom(clip());
  }
  
  private BufferedImage rotozoom(BufferedImage src) {//FIXME implement rotation
    int w = (int) (scalex*(float)src.getWidth());
    int h = (int) (scaley*(float)src.getHeight());
    if (w <= 0)
      w = 1;
    if (h <= 0)
      h = 1;
    Image transf = src.getScaledInstance(w, h, BufferedImage.SCALE_SMOOTH);
    
    BufferedImage tmp = new BufferedImage(
      transf.getWidth(null),
      transf.getHeight(null),
      src.getType()
    );
    tmp.createGraphics().drawImage(transf, 0, 0, null);
    return tmp;
  }
  
  public void restore() {
    rotozoomed = null;
  }
  
  public int getWidth() {
    return w;
  }
  
  public int getHeight() {
    return h;
  }
  
  public void resetClip() {
    clip_rect = new Rectangle(0, 0, w, h);
  }
  
  public void clip(int x, int y, int w, int h) {
    clip_rect = new Rectangle(x, y, w, h);
  }
  
  public void clip(Rectangle clip_rect) {
    this.clip_rect = new Rectangle(clip_rect);
  }
  
  private BufferedImage clip() {
    int rect_x = (int) clip_rect.getX();
    int rect_y = (int) clip_rect.getY();
    int rect_w = (int) clip_rect.getW();
    int rect_h = (int) clip_rect.getH();
    
    BufferedImage tmp = new BufferedImage(rect_w, rect_h, sprite.getType());
    Graphics2D g2d = tmp.createGraphics();
    
    g2d.drawImage(
      sprite,
      0, 0, rect_w, rect_h,
      rect_x, rect_y, rect_x + rect_w, rect_y + rect_h,
      null
    );
    
    return tmp;
  }
  
  public Rectangle getClipRect() {
    return clip_rect;
  }
  
  public boolean isClipped() {
    return (
      (int) clip_rect.getX() != 0 ||
      (int) clip_rect.getY() != 0 ||
      (int) clip_rect.getW() != sprite.getWidth() ||
      (int) clip_rect.getH() != sprite.getHeight()
    );
  }
  
  public float getAlpha() {
    return alpha;
  }
  
  public void setAlpha(float alpha) {
    this.alpha = alpha;
  }

  public float getAngle() {
    return angle;
  }

  public float getScaleX() {
    return scalex;
  }

  public float getScaleY() {
    return scaley;
  }

  public void update() {
    
  }
}
