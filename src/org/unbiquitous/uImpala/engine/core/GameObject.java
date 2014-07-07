package org.unbiquitous.uImpala.engine.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Use this class to implement game objects of a GameObjectTreeScene.
 * @see GameObjectTreeScene
 * @author Pimenta
 *
 */
public abstract class GameObject {
  /**
   * Flag to tell the parent if this object must be destroyed.
   */
  protected boolean destroy = false;
  
  /**
   * If true, the parent won't call update() for this object.
   */
  protected boolean frozen = false;
  
  /**
   * If true, the parent will call render() for this object, even if
   * frozen is true.
   */
  protected boolean visible = false;
  
  /**
   * Method to implement update.
   */
  protected abstract void update();
  
  /**
   * Method to implement rendering.
   */
  protected abstract void render(GameRenderers renderers);
  
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
    newObjects.add(o);
  }
  
  /**
   * Calls update() for this object and its tree.
   */
  protected void updateTree() {
    update();
    for (GameObject o : objects) {
      if (!o.destroy && !o.frozen)
        o.updateTree();
    }
    while (newObjects.size() > 0)
      objects.add(newObjects.removeFirst());
  }
  
  /**
   * Calls render() for this object and its tree.
   */
  protected void renderTree(GameRenderers renderers) {
    render(renderers);
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
  
  /**
   * Get children game objects.
   * @return Children game objects.
   */
  public List<GameObject> getChildren() {
    return objects;
  }
  
  private List<GameObject> objects = new LinkedList<GameObject>();
  private LinkedList<GameObject> newObjects = new LinkedList<GameObject>();
}
