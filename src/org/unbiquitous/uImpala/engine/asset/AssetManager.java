package org.unbiquitous.uImpala.engine.asset;

import java.awt.Font;
import java.util.HashMap;
import java.util.HashSet;

import org.unbiquitous.uImpala.engine.core.GameComponents;
import org.unbiquitous.uImpala.engine.core.GameSettings;

/**
 * Class to manage assets of a game scene.
 * @author Pimenta
 *
 */
public abstract class AssetManager {
  protected HashMap<String, Object> assets = new HashMap<String, Object>();
  protected HashSet<Texture> textures = new HashSet<Texture>();
  
  /**
   * Load a texture.
   * @param path Texture path.
   * @return Texture loaded.
   */
  public abstract Texture getTexture(String path);
  
  /**
   * Load font.
   * @param path Font path.
   * @return Font loaded.
   */
  public abstract Font getFont(String path);
  
  /**
   * Load audio from OGG file.
   * @param path OGG path.
   * @return OggInputStream loaded from OGG file.
   */
  public abstract OggInputStream getOggInputStream(String path);
  
  /**
   * Load map from a text file.
   * @param path Text file path.
   * @return Map loaded.
   */
  public Map getMap(String path) {
    Map asset = (Map)assets.get(path);
    if (asset != null)
      return asset;
    
    asset = new Map(
      GameComponents.get(GameSettings.class).get("root_path") + "/" + path
    );
    
    assets.put(path, asset);
    return asset;
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  /**
   * Engine's private use.
   */
  public void destroy() {
    for (Texture t : textures)
      t.release();
  }
}
