package org.unbiquitous.ubiengine.engine.system.io;

import java.util.HashSet;

/**
 * Class for mouses management.
 * @author Pimenta
 *
 */
public final class MouseManager extends InputManager {
  /**
   * Engine's private use.
   */
  protected HashSet<MouseSource> screenMouses = new HashSet<MouseSource>();
  
  protected void updateLists() {
    // TODO Auto-generated method stub
  }
  
  protected void start(IOResource rsc) {
    // TODO Auto-generated method stub
  }
  
  protected void stop(IOResource rsc) {
    // TODO Auto-generated method stub
  }
  
  public void update() {
    super.update();
    for (MouseSource ms : screenMouses)
      ms.update();
  }
}
