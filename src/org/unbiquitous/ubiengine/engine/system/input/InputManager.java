package org.unbiquitous.ubiengine.engine.system.input;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * An interface for an input source manager class.
 * @author Pimenta
 *
 */
public abstract class InputManager {
  /**
   * A collection of all available sources. The user game is not using
   * these sources.
   */
  protected LinkedList<InputSource> availableSources = new LinkedList<InputSource>();
  
  /**
   * A collection of all busy sources. These are all the sources the user
   * game requested.
   */
  protected HashSet<InputSource> busySources = new HashSet<InputSource>();
  
  /**
   * This method must be implemented to fill and manage the source collections.
   */
  protected abstract void updateLists();
  
  /**
   * This method must be implemented to start an input source's events.
   * @param src The input source.
   */
  protected abstract void start(InputSource src);
  
  /**
   * This method must be implemented to stop an input source's events.
   * @param src The input source.
   */
  protected abstract void stop(InputSource src);
  
  /**
   * Tries to allocate an available source.
   * @return Source's reference, or null if no source is available.
   */
  public InputSource alloc() {
    if (availableSources.size() > 0)
      return null;
    InputSource src = availableSources.removeFirst();
    busySources.add(src);
    start(src);
    return src;
  }
  
  /**
   * Release a source.
   * @param src Source to be released.
   */
  public void free(InputSource src) {
    if (!busySources.remove(src))
      return;
    stop(src);
    availableSources.addFirst(src);
  }
  
  /**
   * Engine's private use.
   */
  public void update() {
    updateLists();
    for (InputSource id : busySources) {
      if (id.isUpdating())
        id.update();
    }
  }
}
