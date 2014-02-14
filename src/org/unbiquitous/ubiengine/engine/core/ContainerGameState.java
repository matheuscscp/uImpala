package org.unbiquitous.ubiengine.engine.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to automatically update and render game objects.
 * Extend only to implement a constructor, to add game objects.
 * @author Pimenta
 *s
 */
public abstract class ContainerGameState extends GameState {
  /**
   * Use to add game objects to this game state.
   * @param o GameObject reference.
   */
  public void add(GameObject o) {
    objects.add(o.setComponents(components, this));
  }
  // ===========================================================================
  // nothings else matters from here to below
  // ===========================================================================
  private List<GameObject> objects = new LinkedList<GameObject>();
  
  public void update() {
    for (GameObject o : objects) {
      if (!o.destroy)
        o.update();
    }
  }
  
  public void render() {
    Iterator<GameObject> i = objects.iterator();
    while (i.hasNext()) {
      GameObject o = i.next();
      if (!o.destroy)
        o.render();
      else {
        o.close();
        i.remove();
      }
    }
  }
  
  public void wakeup(Object... args) {
    for (GameObject o : objects)
      o.wakeup(args);
  }
  
  public void close() {
    for (GameObject o : objects)
      o.close();
  }
}
