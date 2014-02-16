package org.unbiquitous.ubiengine.engine.core;

import java.util.Map.Entry;

import org.unbiquitous.ubiengine.util.ComponentContainer;

/**
 * Class to access a game's components.
 * @author Pimenta
 *
 */
public final class Components {
  /**
   * Access to singleton ComponentContainer instance.
   * @return Game's components.
   */
  public static ComponentContainer get() {
    return components;
  }
  
  /**
   * Access to the game instance.
   * @return Game instance.
   */
  public static UbiGame getGame() {
    if (game != null)
      return game;
    for (Entry<?, ?> entry : components.entrySet()) {
      if (entry.getValue() instanceof UbiGame) {
        game = (UbiGame)entry.getValue();
        break;
      }
    }
    return game;
  }
  
  private static final ComponentContainer components = new ComponentContainer();
  private static UbiGame game = null;
}
