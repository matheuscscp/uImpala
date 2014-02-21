package org.unbiquitous.ubiengine.util.observer;

/**
 * Event base class, to hold event data and stop propagation flag.
 * @author Pimenta
 *
 */
public class Event {
  /**
   * Assign true to stop propagation.
   */
  public boolean stop = false;
}
