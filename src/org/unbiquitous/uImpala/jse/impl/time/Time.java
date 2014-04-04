package org.unbiquitous.uImpala.jse.impl.time;

import org.lwjgl.Sys;

public final class Time extends org.unbiquitous.uImpala.engine.time.Time {
  public long getTime() {
    return Sys.getTime()*1000/Sys.getTimerResolution();
  }
  
  public static void init() {
    time = new Time();
  }
}
