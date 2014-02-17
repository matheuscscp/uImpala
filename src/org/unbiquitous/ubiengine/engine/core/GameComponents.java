package org.unbiquitous.ubiengine.engine.core;

import java.util.HashMap;

import org.unbiquitous.ubiengine.util.ComponentContainer;

/**
 * Class to hold all games' components.
 * @author Pimenta
 *
 */
public final class GameComponents {
  /**
   * Method to access current game's components.
   * @return
   */
  public static ComponentContainer get() {
    return games.get(Thread.currentThread().getId());
  }
  
  protected static void create() {
    games.put(Thread.currentThread().getId(), new ComponentContainer());
  }
  
  protected static void remove() {
    games.remove(Thread.currentThread().getId());
  }
  
  private static final HashMap<Long, ComponentContainer> games = new HashMap<Long, ComponentContainer>();
}
