package org.unbiquitous.uImpala.dalvik.impl.io;

import org.unbiquitous.uImpala.engine.io.KeyboardSource;
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
  
  @Override
  public void open(String t, int w, int h, boolean f, String i, boolean gl) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String getTitle() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setTitle(String title) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public int getWidth() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getHeight() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void setSize(int width, int height) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean isFullscreen() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setFullscreen(boolean fullscreen) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String getIcon() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setIcon(String icon) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean isCloseRequested() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public MouseSource getMouse() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public KeyboardSource getKeyboard() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void start() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void stop() {
    // TODO Auto-generated method stub
    
  }

  @Override
  protected void update() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void close() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean isUpdating() {
    // TODO Auto-generated method stub
    return false;
  }
  
}
