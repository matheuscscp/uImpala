package org.unbiquitous.ubiengine.engine.time;

import java.lang.reflect.Method;

import org.unbiquitous.ubiengine.util.observer.Subject;
import org.unbiquitous.ubiengine.util.observer.SubjectDevice;

/**
 * Event approch timer class. Time in milliseconds.
 * @author Pimenta
 *
 */
public class Timer implements Subject {

  protected boolean done;
  protected boolean paused;
  protected long done_ticks;
  protected long pausetime;
  
  public Timer() {
    done = true;
    paused = false;
    subject = new SubjectDevice(DONE);
  }

  public void update() throws Exception {
    if (!done && !paused && System.currentTimeMillis() >= done_ticks) {
      done = true;
      subject.broadcast(DONE);
    }
  }

  /** @param ms Time in milliseconds. */
  public void start(long ms) {
    if (ms > 0) {
      done_ticks = ms + System.currentTimeMillis();
      done = false;
      paused = false;
    }
  }
  
  public void pause() {
    if (!paused) {
      pausetime = System.currentTimeMillis();
      paused = true;
    }
  }
  
  public void resume() {
    if (paused) {
      done_ticks += System.currentTimeMillis() - pausetime;
      paused = false;
    }
  }
  
  public void cancel() {
    done = true;
  }

  /** Time in milliseconds */
  public long time() {
    if (done)
      return 0;
    
    if (paused)
      return done_ticks - pausetime;
    
    return done_ticks - System.currentTimeMillis();
  }
  
  public boolean isPaused() {
    return paused;
  }
  
  protected SubjectDevice subject;
  
  public static final String DONE = "DONE";
  
  public void connect(String event_type, Method handler) {
    subject.connect(event_type, handler);
  }

  public void connect(String event_type, Object observer, Method handler) {
    subject.connect(event_type, observer, handler);
  }

  public void disconnect(Method handler) {
    subject.disconnect(handler);
  }

  public void disconnect(String event_type, Method handler) {
    subject.disconnect(event_type, handler);
  }

  public void disconnect(Object observer) {
    subject.disconnect(observer);
  }

  public void disconnect(String event_type, Object observer) {
    subject.disconnect(event_type, observer);
  }
}
