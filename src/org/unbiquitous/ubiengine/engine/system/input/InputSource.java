package org.unbiquitous.ubiengine.engine.system.input;

import java.util.LinkedList;
import java.util.Queue;

import org.unbiquitous.ubiengine.util.observer.Event;
import org.unbiquitous.ubiengine.util.observer.Observation;
import org.unbiquitous.ubiengine.util.observer.Observations;
import org.unbiquitous.ubiengine.util.observer.Subject;

/**
 * An interface for an input source class.
 * @author Pimenta
 *
 */
public abstract class InputSource implements Subject {
  /**
   * Data for UPDATE_STARTED and UPDATE_STOPPED events.
   * @author Pimenta
   *
   */
  public static final class UpdateEvent extends Event {
    /**
     * Assignment constructor.
     * @param src The input source.
     */
    public UpdateEvent(InputSource src) {
      source = src;
    }
    
    /**
     * Access method to the source that triggered the event.
     * @return The input source.
     */
    public InputSource source() {
      return source;
    }
    
    private InputSource source;
  }
  
  /**
   * Event to notify that the source update started.
   */
  public static final int UPDATE_STARTED = 0;
  
  /**
   * Event to notify that the source update stopped.
   */
  public static final int UPDATE_STOPPED = 1;
  
  /**
   * Event queue to be polled in <code>update()</code>.
   */
  protected Queue<InputEvent> events = new LinkedList<InputEvent>();
  
  /**
   * Use to broadcast events.
   */
  protected Observations observations = new Observations(UPDATE_STARTED, UPDATE_STOPPED);
  
  /**
   * Method to update the source state and broadcast events.
   */
  protected abstract void update();
  
  /**
   * Query if this source is currently updating.
   * @return Returns true if the source is currently updating.
   */
  public abstract boolean isUpdating();
  
  public void connect(Integer eventType, Observation obs) {
    observations.connect(eventType, obs);
  }
  
  public void disconnect(Integer eventType, Observation obs) {
    observations.disconnect(eventType, obs);
  }
}
