package org.unbiquitous.ubiengine.engine.core;

import java.util.HashMap;

import org.unbiquitous.ubiengine.util.ComponentContainer;

/**
 * Class to access a game's components.
 * @author Pimenta
 *
 */
public final class Components {
  /**
   * Use to access a game's components.
   * @param g Game.
   * @return Game's components.
   */
  public static ComponentContainer get(Class<? extends UosGame> g) {
    return (ComponentContainer)((HashMap<Class<?>, Object>)games).get(g);
  }
  
  /**
   * Engine's private use.
   */
  protected static void put(Class<? extends UosGame> g, ComponentContainer c) {
    games.put(g, c);
  }
  
  /**
   * Engine's private use.
   */
  protected static void remove(Class<? extends UosGame> g) {
    games.remove(g);
  }
  
  private static final ComponentContainer games = new ComponentContainer();
}
