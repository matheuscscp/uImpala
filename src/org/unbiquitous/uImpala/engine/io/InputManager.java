package org.unbiquitous.uImpala.engine.io;

import org.unbiquitous.uImpala.util.observer.Observation;

/**
 * Interface for input resources management.
 * @author Pimenta
 *
 */
public interface InputManager extends IOManager {
	  /**
	   * Allows to connect to an event in all available sources
	   */
	  public void connect(Integer eventType, Observation obs);
  
}
