package org.unbiquitous.uImpala.engine.io;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Class for mouses management.
 * @author Pimenta
 *
 */
public class MouseManager implements InputManager {
  public IOResource alloc() {
    return null;
  }
  
  public boolean free(IOResource rsc) {
    return false;
  }
  
  public void update() {
    for (MouseSource ms : screenMouses)
      ms.update();
  }
  
  public void close() {
    for (Iterator<MouseSource> i = screenMouses.iterator(); i.hasNext();) {
      i.next().close();
      i.remove();
    }
  }
  
  /**
   * Engine's private use.
   */
  public void add(MouseSource ms) {
    screenMouses.add(ms);
  }
  
  /**
   * Engine's private use.
   */
  public void remove(MouseSource ms) {
    screenMouses.remove(ms);
  }
  
  /**
   * Engine's private use.
   */
  protected HashSet<MouseSource> screenMouses = new HashSet<MouseSource>();
}
