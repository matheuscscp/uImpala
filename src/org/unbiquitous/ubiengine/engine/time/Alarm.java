package org.unbiquitous.ubiengine.engine.time;

import java.lang.reflect.Method;

import org.unbiquitous.ubiengine.util.observer.Subject;
import org.unbiquitous.ubiengine.util.observer.Observations;

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
      subject.broadcast(TRRRIMM);
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
  
  public void connect(Integer event_type, Method handler) {
    subject.connect(event_type, handler);
  }
  
  public void connect(Integer event_type, Object observer, Method handler) {
    subject.connect(event_type, observer, handler);
  }
  
  public void disconnect(Method handler) {
    subject.disconnect(handler);
  }
  
  public void disconnect(Integer event_type, Method handler) {
    subject.disconnect(event_type, handler);
  }
  
  public void disconnect(Object observer) {
    subject.disconnect(observer);
  }
  
  public void disconnect(Integer event_type, Object observer) {
    subject.disconnect(event_type, observer);
  }
  
  private Observations subject = new Observations(TRRRIMM);
  private boolean done = true;
  private boolean paused = false;
  private long done_ticks;
  private long pausetime;
}
