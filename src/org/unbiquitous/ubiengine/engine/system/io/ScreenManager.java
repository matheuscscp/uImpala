package org.unbiquitous.ubiengine.engine.system.io;

import org.unbiquitous.ubiengine.engine.core.GameComponents;

/**
 * Class for screens management.
 * @author Pimenta
 *
 */
public final class ScreenManager extends OutputManager {
  /**
   * Constructor to add the default screen in availableResources.
   */
  public ScreenManager() {//FIXME
    availableResources.add(new Screen());
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
  
  public IOResource alloc() {
    Screen scr = (Screen)super.alloc();
    MouseManager mm = GameComponents.get(MouseManager.class);
    if (mm != null)
      mm.screenMouses.add(scr.mouse);
    KeyboardManager km = GameComponents.get(KeyboardManager.class);
    if (km != null)
      km.screenKeyboards.add(scr.keyboard);
    return scr;
  }
  
  public void free(IOResource scr) {
    super.free(scr);
    MouseManager mm = GameComponents.get(MouseManager.class);
    if (mm != null)
      mm.screenMouses.remove(((Screen)scr).mouse);
    KeyboardManager km = GameComponents.get(KeyboardManager.class);
    if (km != null)
      km.screenKeyboards.remove(((Screen)scr).keyboard);
  }
}
