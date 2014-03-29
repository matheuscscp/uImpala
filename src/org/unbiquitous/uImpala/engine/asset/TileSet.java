package org.unbiquitous.uImpala.engine.asset;

import org.unbiquitous.uImpala.engine.io.Screen;

/**
 * Class to split an image to a matrix and render each "tile".
 * @author Pimenta
 *
 */
public class TileSet {
  /**
   * Constructor.
   * @param assets Object to load the image.
   * @param path String path.
   * @param rows Number of rows.
   * @param cols Number of columns.
   */
  public TileSet(AssetManager assets, String path, int rows, int cols) {
    int tileAmount = rows*cols;
    sprite = new Sprite(assets, path);
    tileWidth = sprite.getWidth()/cols;
    tileHeight = sprite.getHeight()/rows;
    tiles = new Tile[tileAmount];
    for (int tileID = 0; tileID < tileAmount; tileID++)
      tiles[tileID] = new Tile((tileID%cols)*tileWidth, (tileID/cols)*tileHeight);
  }
  
  /**
   * Render a tile.
   * @param tileID This number must be in the interval [0, rows*cols - 1].
   * The ID grows traversing rows from left to right.
   * @param screen Screen on which the tile will be rendered.
   * @param x Coordinate x of top-left corner of the tile.
   * @param y Coordinate y of top-left corner of the tile.
   */
  public void render(int tileID, Screen screen, float x, float y) {
    Tile tile = tiles[tileID];
    sprite.clip(tile.x, tile.y, tileWidth, tileHeight);
    sprite.render(x, y, screen);
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  /**
   * Engine's private use.
   */
  protected int tileWidth, tileHeight;
  
  private Sprite sprite;
  private Tile[] tiles;
  
  private class Tile {
    public int x, y;
    public Tile(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
}
