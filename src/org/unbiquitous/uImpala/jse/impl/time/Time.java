package org.unbiquitous.uImpala.jse.impl.time;

import org.lwjgl.Sys;

public final class Time extends org.unbiquitous.uImpala.engine.time.Time {
  public synchronized long getTime() {
    return Sys.getTime()*1000/Sys.getTimerResolution();
  }
  
  public static synchronized void initImpl() {
    if (time == null)
      time = new Time();
  }
}
