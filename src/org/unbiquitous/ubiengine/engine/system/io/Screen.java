package org.unbiquitous.ubiengine.engine.system.io;

import java.util.Arrays;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * Class for screen resource.
 * @author Pimenta
 *
 */
public final class Screen extends OutputResource {
  /**
   * Open the screen.
   * @param t Title.
   * @param w Width.
   * @param h Height.
   * @param f Fullscreen flag.
   * @param i Icon path.
   */
  public void open(String t, int w, int h, boolean f, String i) {
    open = true;
    try {
      setTitle(t);
      setDisplayMode(w, h, f);
      setIcon(i);
      Display.create();
    } catch (Throwable e) {
      open = false;
      throw new Error(e);
    }
  }
  
  public String getTitle() {
    return title;
  }
  
  public void setTitle(String title) {
    if (title == null)
      return;
    if (!open)
      throw new Error("Impossible to set title, screen is not open");
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
    setDisplayMode(width, height, fullscreen);
  }
  
  public boolean isFullscreen() {
    return fullscreen;
  }
  
  public void setFullscreen(boolean fullscreen) {
    setDisplayMode(width, height, fullscreen);
  }
  
  private void setDisplayMode(int w, int h, boolean full) {
    if (!open)
      throw new Error("Cannot set size or fullscreen if screen is not open");
    
    DisplayMode mode = new DisplayMode(w, h);
    
    // if fullscreen, determines the closer available display mode
    if (full) {
      DisplayMode[] dms = null;
      try {
        dms = Display.getAvailableDisplayModes();
      } catch (LWJGLException e) {
        throw new Error(e);
      }
      ComparableDisplayMode[] cdms = new ComparableDisplayMode[dms.length];
      int i = 0;
      for (DisplayMode dm : dms)
        cdms[i++] = new ComparableDisplayMode(dm, w, h);
      Arrays.sort(cdms);
      mode = cdms[0].displayMode;
    }
    
    try {
      Display.setDisplayMode(mode);
      Display.setFullscreen(full);
    } catch (LWJGLException e) {
      throw new Error(e);
    }
    
    width = w;
    height = h;
    fullscreen = full;
  }
  
  public String getIcon() {
    return icon;
  }
  
  public void setIcon(String icon) {
    if (icon == null)
      return;
    if (!open || icon.length() == 0)
      throw new Error("Impossible to set icon. Screen not open or invalid icon path");
    Display.setIcon(IconLoader.load(icon));
    this.icon = icon;
  }
  
  protected void update() {
    if (!open)
      return;
    //TODO
    Display.update();
  }
  
  public void close() {
    if (!open)
      return;
    Display.destroy();
    open = false;
  }
  
  public boolean isUpdating() {
    return true;
  }
  
  /**
   * Engine's private use.
   */
  protected MouseSource mouse = new MouseSource(Mouse.getButtonCount());
  
  /**
   * Engine's private use.
   */
  protected KeyboardSource keyboard = new KeyboardSource();
  
  public MouseSource getMouse() {
    return mouse;
  }
  
  public KeyboardSource getKeyboard() {
    return keyboard;
  }
  
  private String title = null;
  private int width = 0;
  private int height = 0;
  private boolean fullscreen = false;
  private String icon = null;
  
  private boolean open = false;
}
