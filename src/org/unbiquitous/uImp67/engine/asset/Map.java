package org.unbiquitous.uImp67.engine.asset;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Class to load map from text file.
 * @author Pimenta
 *
 */
public class Map {
  /**
   * Loads map from text file.
   * @param path File path.
   */
  public Map(String path) {
    Scanner sc;
    try {
      sc = new Scanner(new FileReader(path));
    } catch (FileNotFoundException e) {
      throw new Error(e);
    }
    sc.useDelimiter(",");
    rows = sc.nextInt();
    cols = sc.nextInt();
    sc.nextLine();
    map = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++)
        map[i][j] = sc.nextInt();
      sc.nextLine();
    }
    sc.close();
  }
  
  /**
   * Get value in map position.
   * @param i Row.
   * @param j Column.
   * @return Value in map.
   */
  public int get(int i, int j) {
    return map[i][j];
  }
  
  /**
   * Gets row amount.
   * @return Number of rows.
   */
  public int getRows() {
    return rows;
  }
  
  /**
   * Gets column amount.
   * @return Number of columns.
   */
  public int getCols() {
    return cols;
  }
  
  private int[][] map;
  private int rows, cols;
}
