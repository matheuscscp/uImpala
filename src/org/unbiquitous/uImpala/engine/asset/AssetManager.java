package org.unbiquitous.uImpala.engine.asset;

import java.awt.Font;
import java.util.HashMap;

import org.unbiquitous.uImpala.engine.core.GameComponents;
import org.unbiquitous.uImpala.engine.core.GameSettings;

/**
 * Class to manage assets of a game scene.
 * @author Pimenta
 *
 */
public abstract class AssetManager {
  /**
   * Create a sprite.
   * @param path Image path.
   * @return Sprite created.
   */
  public abstract Sprite newSprite(String path);
  
  /**
   * Create an animation.
   * @param path Image path.
   * @param frames Number of frames in the sprite sheet.
   * @param fps Number of frames per second.
   * @return Animation created.
   */
  public abstract Animation newAnimation(String path, int frames, float fps);
  
  /**
   * Create a text.
   * @param fontPath Font path.
   * @param text Text to render.
   * @return Text created.
   */
  public abstract Text newText(String fontPath, String text);
  
  /**
   * Create a text.
   * @param font Java Font.
   * @param text Text to render.
   * @return Text created.
   */
  public abstract Text newText(Font font, String text);
  
  /**
   * Create an audio.
   * @param path Audio path.
   * @return Audio created.
   */
  public abstract Audio newAudio(String path);
  
  /**
   * Create a tile set.
   * @param path Image path.
   * @param rows Number of rows.
   * @param cols Number of columns.
   * @return TileSet created.
   */
  public TileSet newTileSet(String path, int rows, int cols) {
    return new TileSet(newSprite(path), rows, cols);
  }
  
  /**
   * Create a tile map.
   * @param mapPath Map path (text file).
   * @param tileSet A TileSet to render tiles.
   * @return TileMap created.
   */
  public TileMap newTileMap(String mapPath, TileSet tileSet) {
    return new TileMap(getMap(mapPath), tileSet);
  }
  
  /**
   * Engine's private use.
   */
  public abstract void destroy();
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  private Map getMap(String path) {
    Map asset = (Map)assets.get(path);
    if (asset != null)
      return asset;
    
    asset = new Map(
      GameComponents.get(GameSettings.class).get("root_path") + "/" + path
    );
    
    assets.put(path, asset);
    return asset;
  }
  
  protected HashMap<String, Object> assets = new HashMap<String, Object>();
}
