package org.unbiquitous.ubiengine.resources.input;

import java.util.HashMap;
import java.util.Map;

import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.adaptabitilyEngine.ServiceCallException;


public class KeyboardReceptionDriverManager {
  
  private boolean receiving = false;
  private InputManager input_manager = null;
  
  public static void init(InputManager input_manager, Gateway gateway) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("manager", new KeyboardReceptionDriverManager(input_manager));
    try {
      gateway.callService(gateway.getCurrentDevice(), "setManager", KeyboardReceptionDriver.RECEPTION_DRIVER, null, null, map);
    } catch (ServiceCallException e) {
    }
  }
  
  public KeyboardReceptionDriverManager(InputManager input_manager) {
    this.input_manager = input_manager;
  }
  
  public void requestAccepted() {
    receiving = true;
  }
  
  public void keyboardClosed() {
    receiving = false;
  }
  
  public void keyDown(int unicodeChar) {
    if (receiving && input_manager != null)
      input_manager.forceKeyPressed(unicodeChar);
  }
  
  public void keyUp(int unicodeChar) {
    if (receiving && input_manager != null)
      input_manager.forceKeyReleased(unicodeChar);
  }
}
