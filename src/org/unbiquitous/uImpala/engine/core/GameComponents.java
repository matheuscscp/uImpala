package org.unbiquitous.uImpala.engine.core;

import org.unbiquitous.uImpala.engine.asset.AssetManager;
import org.unbiquitous.uImpala.util.ComponentContainer;

/**
 * Class to access the components of the current game.
 * @author Pimenta
 *
 */
public final class GameComponents {
  private static final InheritableThreadLocal<ComponentContainer> components = 
    new InheritableThreadLocal<ComponentContainer>() {
         protected ComponentContainer initialValue() {
           return new ComponentContainer();
         }
    };
  private static Class<? extends AssetManager> assetManagerClass;
  
  /**
   * Method to access a component of the current game.
   * @param key Component class.
   * @return Component.
   */
  public static <T> T get(Class<T> key) {
    return components.get().get(key);
  }
  
  /**
   * Method to put a component in the current game.
   * @param key Component class.
   * @param value Component.
   * @return The value mapped previously, or null if there was no mapped value.
   */
  public static Object put(Class<?> key, Object value) {
    return components.get().put(key, value);
  }
  
  public static void setAssetManagerClass(Class<? extends AssetManager> clazz){
    GameComponents.assetManagerClass = clazz;
  }
  
  public static AssetManager createAssetManager(){
    try {
      return assetManagerClass.newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
