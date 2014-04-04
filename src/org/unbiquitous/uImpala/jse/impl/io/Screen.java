package org.unbiquitous.uImpala.jse.impl.io;

public final class Screen extends org.unbiquitous.uImpala.engine.io.Screen {
  private static final class Factory implements org.unbiquitous.uImpala.engine.io.Screen.Factory {
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
}
