package org.unbiquitous.uImp67.util.observer;

/**
 * Interface to be implemented by a class that triggers events. The class must
 * also declare an object of Observations class, since the implementation of
 * this interface must be calls to the methods of the Observations class.
 * @author Pimenta
 *
 */
public interface Subject {
  /**
   * Add an observation to an event. If eventType is null, the observation
   * will be added to all events.<br />
   * <br />
   * Implementation example:<br />
   * <code>
   * observations.connect(eventType, obs);
   * </code>
   * @param eventType Event type.
   * @param obs Observation to be connected.
   */
  public void connect(Integer eventType, Observation obs);
  
  /**
   * Remove an observation from an event. If eventType is null, the observation
   * will be removed from all events.<br />
   * <br />
   * Implementation example:<br />
   * <code>
   * observations.disconnect(eventType, obs);
   * </code>
   * @param eventType Event type.
   * @param obs Observation to be disconnected.
   */
  public void disconnect(Integer eventType, Observation obs);
}
