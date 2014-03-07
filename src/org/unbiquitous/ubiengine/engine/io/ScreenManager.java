package org.unbiquitous.ubiengine.engine.io;

import org.unbiquitous.ubiengine.engine.core.GameComponents;

/**
 * Class for screens management.
 * @author Pimenta
 *
 */
public final class ScreenManager extends OutputManager {
  /**
   * Use this method ONLY to create local screens.
   * @return The local screen created.
   */
  public Screen create() {
    // FIXME when LWJGL support multiple windows
    
    if (localCreated)
      return null;
    Screen screen = new Screen();
    busyResources.add(screen);
    addMouseKeyboard(screen);
    localCreated = true;
    return screen;
  }
  
  /**
   * Use this method ONLY to destroy local screens.
   * @param screen Screen to be destroyed.
   * @return Returns true if the screen was removed from the container of
   * busy screens.
   */
  public boolean destroy(Screen screen) {
    if (!busyResources.remove(screen))
      return false;
    screen.close();
    removeMouseKeyboard(screen);
    localCreated = false;
    return true;
  }
  
  protected void updateLists() {
    
  }
  
  protected void start(IOResource rsc) {
    
  }
  
  protected void stop(IOResource rsc) {
    
  }
  
  /**
   * Use this method to allocate screens ONLY of the smart space.
   * @return The screen allocated, or null if no screen was available.
   */
  public IOResource alloc() {
    Screen screen = (Screen)super.alloc();
    if (screen == null)
      return null;
    addMouseKeyboard(screen);
    return screen;
  }
  
  /**
   * Use this method to release screens ONLY of the smart space.
   * @return Returns true if the screen was removed from the container of
   * busy screens.
   */
  public boolean free(IOResource screen) {
    if (!super.free(screen))
      return false;
    removeMouseKeyboard((Screen)screen);
    return true;
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  private void addMouseKeyboard(Screen screen) {
    MouseManager mm = GameComponents.get(MouseManager.class);
    if (mm != null && screen.mouse != null)
      mm.screenMouses.add(screen.mouse);
    KeyboardManager km = GameComponents.get(KeyboardManager.class);
    if (km != null && screen.keyboard != null)
      km.screenKeyboards.add(screen.keyboard);
  }
  
  private void removeMouseKeyboard(Screen screen) {
    MouseManager mm = GameComponents.get(MouseManager.class);
    if (mm != null && screen.mouse != null)
      mm.screenMouses.remove(screen.mouse);
    KeyboardManager km = GameComponents.get(KeyboardManager.class);
    if (km != null && screen.keyboard != null)
      km.screenKeyboards.remove(screen.keyboard);
  }
  
  private boolean localCreated = false;
}
