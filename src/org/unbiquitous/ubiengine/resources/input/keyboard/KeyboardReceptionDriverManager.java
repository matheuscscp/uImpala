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
  
  public void requestAccepted(String transmitter_device) {
    if (keyboard_manager != null)
      keyboard_manager.externalRequestAccepted(transmitter_device);
  }
  
  public void keyboardClosed(String transmitter_device) {
    if (keyboard_manager != null)
      keyboard_manager.externalDeviceClosed(transmitter_device);
  }
  
  public void keyDown(String transmitter_device, int unicode_char) {
    if (keyboard_manager != null)
      keyboard_manager.externalKeyDown(transmitter_device, unicode_char);
  }
  
  public void keyUp(String transmitter_device, int unicode_char) {
    if (keyboard_manager != null)
      keyboard_manager.externalKeyUp(transmitter_device, unicode_char);
  }
}
