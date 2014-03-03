package org.unbiquitous.ubiengine.engine.system.io;

import java.util.HashSet;

/**
 * Class for keyboards management.
 * @author Pimenta
 *
 */
public final class KeyboardManager extends InputManager {
  /**
   * Engine's private use.
   */
  protected HashSet<KeyboardSourceOld> screenKeyboards = new HashSet<KeyboardSourceOld>();
  
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
    for (KeyboardSourceOld ks : screenKeyboards)
      ks.update();
  }
}
