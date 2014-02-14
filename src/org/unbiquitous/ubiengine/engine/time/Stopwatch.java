package org.unbiquitous.ubiengine.engine.time;

/**
 * Query approch timer class. Time in milliseconds.
 * @author Pimenta
 *
 */
public class Stopwatch {
  protected long initialtime;
  protected long pausetime;
  protected boolean paused;
  
  public Stopwatch() {
    initialtime = -1;
    paused = false;
  }
  
  public void start() {
    initialtime = System.currentTimeMillis();
    paused = false;
  }
  
  public void pause() {
    if (!paused && initialtime != -1) {
      pausetime = System.currentTimeMillis();
      paused = true;
    }
  }
  
  public void resume() {
    if (paused) {
      initialtime += System.currentTimeMillis() - pausetime;
      paused = false;
    }
  }
  
  /** Time in milliseconds */
  public long time() {
    if (initialtime == -1)
      return -1;
    
    if (paused)
      return pausetime - initialtime;
    
    return System.currentTimeMillis() - initialtime;
  }
  
  public boolean isPaused() {
    return paused;
  }
}
