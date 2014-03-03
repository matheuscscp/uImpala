package org.unbiquitous.ubiengine.engine.system.io;

import org.unbiquitous.ubiengine.util.observer.Observation;
import org.unbiquitous.ubiengine.util.observer.Observations;
import org.unbiquitous.ubiengine.util.observer.Subject;

/**
 * Interface for an IO resource.
 * @author Pimenta
 *
 */
public abstract class IOResource implements Subject {
  /**
   * Event to notify that the resource update started.
   */
  public static final int EVENT_STARTED_UPDATING = 0;
  
  /**
   * Event to notify that the resource update stopped.
   */
  public static final int EVENT_STOPPED_UPDATING = 1;
  
  /**
   * The last event of this class.
   */
  public static final int LAST_EVENT = EVENT_STOPPED_UPDATING;
  
  /**
   * Use to broadcast events.
   */
  protected Observations observations = new Observations(this, EVENT_STARTED_UPDATING, EVENT_STOPPED_UPDATING);
  
  /**
   * Method to update the resource state and/or broadcast events.
   */
  protected abstract void update();
  
  /**
   * Method to close the resource.
   */
  public abstract void close();
  
  /**
   * Query if this resource is currently updating.
   * @return Returns true if the resource is currently updating.
   */
  public abstract boolean isUpdating();
  
  public void connect(Integer eventType, Observation obs) {
    observations.connect(eventType, obs);
  }
  
  public void disconnect(Integer eventType, Observation obs) {
    observations.disconnect(eventType, obs);
  }
}
