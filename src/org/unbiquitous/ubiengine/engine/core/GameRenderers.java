package org.unbiquitous.ubiengine.engine.core;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * Container for all rendering operations in a frame.
 * @author Pimenta
 *
 */
public class GameRenderers {
  /**
   * Method to add a render operation to the container.
   * @param z Plane of renderization. The renderization will happen
   * in ascending order.
   * @param renderer Renderer to be called.
   */
  public void put(int z, Runnable renderer) {
    List<Runnable> l = renderers.get(z);
    if (l == null) {
      l = new LinkedList<Runnable>();
      renderers.put(z, l);
    }
    l.add(renderer);
  }
  
  /**
   * Method to render everything. Also clears the container.
   */
  public void render() {
    while (renderers.size() > 0) {
      LinkedList<Runnable> tmp = (LinkedList<Runnable>)renderers.pollFirstEntry().getValue();
      while (tmp.size() > 0)
        tmp.removeFirst().run();
    }
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  private TreeMap<Integer, List<Runnable>> renderers = new TreeMap<Integer, List<Runnable>>();
}
