package org.unbiquitous.ubiengine.engine.system.io;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Class for IO resources management.
 * @author Pimenta
 *
 */
public abstract class IOManager {
  /**
   * A collection of all available resources. The user game is not using
   * these resources.
   */
  protected LinkedList<IOResource> availableResources = new LinkedList<IOResource>();
  
  /**
   * A collection of all busy resources. These are all the resources the user
   * game requested.
   */
  protected HashSet<IOResource> busyResources = new HashSet<IOResource>();
  
  /**
   * This method must be implemented to fill and manage the resource
   * collections.
   */
  protected abstract void updateLists();
  
  /**
   * This method must be implemented to start resource activity.
   * @param rsc The resource.
   */
  protected abstract void start(IOResource rsc);
  
  /**
   * This method must be implemented to stop resource activity.
   * @param rsc The resource.
   */
  protected abstract void stop(IOResource rsc);
  
  /**
   * Tries to allocate an available resource.
   * @return Resource's reference, or null if no resource is available.
   */
  public IOResource alloc() {
    if (availableResources.size() > 0)
      return null;
    IOResource rsc = availableResources.removeFirst();
    busyResources.add(rsc);
    start(rsc);
    return rsc;
  }
  
  /**
   * Release a resource.
   * @param rsc Resource to be released.
   */
  public void free(IOResource rsc) {
    if (!busyResources.remove(rsc))
      return;
    stop(rsc);
    availableResources.addFirst(rsc);
  }
  
  /**
   * Engine's private use.
   */
  public void update() {
    updateLists();
    for (IOResource rsc : busyResources) {
      if (rsc.isUpdating())
        rsc.update();
    }
  }
}
