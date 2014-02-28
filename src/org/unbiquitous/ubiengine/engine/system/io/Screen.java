package org.unbiquitous.ubiengine.engine.system.io;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * Class for screen resource.
 * @author Pimenta
 *
 */
public final class Screen extends IOResource {
  /**
   * Open the screen.
   * @param t Title.
   * @param w Width.
   * @param h Height.
   * @param f Fullscreen flag.
   * @param i Icon path.
   */
  public void open(String t, int w, int h, boolean f, String i) {
    try {
      Display.setDisplayMode(new DisplayMode(w, h));
      Display.setTitle(t);
      Display.setFullscreen(f);
      if (i != null)
        Display.setIcon(IconLoader.load(i));
      if (!open)
        Display.create();
    } catch (LWJGLException e) {
      throw new Error(e);
    }
    title = t;
    width = w;
    height = h;
    fullscreen = f;
    icon = i;
    open = true;
  }
  
  public String getTitle() {
    return title;
  }
  
  public void setTitle(String title) {
    if (!open)
      return;
    Display.setTitle(title);
    this.title = title;
  }
  
  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }
  
  public void setSize(int width, int height) {
    if (!open || (width == this.width && height == this.height))
      return;
    try {
      Display.setDisplayMode(new DisplayMode(width, height));
    } catch (LWJGLException e) {
      throw new Error(e);
    }
    this.width = width;
    this.height = height;
  }
  
  public boolean isFullscreen() {
    return fullscreen;
  }
  
  public void setFullscreen(boolean fullscreen) {
    if (!open)
      return;
    try {
      Display.setFullscreen(fullscreen);
    } catch (LWJGLException e) {
      throw new Error(e);
    }
    this.fullscreen = fullscreen;
  }
  
  public String getIcon() {
    return icon;
  }
  
  public void setIcon(String icon) {
    if (!open)
      return;
    if (icon != null)
      Display.setIcon(IconLoader.load(icon));
    this.icon = icon;
  }
  
  protected void update() {//FIXME
    //TODO
    Display.update();
  }
  
  public void close() {//FIXME
    if (open) {
      Display.destroy();
      open = false;
    }
  }
  
  public boolean isUpdating() {//FIXME
    return true;
  }
  
  /**
   * Engine's private use.
   */
  protected MouseSource mouse = new MouseSource();
  
  /**
   * Engine's private use.
   */
  protected KeyboardSource keyboard = new KeyboardSource();
  
  private String title;
  private int width;
  private int height;
  private boolean fullscreen;
  private String icon;
  
  private boolean open = false;
}
