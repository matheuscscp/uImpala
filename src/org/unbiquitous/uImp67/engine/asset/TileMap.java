package org.unbiquitous.uImp67.engine.asset;

import org.unbiquitous.uImp67.engine.io.Screen;

/**
 * Class to load a map from a file and render it using a TileSet.
 * @author Pimenta
 *
 */
public class TileMap {
  /**
   * Constructor.
   * @param assets Object to load the map.
   * @param mapPath String path.
   * @param tileSet TileSet to render each tile.
   */
  public TileMap(AssetManager assets, String mapPath, TileSet tileSet) {
    map = assets.getMap(mapPath);
    this.tileSet = tileSet;
  }
  
  /**
   * Render the whole map.
   * @param screen Screen on which the map will be rendered.
   * @param x Coordinate x of top-left corner of the map.
   * @param y Coordinate y of top-left corner of the map.
   */
  public void render(Screen screen, float x, float y) {
    for (int i = 0; i < map.getRows(); i++) {
      for (int j = 0; j < map.getCols(); j++) {
        int tileID = map.get(i, j);
        if (tileID != 0)
          tileSet.render(tileID - 1, screen, x + j*tileSet.tileWidth, y + i*tileSet.tileHeight);
      }
    }
  }
  
  private TileSet tileSet;
  private Map map;
}
