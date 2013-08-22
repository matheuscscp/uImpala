package org.unbiquitous.ubiengine.resources.input.keyboard;

import java.util.HashMap;
import java.util.Map;

import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.adaptabitilyEngine.ServiceCallException;

public class KeyboardReceptionDriverManager {
  
  private KeyboardManager keyboard_manager = null;
  
  public static void init(KeyboardManager keyboard_manager, Gateway gateway) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("manager", new KeyboardReceptionDriverManager(keyboard_manager));
    try {
      gateway.callService(gateway.getCurrentDevice(), "setManager", KeyboardReceptionDriver.RECEPTION_DRIVER, null, null, map);
    } catch (ServiceCallException e) {
    }
  }
  
  public KeyboardReceptionDriverManager(KeyboardManager keyboard_manager) {
    this.keyboard_manager = keyboard_manager;
  }
  
  public void requestAccepted(String device_name) {
    if (keyboard_manager != null)
      keyboard_manager.externalRequestAccepted(device_name);
  }
  
  public void keyboardClosed(String device_name) {
    if (keyboard_manager != null)
      keyboard_manager.externalKeyboardClosed(device_name);
  }
  
  public void keyDown(String device_name, int unicodeChar) {
    if (keyboard_manager != null)
      keyboard_manager.externalKeyDown(device_name, unicodeChar);
  }
  
  public void keyUp(String device_name, int unicodeChar) {
    if (keyboard_manager != null)
      keyboard_manager.externalKeyUp(device_name, unicodeChar);
  }
}
