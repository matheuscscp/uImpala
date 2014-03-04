package org.unbiquitous.ubiengine.engine.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Use this class to implement game objects of a ContainerScene.
 * @see ContainerScene
 * @author Pimenta
 *
 */
public abstract class GameObject {
  /**
   * Flag to tell the game scene if this object must be destroyed.
   */
  protected boolean destroy = false;
  
  /**
   * Method to implement update.
   */
  protected abstract void update();
  
  /**
   * Method to implement rendering.
   */
  protected abstract void render(RendererContainer renderers);
  
  /**
   * Handle a pop from the stack of game scenes.
   * @param args Arguments passed from the scene popped.
   */
  protected abstract void wakeup(Object... args);
  
  /**
   * Method to close whatever is necessary.
   */
  protected abstract void destroy();
  
  /**
   * Add a child game object.
   * @param o Game object.
   */
  public void add(GameObject o) {
    objects.add(o);
  }
  
  /**
   * Calls update() for this object and its tree.
   */
  protected void updateTree() {
    update();
    for (GameObject o : objects) {
      if (!o.destroy)
        o.updateTree();
    }
  }
  
  /**
   * Calls render() for this object and its tree.
   */
  protected void renderTree(RendererContainer renderers) {
    render(renderers);
    Iterator<GameObject> i = objects.iterator();
    while (i.hasNext()) {
      GameObject o = i.next();
      if (!o.destroy)
        o.renderTree(renderers);
      else {
        o.destroyTree();
        i.remove();
      }
    }
  }
  
  /**
   * Calls wakeup() for this object and its tree.
   */
  protected void wakeupTree(Object... args) {
    wakeup(args);
    for (GameObject o : objects)
      o.wakeupTree(args);
  }
  
  /**
   * Calls destroy() for this object and its tree.
   */
  protected void destroyTree() {
    destroy();
    for (GameObject o : objects)
      o.destroyTree();
  }
  
  private List<GameObject> objects = new LinkedList<GameObject>();
}
