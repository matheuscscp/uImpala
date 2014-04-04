package org.unbiquitous.uImpala.engine.time;

/**
 * Class to access the system time in milliseconds.
 * @author Pimenta
 *
 */
public abstract class Time {
  /**
   * Method to access the system time.
   * @return System time in milliseconds.
   */
  public static long get() {
    return time.getTime();
  }
  
  /**
   * Method to access the system time.
   * @return System time in milliseconds.
   */
  protected abstract long getTime();
  
  protected static Time time = null;
}
