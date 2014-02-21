package org.unbiquitous.ubiengine.engine.system.input;

import java.util.LinkedList;
import java.util.Queue;

import org.unbiquitous.ubiengine.util.observer.Event;
import org.unbiquitous.ubiengine.util.observer.Observation;
import org.unbiquitous.ubiengine.util.observer.Observations;
import org.unbiquitous.ubiengine.util.observer.Subject;

/**
 * An interface for an input device class.
 * @author Pimenta
 *
 */
public abstract class InputDevice implements Subject {
  /**
   * Data for UPDATE_STARTED and UPDATE_STOPPED events.
   * @author Pimenta
   *
   */
  public static final class UpdateEvent extends Event {
    /**
     * Assignment constructor.
     * @param dev The input device.
     */
    public UpdateEvent(InputDevice dev) {
      device = dev;
    }
    
    /**
     * Access method to the device that triggered the event.
     * @return The input device.
     */
    public InputDevice device() {
      return device;
    }
    
    private InputDevice device;
  }
  
  /**
   * Event to notify that the device update started.
   */
  public static final int UPDATE_STARTED = 0;
  
  /**
   * Event to notify that the device update stopped.
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
   * Method to update the device state and broadcast events.
   */
  protected abstract void update();
  
  /**
   * Query if this device is currently updating.
   * @return Returns true if the device is currently updating.
   */
  public abstract boolean isUpdating();
  
  public void connect(Integer eventType, Observation obs) {
    observations.connect(eventType, obs);
  }
  
  public void disconnect(Integer eventType, Observation obs) {
    observations.disconnect(eventType, obs);
  }
}
