package org.unbiquitous.uImpala.jse.impl.io;

public final class Screen extends org.unbiquitous.uImpala.engine.io.Screen {
  private static final class Factory implements org.unbiquitous.uImpala.engine.io.Screen.Factory {
    public Screen create() {
      return new Screen();
    }
  }
  
  public static synchronized void initImpl() {
    if (factory == null)
      factory = new Factory();
  }
}
