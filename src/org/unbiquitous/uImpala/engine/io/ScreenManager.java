package org.unbiquitous.uImpala.engine.io;

import java.util.HashSet;
import java.util.List;

/**
 * Class for screens management.
 * @author Pimenta
 *
 */
public class ScreenManager implements OutputManager {
  /**
   * Use this method ONLY to create local screens.
   * @return The local screen created.
   */
  public Screen create() {
    Screen screen = Screen.create();
    if (screen != null)
      localScreens.add(screen);
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
    for (Screen s : localScreens)
      s.close();
  }
  
  private HashSet<Screen> localScreens = new HashSet<Screen>();
  
	@SuppressWarnings("unchecked")
	public List<IOResource> list() {
		return (List<IOResource>) localScreens.clone();
	}
}
