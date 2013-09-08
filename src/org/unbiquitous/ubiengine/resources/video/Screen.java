package org.unbiquitous.ubiengine.resources.video;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
      
      public RenderImage(Image src, int x, int y, boolean center) {
        super(x, y, center);
        this.src = src;
      }
      
      public void render(Graphics2D g2d) {
        if (center) {
          x -= src.getWidth(null)/2;
          y -= src.getHeight(null)/2;
        }
        g2d.drawImage(src, x, y, null);
      }
    }
    
    private static class RenderText extends RenderOperation {//FIXME
      private Font font;
      private String str;
      
      public RenderText(String str, Font font, int x, int y, boolean center) {
        super(x, y, center);
        this.str = str;
        this.font = font;
      }
      
      public void render(Graphics2D g2d) {
        Font save;
        if (font != null) {
          save = g2d.getFont();
          g2d.setFont(font);
          g2d.setColor(Color.WHITE);
          if (center) {
            FontMetrics metrics = g2d.getFontMetrics(font);
            x -= metrics.stringWidth(str)/2;
            y -= metrics.getHeight()/2;
          }
          g2d.drawString(str, x, y);
          g2d.setFont(save);
        }
        else {
          g2d.setColor(Color.WHITE);
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
      while (!render_ops.isEmpty()) {
        render_ops.poll().render(g2d);
      }
    }
    
    public void renderImage(Image src, int x, int y, boolean center) {
      render_ops.add(new RenderImage(src, x, y, center));
    }
    
    public void renderText(String str, Font font, int x, int y, boolean center) {
      render_ops.add(new RenderText(str, font, x, y, center));
    }
  }
  
  private JFrame window;
  private ScreenJPanel screen;
  private boolean show_fps;
  private DeltaTime deltatime;
  public boolean quit_requested;
  
  public Screen(String title, int width, int height, DeltaTime dt) {
    window = new JFrame();
    screen = new ScreenJPanel();
    show_fps = false;
    deltatime = dt;
    quit_requested = false;
    
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
    //FIXME show FPS FIXME
    if (show_fps)
      screen.renderText(String.format("FPS: %.2f", deltatime.getRealFPS()), null, 950, 15, false);
    
    screen.repaint();
  }

  public void setSize(int w, int h) {
    screen.setPreferredSize(new Dimension(w, h));
    window.pack();
  }
  
  public void showFPS(boolean enable) {
    show_fps = enable;
  }
  
  public void renderImage(Image src, int x, int y, boolean center) {
    screen.renderImage(src, x, y, center);
  }
  
  public void renderText(String text, Font font, int x, int y, boolean center) {
    screen.renderText(text, font, x, y, center);
  }
  
  public void addKeyListener(KeyListener l) {
    window.addKeyListener(l);
  }
  
  public void addMouseListener(MouseListener l) {
    window.addMouseListener(l);
  }
  
  public void addMouseMotionListener(MouseMotionListener l) {
    window.addMouseMotionListener(l);
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
