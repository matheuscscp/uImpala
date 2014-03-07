package org.unbiquitous.ubiengine.engine.io;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

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
  protected TreeSet<IOResource> busyResources = new TreeSet<IOResource>();
  
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
    if (availableResources.size() == 0)
      return null;
    IOResource rsc = availableResources.removeFirst();
    busyResources.add(rsc);
    start(rsc);
    return rsc;
  }
  
  /**
   * Release a resource.
   * @param rsc Resource to be released.
   * @return Returns true if the resource was removed from the container of
   * busy resources.
   */
  public boolean free(IOResource rsc) {
    if (!busyResources.remove(rsc))
      return false;
    rsc.close();
    stop(rsc);
    availableResources.addLast(rsc);
    return true;
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
  
  /**
   * Engine's private use.
   */
  public void close() {
    while (availableResources.size() > 0)
      availableResources.removeFirst().close();
    for (Iterator<IOResource> i = busyResources.iterator(); i.hasNext();) {
      i.next().close();
      i.remove();
    }
  }
}
