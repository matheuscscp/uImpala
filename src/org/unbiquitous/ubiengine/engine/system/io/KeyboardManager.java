package org.unbiquitous.ubiengine.engine.system.io;

import java.util.HashSet;
import java.util.Iterator;

import org.unbiquitous.ubiengine.engine.core.GameComponents;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;

/**
 * Class for keyboards management.
 * @author Pimenta
 *
 */
public final class KeyboardManager extends InputManager {
  /**
   * Engine's private use.
   */
  protected HashSet<KeyboardSource> screenKeyboards = new HashSet<KeyboardSource>();
  
  public KeyboardManager() {
    gateway = GameComponents.get(Gateway.class);
  }
  
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
    for (KeyboardSource ks : screenKeyboards)
      ks.update();
  }
  
  public void destroy() {
    super.destroy();
    for (Iterator<KeyboardSource> i = screenKeyboards.iterator(); i.hasNext();) {
      i.next().close();
      i.remove();
    }
  }
  
  private Gateway gateway;
}
