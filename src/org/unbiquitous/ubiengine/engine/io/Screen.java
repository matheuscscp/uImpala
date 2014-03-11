package org.unbiquitous.ubiengine.engine.io;

import java.util.Arrays;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.unbiquitous.ubiengine.engine.core.GameComponents;

/**
 * Class for screen resource.
 * @author Pimenta
 *
 */
public final class Screen extends OutputResource {
  /**
   * Broadcasted when the user requests to close the screen.
   */
  public static final int EVENT_CLOSE_REQUEST = IOResource.LAST_EVENT + 1;
  
  /**
   * The last event of this class.
   */
  public static final int LAST_EVENT          = EVENT_CLOSE_REQUEST;
  
  /**
   * Constructor to setup screen events.
   */
  protected Screen() {
    observations.addEvents(EVENT_CLOSE_REQUEST);
  }
  
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
      mouse = new MouseSource(Mouse.getButtonCount());
      keyboard = new KeyboardSource(Keyboard.KEYBOARD_SIZE);
      addMouseKeyboard();
    } catch (Throwable e) {
      open = false;
      throw new Error(e);
    }
  }
  
  /**
   * Initializes fixed pipeline OpenGL.
   */
  public void initGL() {
    // enable texture
    GL11.glEnable(GL11.GL_TEXTURE_2D);
    
    // enable blend
    GL11.glEnable(GL11.GL_BLEND);
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    
    // backbuffer size
    GL11.glViewport(0, 0, width, height);
    
    // setup projection
    GL11.glMatrixMode(GL11.GL_PROJECTION);
    GL11.glLoadIdentity();
    GL11.glOrtho(0, width, height, 0, -1, 1);
    
    // clears matrix
    GL11.glMatrixMode(GL11.GL_MODELVIEW);
    GL11.glLoadIdentity();
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
    
    width = full ? mode.getWidth() : w;
    height = full ? mode.getHeight() : h;
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
  
  public boolean isCloseRequested() {
    return closeRequested;
  }
  
  protected void update() {
    if (!open)
      return;
    
    // mouse
    while (Mouse.next()) {
      MouseEvent event = new MouseEvent(0, Mouse.getEventX(), Mouse.getEventY(), Mouse.getEventButton());
      if (event.button == -1)
        event.type = MouseSource.EVENT_MOUSE_MOTION;
      else if (Mouse.getEventButtonState())
        event.type = MouseSource.EVENT_BUTTON_DOWN;
      else
        event.type = MouseSource.EVENT_BUTTON_UP;
      mouse.events.add(event);
    }
    
    // keyboard
    while (Keyboard.next()) {
      KeyboardEvent event = new KeyboardEvent(0, Keyboard.getEventKey(), Keyboard.getEventCharacter());
      if (Keyboard.getEventKeyState())
        event.type = KeyboardSource.EVENT_KEY_DOWN;
      else
        event.type = KeyboardSource.EVENT_KEY_UP;
      keyboard.events.add(event);
    }
    
    // screen
    Display.update();
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    closeRequested = Display.isCloseRequested();
    if (closeRequested)
      observations.broadcast(EVENT_CLOSE_REQUEST, null);
  }
  
  public void close() {
    if (!open)
      return;
    removeMouseKeyboard();
    Display.destroy();
    open = false;
    mouse = null;
    keyboard = null;
  }
  
  public boolean isUpdating() {
    return true;
  }
  
  public MouseSource getMouse() {
    return mouse;
  }
  
  public KeyboardSource getKeyboard() {
    return keyboard;
  }
  
  private void addMouseKeyboard() {
    MouseManager mm = GameComponents.get(MouseManager.class);
    if (mm != null && mouse != null)
      mm.screenMouses.add(mouse);
    KeyboardManager km = GameComponents.get(KeyboardManager.class);
    if (km != null && keyboard != null)
      km.screenKeyboards.add(keyboard);
  }
  
  private void removeMouseKeyboard() {
    MouseManager mm = GameComponents.get(MouseManager.class);
    if (mm != null && mouse != null)
      mm.screenMouses.remove(mouse);
    KeyboardManager km = GameComponents.get(KeyboardManager.class);
    if (km != null && keyboard != null)
      km.screenKeyboards.remove(keyboard);
  }
  
  /**
   * Engine's private use.
   */
  protected MouseSource mouse = null;
  
  /**
   * Engine's private use.
   */
  protected KeyboardSource keyboard = null;
  
  private String title = null;
  private int width = 0;
  private int height = 0;
  private boolean fullscreen = false;
  private String icon = null;
  
  private boolean open = false;
  private boolean closeRequested = false;
}
