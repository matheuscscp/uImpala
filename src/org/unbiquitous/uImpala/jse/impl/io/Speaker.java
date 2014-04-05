package org.unbiquitous.uImpala.jse.impl.io;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;

public class Speaker extends org.unbiquitous.uImpala.engine.io.Speaker {
  private static class Factory implements org.unbiquitous.uImpala.engine.io.Speaker.Factory {
    public Speaker create() {
      return new Speaker();
    }
  }
  
  public static synchronized void initImpl() {
    if (factory == null)
      factory = new Factory();
  }
  
  private Speaker() {
    try {
      AL.create();
    } catch (LWJGLException e) {
      throw new Error(e);
    }
  }
  
  protected void update() {
    
  }
  
  public void close() {
    AL.destroy();
  }
  
  public boolean isUpdating() {
    return true;
  }
}
