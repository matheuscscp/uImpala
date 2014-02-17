package org.unbiquitous.ubiengine.engine.core;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * Container for all rendering operations in a frame.
 * @author Pimenta
 *
 */
public class RendererContainer {
  /**
   * Method to add a render operation to the container.
   * @param z Plane of renderization. The renderization will happen
   * in ascending order.
   * @param renderer Renderer to be called.
   */
  public void put(int z, Renderer renderer) {
    List<Renderer> l = renderers.get(z);
    if (l == null) {
      l = new LinkedList<Renderer>();
      renderers.put(z, l);
    }
    l.add(renderer);
  }
  
  /**
   * Method to render everything.
   */
  public void render() {
    for (Entry<?, List<Renderer>> e : renderers.entrySet()) {
      for (Renderer r : e.getValue())
        r.render();
    }
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  private TreeMap<Integer, List<Renderer>> renderers = new TreeMap<Integer, List<Renderer>>();
}
