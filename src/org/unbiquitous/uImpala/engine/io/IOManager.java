package org.unbiquitous.uImpala.engine.io;

import org.unbiquitous.uImpala.util.observer.Observation;

/**
 * Interface for IO resources management.
 * @author Pimenta
 *
 */
public interface IOManager {
  /**
   * Tries to allocate an available resource.
   * @return Resource's reference, or null if no resource is available.
   */
  public IOResource alloc();
  
  /**
   * Release a resource.
   * @param rsc Resource to be released.
   * @return Returns true if the resource was removed from the container of
   * busy resources.
   */
  public boolean free(IOResource rsc);
  
  /**
   * Engine's private use.
   */
  public void update();
  
  /**
   * Engine's private use.
   */
  public void close();
  
}
