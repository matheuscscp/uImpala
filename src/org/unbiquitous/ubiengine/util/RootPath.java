package org.unbiquitous.ubiengine.util;

/**
 * Class to hold the root path of application resources.
 * 
 * @author Matheus
 */
public class RootPath {
  private String path;
  
  /** @param path Root path string. */
  public RootPath(String path) {
    this.path = path;
  }
  
  /** @return Root path string. */
  public String get() {
    return path;
  }
  
  /**
   * @param path Local path string.
   * @return Concatenation of root path with the passed argument.
   */
  public String get(String path) {
    return this.path + (path == null ? "" : path);
  }
}
