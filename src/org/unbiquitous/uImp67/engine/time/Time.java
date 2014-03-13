package org.unbiquitous.uImp67.engine.time;

import org.lwjgl.Sys;

/**
 * Class to access the system time in milliseconds.
 * @author Pimenta
 *
 */
public final class Time {
  /**
   * Method to access the system time.
   * @return System time in milliseconds.
   */
  public static long get() {
    return Sys.getTime()*1000/Sys.getTimerResolution();
  }
}
