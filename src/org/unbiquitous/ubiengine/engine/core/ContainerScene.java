package org.unbiquitous.ubiengine.engine.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to automatically update and render game objects.
 * Extend only to implement a constructor, to add game objects.
 * @see GameObject
 * @see GameScene
 * @author Pimenta
 *
 */
public abstract class ContainerScene extends GameScene {
  /**
   * Add a game object to the game scene.
   * @param o Game object.
   */
  protected void add(GameObject o) {
    newObjects.add(o);
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  /**
   * Engine's private use.
   */
  protected void update() {
    for (GameObject o : objects) {
      if (!o.destroy && !o.frozen)
        o.updateTree();
    }
    while (newObjects.size() > 0)
      objects.add(newObjects.removeFirst());
  }
  
  /**
   * Engine's private use.
   */
  protected void render() {
    GameRenderers renderers = new GameRenderers();
    Iterator<GameObject> i = objects.iterator();
    while (i.hasNext()) {
      GameObject o = i.next();
      if (!o.destroy) {
        if (!o.frozen || (o.frozen && o.visible))
          o.renderTree(renderers);
      }
      else {
        o.destroyTree();
        i.remove();
      }
    }
    renderers.render();
  }
  
  /**
   * Engine's private use.
   */
  protected void wakeup(Object... args) {
    for (GameObject o : objects)
      o.wakeupTree(args);
  }
  
  /**
   * Engine's private use.
   */
  protected void destroy() {
    for (GameObject o : objects)
      o.destroyTree();
  }
  
  private List<GameObject> objects = new LinkedList<GameObject>();
  private LinkedList<GameObject> newObjects = new LinkedList<GameObject>();
}
