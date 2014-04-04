package org.unbiquitous.uImpala.engine.asset;

import org.unbiquitous.uImpala.engine.io.Screen;

/**
 * Class to load a map from a file and render it using a TileSet.
 * @author Pimenta
 *
 */
public class TileMap {
  /**
   * Constructor.
   * @param map Logical map to render tiles.
   * @param tileSet TileSet to render each tile.
   */
  protected TileMap(Map map, TileSet tileSet) {
    this.map = map;
    this.tileSet = tileSet;
  }
  
  /**
   * Render the whole map.
   * @param screen Screen on which the map will be rendered.
   * @param x Coordinate x of top-left corner of the map.
   * @param y Coordinate y of top-left corner of the map.
   */
  public void render(Screen screen, float x, float y) {
    for (int i = 0; i < map.rows; i++) {
      for (int j = 0; j < map.cols; j++) {
        int tileID = map.map[i][j];
        if (tileID != 0)
          tileSet.render(tileID - 1, screen, x + j*tileSet.tileWidth, y + i*tileSet.tileHeight);
      }
    }
  }
  
  /**
   * Get the logical map.
   * @return The logical map.
   */
  public Map getMap() {
    return map;
  }
  
  /**
   * Get the tile set used to render tiles.
   * @return TileSet used to render tiles.
   */
  public TileSet getTileSet() {
    return tileSet;
  }
  
  private Map map;
  private TileSet tileSet;
}
