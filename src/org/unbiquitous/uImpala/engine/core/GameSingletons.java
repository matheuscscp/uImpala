package org.unbiquitous.uImpala.engine.core;

import org.unbiquitous.uImpala.util.SingletonContainer;

/**
 * Class to access the singletons of the current game.
 * @author Pimenta
 *
 */
public final class GameSingletons {
  /**
   * Method to access a singleton of the current game.
   * @param key Singleton class.
   * @return Singleton instance.
   */
  public static <T> T get(Class<T> key) {
    return singletons.get().get(key);
  }
  
  /**
   * Method to put a singleton in the current game.
   * @param key Singleton class.
   * @param value Singleton instance.
   * @return The value mapped previously, or null if there was no mapped value.
   */
  public static Object put(Class<?> key, Object value) {
    return singletons.get().put(key, value);
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  private static final InheritableThreadLocal<SingletonContainer> singletons =
  new InheritableThreadLocal<SingletonContainer>() {
    protected SingletonContainer initialValue() {
      return new SingletonContainer();
    }
  };
}
