package org.unbiquitous.uImpala.jse.impl.io;

import java.util.Arrays;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.unbiquitous.uImpala.engine.core.GameComponents;
import org.unbiquitous.uImpala.engine.io.KeyboardEvent;
import org.unbiquitous.uImpala.engine.io.KeyboardManager;
import org.unbiquitous.uImpala.engine.io.KeyboardSource;
import org.unbiquitous.uImpala.engine.io.MouseEvent;
import org.unbiquitous.uImpala.engine.io.MouseManager;
import org.unbiquitous.uImpala.engine.io.MouseSource;

public class Screen extends org.unbiquitous.uImpala.engine.io.Screen {
  private static class Factory implements org.unbiquitous.uImpala.engine.io.Screen.Factory {
    public Screen create() {
      if (screen != null)
        return null;
      screen = new Screen();
      return screen;
    }
  }
  
  public static synchronized void initImpl() {
    if (factory == null)
      factory = new Factory();
  }
  
  private static Screen screen = null;
  
  public void open(String t, int w, int h, boolean f, String i, boolean gl) {
    open = true;
    try {
      setTitle(t);
      setDisplayMode(w, h, f);
      setIcon(i);
      Display.create();
      Display.setVSyncEnabled(true);
      mouse = GameComponents.get(MouseManager.class) != null ? new MouseSource(Mouse.getButtonCount()) : null;
      keyboard = GameComponents.get(KeyboardManager.class) != null ? new KeyboardSource(Keyboard.KEYBOARD_SIZE, null) : null;
      addMouseKeyboard();
      if (gl)
        initLegacyOpenGL();
    } catch (Throwable e) {
      Display.destroy();
      throw new Error(e);
    }
  }
  
  private void initLegacyOpenGL() {
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
    if (!open)
      throw new Error("Impossible to set icon. Screen not open");
    Display.setIcon(IconLoader.load(icon));
    this.icon = icon;
  }
  
  public boolean isCloseRequested() {
    return closeRequested;
  }
  
  public void start() {
    
  }
  
  public void stop() {
    
  }
  
  protected void update() {
    if (!open)
      return;
    
    // mouse
    while (Mouse.next()) {
      int butt = Mouse.getEventButton();
      MouseEvent event;
      if (butt == -1)
        event = new MouseEvent(MouseSource.EVENT_MOUSE_MOTION, Mouse.getEventX(), height - 1 - Mouse.getEventY(), butt);
      else if (Mouse.getEventButtonState())
        event = new MouseEvent(MouseSource.EVENT_BUTTON_DOWN, Mouse.getEventX(), height - 1 - Mouse.getEventY(), butt);
      else
        event = new MouseEvent(MouseSource.EVENT_BUTTON_UP, Mouse.getEventX(), height - 1 - Mouse.getEventY(), butt);
      mouse.add(event);
    }
    
    // keyboard
    while (Keyboard.next()) {
      KeyboardEvent event;
      if (Keyboard.getEventKeyState())
        event = new KeyboardEvent(KeyboardSource.EVENT_KEY_DOWN, Keyboard.getEventKey(), Keyboard.getEventCharacter());
      else
        event = new KeyboardEvent(KeyboardSource.EVENT_KEY_UP, Keyboard.getEventKey(), Keyboard.getEventCharacter());
      keyboard.add(event);
    }
    
    // screen
    Display.update();
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    closeRequested = Display.isCloseRequested();
    if (closeRequested)
      observations.broadcast(EVENT_CLOSE_REQUEST);
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
    if (mouse != null)
      GameComponents.get(MouseManager.class).add(mouse);
    if (keyboard != null)
      GameComponents.get(KeyboardManager.class).add(keyboard);
  }
  
  private void removeMouseKeyboard() {
    if (mouse != null)
      GameComponents.get(MouseManager.class).remove(mouse);
    if (keyboard != null)
      GameComponents.get(KeyboardManager.class).remove(keyboard);
  }
  
  protected MouseSource mouse = null;
  protected KeyboardSource keyboard = null;
  
  private String title = null;
  private int width = 0;
  private int height = 0;
  private boolean fullscreen = false;
  private String icon = null;
  
  private boolean open = false;
  private boolean closeRequested = false;
}
