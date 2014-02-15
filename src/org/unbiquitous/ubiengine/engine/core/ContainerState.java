package org.unbiquitous.ubiengine.engine.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.unbiquitous.ubiengine.util.ComponentContainer;

/**
 * Class to automatically update and render game objects.
 * Extend only to implement a constructor, to add game objects.
 * @see GameObject
 * @see AbstractState
 * @author Pimenta
 *
 */
public abstract class ContainerState extends GameState {
  /**
   * Use to add game objects to ContainerState.
   * @param o GameObject reference.
   */
  protected void add(GameObject o) {
    objects.add(o.setComponents(components, this));
  }
  
  /**
   * After creating a <code>GameObject</code> inside a
   * <code>ContainerState</code>, call this method if you don't want to
   * call <code>ContainerState.add</code> for the new object. Example:<br />
   * <br />
   * Consider that <code>Player</code> extends <code>GameObject</code>.<br />
   * <br />
   * Inside a class that extends <code>ContainerState</code>:<br />
   * <code>
   * add(new Player());<br />
   * // or<br />
   * Player player = new Player();<br />
   * setComponents(player);
   * </code>
   * @param o Game object to be settled.
   * @return This.
   * @see ContainerState#add
   */
  protected void setComponents(GameObject o) {
    o.setComponents(components, this);
  }
  
  /**
   * Use this class to implement game objects of a ContainerState.
   * @see ContainerState
   * @author Pimenta
   *
   */
  public static abstract class GameObject {
    /**
     * Use to manage singleton instances.
     */
    protected ComponentContainer components;
    
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
     * Use this method to build assets.
     * @param key Class of the asset to be created.
     * @param args Arguments to be passed to the constructor.
     * @return Asset reference.
     */
    protected <T> T build(Class<T> key, Object... args) {
      return components.get(UosGame.class).build(key, args);
    }
    
    /**
     * Create a new game state and pass to this method to change the current
     * game state.
     * @param state The new game state.
     */
    protected void change(GameState state) {
      components.get(UosGame.class).change(state);
    }
    
    /**
     * Create a new game state and pass to this method to push it.
     * @param state The new game state.
     */
    protected void push(GameState state) {
      components.get(UosGame.class).push(state);
    }
    
    /**
     * Create arguments (or not) and pass to this method to pop this game state.
     * @param args Args to be passed to the new state in the top of the stack.
     */
    protected void pop(Object... args) {
      components.get(UosGame.class).pop(args);
    }
    
    /**
     * Use to add game objects to the parent game state.
     * @param o GameObject reference.
     */
    protected void add(GameObject o) {
      state.add(o);
    }
    
    /**
     * After creating a <code>GameObject</code> inside another, call
     * this method if you don't want to call <code>GameObject.add</code>
     * for the new object. Example:<br />
     * <br />
     * Consider that <code>Player</code> extends <code>GameObject</code>.<br />
     * <br />
     * Inside another class that extends <code>GameObject</code>:<br />
     * <code>
     * add(new Player());<br />
     * // or<br />
     * this.player = new Player().setComponents(this);
     * </code>
     * @param other Components' source.
     * @return This.
     * @see GameObject#add
     */
    protected GameObject setComponents(GameObject other) {
      components = other.components;
      state = other.state;
      return this;
    }
    
    /**
     * Flag to tell the game state if this object must be destroyed.
     */
    protected boolean destroy = false;
//==============================================================================
//nothings else matters from here to below
//==============================================================================
    /**
     * Engine's private use.
     */
    private GameObject setComponents(ComponentContainer coms, ContainerState s) {
      components = coms;
      state = s;
      return this;
    }
    
    /**
     * Engine's private use.
     */
    private ContainerState state;
  }
  
  /**
   * Engine's private use.
   */
  private List<GameObject> objects = new LinkedList<GameObject>();
  
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
}
