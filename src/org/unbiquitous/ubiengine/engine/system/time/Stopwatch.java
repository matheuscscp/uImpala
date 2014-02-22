package org.unbiquitous.ubiengine.engine.system.time;

/**
 * Timer class with query approach.
 * @author Pimenta
 *
 */
public final class Stopwatch {
  /**
   * Resets and starts counting time.
   */
  public void start() {
    initialtime = Time.get();
    paused = false;
  }
  
  /**
   * Pauses the time counting.
   */
  public void pause() {
    if (!paused && initialtime != -1) {
      pausetime = Time.get();
      paused = true;
    }
  }
  
  /**
   * Resumes the time counting.
   */
  public void resume() {
    if (paused) {
      initialtime += Time.get() - pausetime;
      paused = false;
    }
  }
  
  /**
   * Query the counted time.
   * @return Time in milliseconds.
   */
  public long time() {
    if (initialtime == -1)
      return -1;
    if (paused)
      return pausetime - initialtime;
    return Time.get() - initialtime;
  }
  
  /**
   * Query if timer is paused.
   * @return Boolean value.
   */
  public boolean isPaused() {
    return paused;
  }

  private boolean paused = false;
  private long initialtime = -1;
  private long pausetime;
}
