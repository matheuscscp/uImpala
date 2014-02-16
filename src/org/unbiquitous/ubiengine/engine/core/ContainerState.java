package org.unbiquitous.ubiengine.engine.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to automatically update and render game objects.
 * Extend only to implement a constructor, to add game objects.
 * @see GameObject
 * @see GameState
 * @author Pimenta
 *
 */
public abstract class ContainerState extends GameState {
  /**
   * Add a game object to the game state.
   * @param o Game object.
   */
  protected void add(GameObject o) {
    objects.add(o);
  }
  
  /**
   * Use this class to implement game objects of a ContainerState.
   * @see ContainerState
   * @author Pimenta
   *
   */
  public static abstract class GameObject {
    /**
     * Flag to tell the game state if this object must be destroyed.
     */
    protected boolean destroy = false;
    
    /**
     * Method to implement update.
     */
    protected abstract void update();
    
    /**
     * Method to implement rendering.
     */
    protected abstract void render();
    
    /**
     * Handle a pop from the stack of game states.
     * @param args Arguments passed from the state popped.
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
    protected void addChild(GameObject o) {
      if (o != this)
        objects.add(o);
    }
    
    /**
     * Update all children game objects.
     */
    protected void updateChildren() {
      for (GameObject o : objects) {
        if (!o.destroy)
          o.update();
      }
    }
    
    /**
     * Render all children game objects.
     */
    protected void renderChildren() {
      Iterator<GameObject> i = objects.iterator();
      while (i.hasNext()) {
        GameObject o = i.next();
        if (!o.destroy)
          o.render();
        else {
          o.destroy();
          i.remove();
        }
      }
    }
    
    /**
     * Handle a pop from the stack of game states in all children game objects.
     * @param args Arguments passed from the state popped.
     */
    protected void wakeupChildren(Object... args) {
      for (GameObject o : objects)
        o.wakeup(args);
    }
    
    /**
     * Destroy all children game objects.
     */
    protected void destroyChildren() {
      for (GameObject o : objects)
        o.destroy();
    }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
    private List<GameObject> objects = new LinkedList<GameObject>();
  }
  
  /**
   * Engine's private use.
   */
  protected void update() {
    for (GameObject o : objects) {
      if (!o.destroy)
        o.update();
    }
  }
  
  /**
   * Engine's private use.
   */
  protected void render() {
    Iterator<GameObject> i = objects.iterator();
    while (i.hasNext()) {
      GameObject o = i.next();
      if (!o.destroy)
        o.render();
      else {
        o.destroy();
        i.remove();
      }
    }
  }
  
  /**
   * Engine's private use.
   */
  protected void wakeup(Object... args) {
    for (GameObject o : objects)
      o.wakeup(args);
  }
  
  /**
   * Engine's private use.
   */
  protected void close() {
    for (GameObject o : objects)
      o.destroy();
  }
  
  private List<GameObject> objects = new LinkedList<GameObject>();
}
