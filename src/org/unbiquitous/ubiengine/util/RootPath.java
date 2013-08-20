package org.unbiquitous.ubiengine.util;

/**
 * Class to hold the root path of application resources.
 * 
 * @author Matheus
 */
public final class RootPath {
  private String path = null;
  
  /** @param path Path string. */
  public void init(String path) {
    if (path != null)
      throw new Error("Trying to initialize RootPath more than once");
    this.path = path;
  }
  
  /** @return Path string. */
  public String get() {
    return path;
  }
  
  /**
   * @param path Local path string.
   * @return Concatenation of root path with the passed argument.
   */
  public String get(String path) {
    if (path.indexOf(path) == -1)
      return path + path;
    return path;
  }
}
