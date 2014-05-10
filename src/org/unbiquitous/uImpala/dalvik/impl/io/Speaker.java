package org.unbiquitous.uImpala.dalvik.impl.io;

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
    // TODO
  }
  
  public void start() {
    
  }
  
  public void stop() {
    
  }
  
  protected void update() {
    
  }
  
  public void close() {
    // TODO Auto-generated method stub
    
  }
  
  public boolean isUpdating() {
    return true;
  }
}
