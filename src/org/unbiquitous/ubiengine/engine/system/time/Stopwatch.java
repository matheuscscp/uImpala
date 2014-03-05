package org.unbiquitous.ubiengine.engine.system.time;

/**
 * Timer class with query approach.
 * @author Pimenta
 *
 */
public final class Stopwatch {
  /**
   * Starts the time counting. If already counting, starts over.
   */
  public void start() {
    paused = false;
    started = true;
    initialTime = Time.get();
  }
  
  /**
   * Pauses the time counting if the stopwatch is not paused.
   */
  public void pause() {
    if (!paused && started) {
      pauseTime = Time.get();
      paused = true;
    }
  }
  
  /**
   * Resumes the time counting if the stopwatch is paused.
   */
  public void resume() {
    if (paused && started) {
      initialTime += Time.get() - pauseTime;
      paused = false;
    }
  }
  
  /**
   * Stops and sets the counting to zero.
   */
  public void reset() {
    started = false;
  }
  
  /**
   * Query the time counted.
   * @return Time in milliseconds.
   */
  public long time() {
    if (paused)
      return pauseTime - initialTime;
    if (!started)
      return 0;
    return Time.get() - initialTime;
  }
  
  private boolean paused = false, started = false;
  private long initialTime = 0, pauseTime = 0;
}
