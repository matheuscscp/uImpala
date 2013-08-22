package org.unbiquitous.ubiengine.resources.input;

import org.unbiquitous.ubiengine.resources.input.keyboard.KeyboardManager;
import org.unbiquitous.ubiengine.resources.input.mouse.MouseManager;
import org.unbiquitous.ubiengine.util.ComponentContainer;

public final class InputManager {

  private KeyboardManager keyboard_manager;
  private MouseManager mouse_manager;
  
  public InputManager(ComponentContainer components) {
    keyboard_manager = new KeyboardManager(components);
    mouse_manager = new MouseManager(components);
  }
  
  public KeyboardManager getKeyboardManager() {
    return keyboard_manager;
  }
  
  public MouseManager getMouseManager() {
    return mouse_manager;
  }
  
  public void update() throws Throwable {
    keyboard_manager.update();
    mouse_manager.update();
  }
}
