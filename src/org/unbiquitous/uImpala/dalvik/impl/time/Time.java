package org.unbiquitous.uImpala.dalvik.impl.time;

public class Time extends org.unbiquitous.uImpala.engine.time.Time {
  protected long getTime() {
    return System.currentTimeMillis();
  }
  
  public static synchronized void initImpl() {
    if (time == null)
      time = new Time();
  }
}
