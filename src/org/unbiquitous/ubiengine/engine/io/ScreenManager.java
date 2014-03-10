package org.unbiquitous.ubiengine.engine.io;

import java.util.HashSet;
import java.util.Iterator;

import org.unbiquitous.ubiengine.engine.core.GameComponents;

/**
 * Class for screens management.
 * @author Pimenta
 *
 */
public final class ScreenManager implements OutputManager {
  /**
   * Use this method ONLY to create local screens.
   * @return The local screen created.
   */
  public Screen create() {
    // FIXME when LWJGL support multiple windows
    if (localScreens.size() > 0)
      return null;
    Screen screen = new Screen();
    localScreens.add(screen);
    addMouseKeyboard(screen);
    return screen;
  }
  
  /**
   * Use this method ONLY to destroy local screens.
   * @param screen Screen to be destroyed.
   * @return Returns true if the screen was removed from the container of
   * busy screens.
   */
  public boolean destroy(Screen screen) {
    if (!localScreens.contains(screen))
      return false;
    screen.close();
    localScreens.remove(screen);
    removeMouseKeyboard(screen);
    return true;
  }
  
  /**
   * Use this method to allocate screens ONLY of the smart space.
   * @return The screen allocated, or null if no screen was available.
   */
  public IOResource alloc() {
    return null;
  }
  
  /**
   * Use this method to release screens ONLY of the smart space.
   * @return Returns true if the screen was removed from the container of
   * busy screens.
   */
  public boolean free(IOResource screen) {
    return false;
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  public void update() {
    for (Screen s : localScreens)
      s.update();
  }
  
  public void close() {
    for (Iterator<Screen> i = localScreens.iterator(); i.hasNext();) {
      i.next().close();
      i.remove();
    }
  }
  
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
  
  private HashSet<Screen> localScreens = new HashSet<Screen>();
}
