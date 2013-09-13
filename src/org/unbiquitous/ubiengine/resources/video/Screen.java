package org.unbiquitous.ubiengine.resources.video;

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

import org.unbiquitous.ubiengine.resources.time.DeltaTime;
import org.unbiquitous.ubiengine.util.mathematics.geometry.Rectangle;

public final class Screen implements WindowListener {
  @SuppressWarnings("serial")
  private static final class ScreenJPanel extends JPanel {
    private static abstract class RenderOperation {
      protected int x, y;
      protected boolean center;
      
      public RenderOperation(int x, int y, boolean center) {
        this.x = x;
        this.y = y;
        this.center = center;
      }
      
      public abstract void render(Graphics2D g2d);
    }
    
    private static class RenderImage extends RenderOperation {
      private Image src;
      private int rect_x, rect_y, rect_w, rect_h;
      
      public RenderImage(int x, int y, boolean center, Image src, Rectangle clip_rect) {
        super(x, y, center);
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
      
      public RenderText(String str, Font font, Color color, int x, int y, boolean center) {
        super(x, y, center);
        this.str = str;
        this.font = font;
        this.color = color;
      }
      
      public void render(Graphics2D g2d) {
        Font font_save;
        Color color_save;
        if (font != null) {
          font_save = g2d.getFont();
          color_save = g2d.getColor();
          g2d.setFont(font);
          g2d.setColor(color != null ? color : Color.WHITE);
          if (center) {
            FontMetrics metrics = g2d.getFontMetrics(font);
            x -= metrics.stringWidth(str)/2;
            y -= metrics.getHeight()/2;
          }
          g2d.drawString(str, x, y);
          g2d.setFont(font_save);
          g2d.setColor(color_save);
        }
        else {
          g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
          g2d.setColor(Color.WHITE);
          if (center) {
            FontMetrics metrics = g2d.getFontMetrics(font);
            x -= metrics.stringWidth(str)/2;
            y -= metrics.getHeight()/2;
          }
          g2d.drawString(str, x, y);
        }
      }
    }
    
    private Queue<RenderOperation> render_ops;
    
    public ScreenJPanel() {
      render_ops = new LinkedList<RenderOperation>();
    }
    
    public void paint(Graphics g) {
      Graphics2D g2d = (Graphics2D) g;
      g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      while (!render_ops.isEmpty()) {
        render_ops.poll().render(g2d);
      }
    }
    
    public void renderImage(int x, int y, boolean center, Image src, Rectangle clip_rect) {
      render_ops.add(new RenderImage(x, y, center, src, clip_rect));
    }
    
    public void renderText(String str, Font font, Color color, int x, int y, boolean center) {
      render_ops.add(new RenderText(str, font, color, x, y, center));
    }
  }
  
  private JFrame window;
  private ScreenJPanel screen;
  private boolean show_fps;
  private DeltaTime deltatime;
  public boolean quit_requested;
  private int w;
  
  public Screen(String title, int width, int height, DeltaTime dt) {
    window = new JFrame();
    screen = new ScreenJPanel();
    show_fps = false;
    deltatime = dt;
    quit_requested = false;
    w = width;
    
    screen.setPreferredSize(new Dimension(width, height));
    window.add(screen);
    window.setResizable(false);
    window.pack();
    window.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
    window.setLocationRelativeTo(null);
    window.setTitle(title);
    window.addWindowListener(this);
    window.setVisible(true);
  }
  
  public void close() {
    screen = null;
    window.dispose();
    window = null;
  }
  
  public void update() {
    if (show_fps)
      screen.renderText(String.format("%.1f FPS", deltatime.getRealFPS()), null, null, w - 100, 18, false);
    
    screen.repaint();
  }

  public void setSize(int w, int h) {
    screen.setPreferredSize(new Dimension(w, h));
    window.pack();
  }
  
  public void showFPS(boolean enable) {
    show_fps = enable;
  }
  
  public void renderImage(int x, int y, boolean center, Image src, Rectangle clip_rect) {
    screen.renderImage(x, y, center, src, clip_rect);
  }
  
  public void renderText(String text, Font font, Color color, int x, int y, boolean center) {
    screen.renderText(text, font, color, x, y, center);
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
    quit_requested = true;
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
