package org.unbiquitous.ubiengine.engine.system.time;

import org.unbiquitous.ubiengine.util.observer.Observation;
import org.unbiquitous.ubiengine.util.observer.Observations;
import org.unbiquitous.ubiengine.util.observer.Subject;

/**
 * Timer class with event approach.
 * @author Pimenta
 *
 */
public final class Alarm implements Subject {
  /**
   * Call this method every frame to awake sleepers!
   */
  public void update() {
    if (!done && !paused && System.currentTimeMillis() >= done_ticks) {
      done = true;
      observations.broadcast(TRRRIMM, null);
    }
  }
  
  /**
   * Resets and starts counting time.
   * @param ms Time in milliseconds.
   */
  public void start(long ms) {
    if (ms > 0) {
      done_ticks = ms + System.currentTimeMillis();
      done = false;
      paused = false;
    }
  }
  
  /**
   * Pauses the time counting.
   */
  public void pause() {
    if (!paused) {
      pausetime = System.currentTimeMillis();
      paused = true;
    }
  }
  
  /**
   * Resumes the time counting.
   */
  public void resume() {
    if (paused) {
      done_ticks += System.currentTimeMillis() - pausetime;
      paused = false;
    }
  }
  
  /**
   * Aborts the alarm.
   */
  public void abort() {
    done = true;
  }
  
  /**
   * Query the counted time.
   * @return Time in milliseconds.
   */
  public long time() {
    if (done)
      return 0;
    if (paused)
      return done_ticks - pausetime;
    return done_ticks - System.currentTimeMillis();
  }
  
  /**
   * Query if timer is paused.
   * @return Boolean value.
   */
  public boolean isPaused() {
    return paused;
  }
  
  /**
   * There is only one event: TRRRIMM!
   */
  public static final Integer TRRRIMM = 0;
  
  public void connect(Integer eventType, Observation obs) {
    observations.connect(eventType, obs);
  }
  
  public void disconnect(Integer eventType, Observation obs) {
    observations.disconnect(eventType, obs);
  }
  
  private Observations observations = new Observations(TRRRIMM);
  private boolean done = true;
  private boolean paused = false;
  private long done_ticks;
  private long pausetime;
}
