package org.unbiquitous.uImpala.engine.asset;

import java.awt.Font;
import java.util.HashMap;

import org.unbiquitous.uImpala.engine.core.GameComponents;
import org.unbiquitous.uImpala.engine.core.GameSettings;
import org.unbiquitous.uImpala.util.Color;
import org.unbiquitous.uImpala.util.math.Point;

/**
 * Class to manage assets of a game scene.
 * @author Pimenta
 *
 */
public abstract class AssetManager {
  protected static interface Factory {
    public AssetManager create();
  }
  
  protected static Factory factory = null;
  
  /**
   * Constructor.
   * @return AssetManager created.
   */
  public static AssetManager create() {
    return factory.create();
  }
  
  /**
   * Create a sprite.
   * @param path Image path.
   * @return Sprite created.
   */
  public abstract Sprite newSprite(String path);
  
  /**
   * Create an animation.
   * @param sprite Sprite to render.
   * @param frames Number of frames in the sprite sheet.
   * @param fps Number of frames per second.
   * @return Animation created.
   */
  public Animation newAnimation(Sprite sprite, int frames, float fps) {
    return new Animation(sprite, frames, fps);
  }
  
  /**
   * Create an animation.
   * @param path Image path.
   * @param frames Number of frames in the sprite sheet.
   * @param fps Number of frames per second.
   * @return Animation created.
   */
  public Animation newAnimation(String path, int frames, float fps) {
    return new Animation(newSprite(path), frames, fps);
  }
  
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
   * @param spr Sprite to render.
   * @param rows Number of rows.
   * @param cols Number of columns.
   * @return TileSet created.
   */
  public TileSet newTileSet(Sprite spr, int rows, int cols) {
    return new TileSet(spr, rows, cols);
  }
  
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
   * Get a map from text file.
   * @param path Text file path.
   * @return Map from text file.
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
  
  /**
   * Create a tile map.
   * @param map Logical map.
   * @param tileSet A TileSet to render tiles.
   * @return TileMap created.
   */
  public TileMap newTileMap(Map map, TileSet tileSet) {
    return new TileMap(map, tileSet);
  }
  
  /**
   * Create a tile map.
   * @param mapPath Logical map path (text file).
   * @param tileSet TileSet to render tiles.
   * @return TileMap created.
   */
  public TileMap newTileMap(String mapPath, TileSet tileSet) {
    return new TileMap(getMap(mapPath), tileSet);
  }
  
  /**
   * Create a tile map.
   * @param map Logical map.
   * @param tsSprite Sprite for the TileSet.
   * @param tsRows Number of rows for the TileSet.
   * @param tsCols Number of columns for the TileSet.
   * @return TileMap created.
   */
  public TileMap newTileMap(Map map, Sprite tsSprite, int tsRows, int tsCols) {
    return new TileMap(map, new TileSet(tsSprite, tsRows, tsCols));
  }
  
  /**
   * Create a tile map.
   * @param map Logical map.
   * @param tsPath Image path for the Sprite of the TileSet.
   * @param tsRows Number of rows for the TileSet.
   * @param tsCols Number of columns for the TileSet.
   * @return TileMap created.
   */
  public TileMap newTileMap(Map map, String tsPath, int tsRows, int tsCols) {
    return new TileMap(map, new TileSet(newSprite(tsPath), tsRows, tsCols));
  }
  
  /**
   * Create a tile map.
   * @param mapPath Logical map path (text file).
   * @param tsSprite Sprite for the TileSet.
   * @param tsRows Number of rows for the TileSet.
   * @param tsCols Number of columns for the TileSet.
   * @return TileMap created.
   */
  public TileMap newTileMap(String mapPath, Sprite tsSprite, int tsRows, int tsCols) {
    return new TileMap(getMap(mapPath), new TileSet(tsSprite, tsRows, tsCols));
  }
  
  /**
   * Create a tile map.
   * @param mapPath Logical map path (text file).
   * @param tsPath Image path for the Sprite of the TileSet.
   * @param tsRows Number of rows for the TileSet.
   * @param tsCols Number of columns for the TileSet.
   * @return TileMap created.
   */
  public TileMap newTileMap(String mapPath, String tsPath, int tsRows, int tsCols) {
    return new TileMap(getMap(mapPath), new TileSet(newSprite(tsPath), tsRows, tsCols));
  }
  
  public abstract SimetricShape newSimetricShape(Point center, Color paint, float radius, int numberOfSides);
  public abstract SimetricShape newCircle(Point center, Color paint, float radius);
  public abstract Rectangle newRectangle(Point center, Color paint, float width, float height);
  
  
  /**
   * Engine's private use.
   */
  public abstract void destroy();
  
  protected HashMap<String, Object> assets = new HashMap<String, Object>();
}
