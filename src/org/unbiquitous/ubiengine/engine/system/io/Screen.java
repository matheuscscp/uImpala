package org.unbiquitous.ubiengine.engine.system.io;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.unbiquitous.ubiengine.util.mathematics.Rectangle;

public final class Screen implements WindowListener {
  @SuppressWarnings("serial")
  private static final class ScreenJPanel extends JPanel {
    private static abstract class RenderOperation {
      protected int x, y;
      protected boolean center;
      protected float alpha;
      
      public RenderOperation(int x, int y, boolean center, float alpha) {
        this.x = x;
        this.y = y;
        this.center = center;
        this.alpha = (alpha < 0.0f || alpha > 1.0f ? 1.0f : alpha);
      }
      
      public abstract void render(Graphics2D g2d);
    }
    
    private static class RenderImage extends RenderOperation {
      private Image src;
      private int rect_x, rect_y, rect_w, rect_h;
      
      public RenderImage(int x, int y, boolean center, float alpha, Image src, Rectangle clip_rect) {
        super(x, y, center, alpha);
        this.src = src;
        rect_x = (int) clip_rect.getX();
        rect_y = (int) clip_rect.getY();
        rect_w = (int) clip_rect.getW();
        rect_h = (int) clip_rect.getH();
        if (center) {
          this.x -= rect_w/2;
          this.y -= rect_h/2;
        }
      }
      
      public void render(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(
          src,
          x, y, x + rect_w, y + rect_h,
          rect_x, rect_y, rect_x + rect_w, rect_y + rect_h,
          null
        );
      }
    }
    
    private static class RenderText extends RenderOperation {
      private Font font;
      private Color color;
      private String str;
      
      public RenderText(int x, int y, boolean center, float alpha, String str, Font font, Color color) {
        super(x, y, center, alpha);
        this.str = str;
        this.font = (font == null ? new Font(Font.MONOSPACED, Font.BOLD, 20) : font);
        this.color = (color == null ? Color.WHITE : color);
      }
      
      public void render(Graphics2D g2d) {
        FontMetrics fm = g2d.getFontMetrics(font);
        if (center) {
          x -= fm.stringWidth(str)/2;
          y -= fm.getHeight()/2;
        }
        
        g2d.setFont(font);
        g2d.setColor(color);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawString(str, x, y + (int) (0.69f*fm.getHeight()));
      }
    }
    
    private Queue<RenderOperation> render_ops;
    
    public ScreenJPanel() {
      render_ops = new LinkedList<RenderOperation>();
    }
    
    public void paint(Graphics g) {
      Graphics2D g2d = (Graphics2D) g;
      while (!render_ops.isEmpty()) {
        render_ops.poll().render(g2d);
      }
    }
    
    public void renderImage(int x, int y, boolean center, float alpha, Image src, Rectangle clip_rect) {
      render_ops.add(new RenderImage(x, y, center, alpha, src, clip_rect));
    }
    
    public void renderText(int x, int y, boolean center, float alpha, String str, Font font, Color color) {
      render_ops.add(new RenderText(x, y, center, alpha, str, font, color));
    }
  }
  
  private JFrame window;
  private ScreenJPanel screen;
  private boolean show_fps;
  private boolean window_closing;
  private int width;
  
  public Screen(String title, int w, int h) {
    window = new JFrame();
    screen = new ScreenJPanel();
    show_fps = false;
    window_closing = false;
    width = w;
    
    screen.setPreferredSize(new Dimension(w, h));
    window.add(screen);
    window.setResizable(false);
    window.pack();
    window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    window.setLocationRelativeTo(null);
    window.setTitle(title);
    window.addWindowListener(this);
    window.setVisible(true);
  }
  
  public void close() {
    window.dispose();
    screen = null;
    window = null;
  }
  
  public void update() {
    if (show_fps)
      screen.renderText(width - 100, 0, false, 1.0f, String.format("%.1f FPS", dt), null, null);
    
    screen.repaint();
  }

  public Dimension getSize() {
    return screen.getSize();
  }
  
  public void setSize(int w, int h) {
    screen.setPreferredSize(new Dimension(w, h));
    window.pack();
    this.width = w;
  }
  
  public void showFPS(boolean enable) {
    show_fps = enable;
  }
  
  public void renderImage(int x, int y, boolean center, float alpha, Image src, Rectangle clip_rect) {
    screen.renderImage(x, y, center, alpha, src, clip_rect);
  }
  
  public void renderText(int x, int y, boolean center, float alpha, String text, Font font, Color color) {
    screen.renderText(x, y, center, alpha, text, font, color);
  }
  
  public Dimension getTextSize(String text, Font font) {
    FontMetrics metrics = ((Graphics2D) screen.getGraphics()).getFontMetrics(font);
    return new Dimension(metrics.stringWidth(text), metrics.getHeight());
  }
  
  public void addKeyListener(KeyListener l) {
    window.addKeyListener(l);
  }
  
  public void addMouseListener(MouseListener l) {
    screen.addMouseListener(l);
  }
  
  public void addMouseMotionListener(MouseMotionListener l) {
    screen.addMouseMotionListener(l);
  }

  public void windowActivated(WindowEvent arg0) {
    
  }

  public void windowClosed(WindowEvent arg0) {
    
  }

  public void windowClosing(WindowEvent arg0) {
    window_closing = true;
  }

  public boolean windowClosing() {
    boolean tmp = window_closing;
    window_closing = false;
    return tmp;
  }

  public void windowDeactivated(WindowEvent arg0) {
    
  }

  public void windowDeiconified(WindowEvent arg0) {
    
  }

  public void windowIconified(WindowEvent arg0) {
    
  }

  public void windowOpened(WindowEvent arg0) {
    
  }
}
