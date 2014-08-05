package org.unbiquitous.uImpala.engine.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Container for all rendering operations in a frame.
 * @author Pimenta
 *
 */
public class GameRenderers {
  /**
   * Method to add a rendering operation to the container.
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
  
  /**
   * Method to move the rendering operations from another GameRenderers object
   * to this one.
   * @param otherRenderers Other GameRenderers object.
   */
  public void absorb(GameRenderers otherRenderers) {
    while (otherRenderers.renderers.size() > 0) {
      Entry<Integer, List<Runnable>> entry = otherRenderers.renderers.pollFirstEntry();
      List<Runnable> l = renderers.get(entry.getKey());
      if (l == null) {
        l = new LinkedList<Runnable>();
        renderers.put(entry.getKey(), l);
      }
      l.addAll(entry.getValue());
    }
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  private TreeMap<Integer, List<Runnable>> renderers = new TreeMap<Integer, List<Runnable>>();
}
