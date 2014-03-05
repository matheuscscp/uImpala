package org.unbiquitous.ubiengine.engine.time;

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
    started = true;
    paused = false;
    initialTime = Time.get();
  }
  
  /**
   * Pauses the time counting if the stopwatch is not paused.
   */
  public void pause() {
    if (!paused) {
      paused = true;
      pauseTime = Time.get();
    }
  }
  
  /**
   * Resumes the time counting if the stopwatch is paused.
   */
  public void resume() {
    if (paused) {
      paused = false;
      initialTime += Time.get() - pauseTime;
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
    if (!started)
      return 0;
    if (paused)
      return pauseTime - initialTime;
    return Time.get() - initialTime;
  }
  
  private boolean started = false, paused = false;
  private long initialTime = 0, pauseTime = 0;
}
