package org.unbiquitous.ubiengine.resources.input.keyboard;

import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.unbiquitous.ubiengine.game.Settings;
import org.unbiquitous.ubiengine.resources.video.Screen;
import org.unbiquitous.ubiengine.util.ComponentContainer;
import org.unbiquitous.ubiengine.util.observer.ObservationStack;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;

public final class KeyboardManager implements KeyListener {

  private KeyboardDevice keyboard;
  private HashMap<String, KeyboardDevice> external_keyboards = new HashMap<String, KeyboardDevice>();
  private ObservationStack observation_stack;
  private Gateway gateway;
  private String application_name;
  
  public KeyboardManager(ComponentContainer components) {
    observation_stack = components.get(ObservationStack.class);
    gateway = components.get(Gateway.class);
    application_name = (String) components.get(Settings.class).get("window_title");
    
    keyboard = new KeyboardDevice(observation_stack);
    components.get(Screen.class).addKeyListener(this);
    KeyboardReceptionDriverManager.init(this, gateway);
  }
  
  public KeyboardDevice getKeyboard() {
    return keyboard;
  }
  
  public KeyboardDevice newExternalKeyboard() {
    KeyboardDevice new_keyboard = null;
    
    // find a new device
    Iterator<?> it = external_keyboards.entrySet().iterator();
    while (it.hasNext()) {
      KeyboardDevice device = (KeyboardDevice) ((Map.Entry<?, ?>) it.next()).getValue();
      if (device == null) {
        new_keyboard = new KeyboardDevice(observation_stack);
        break;
      }
    }
    
    return new_keyboard;
  }
  
  public void update() throws Throwable {
    keyboard.update();
    
    // updating external keyboards
    Iterator<?> it = external_keyboards.entrySet().iterator();
    while (it.hasNext()) {
      KeyboardDevice device = (KeyboardDevice) ((Map.Entry<?, ?>) it.next()).getValue();
      if (device != null)
        device.update();
    }
    
    updateDeviceList();
  }

  private void updateDeviceList() {
    
    
    
    /*
    if (gateway == null)
      return;
    
    List<DriverData> current_drivers = gateway.listDrivers(KeyboardReceptionDriver.TRANSMISSION_DRIVER);
    if (current_drivers == null) {
      old_devices = new ArrayList<UpDevice>();
      return;
    }
    
    List<UpDevice> current_devices = new ArrayList<UpDevice>();
    List<UpDevice> new_devices = new ArrayList<UpDevice>();
    
    // get current devices
    for (DriverData driver : current_drivers)
      current_devices.add(driver.getDevice());
    
    // find new devices
    for (UpDevice current_device : current_devices) {
      int i = 0;
      while (i < old_devices.size()) {
        if (old_devices.get(i).getName().equals(current_device.getName()))
          break;
        else
          ++i;
      }
      if (i == old_devices.size())
        new_devices.add(current_device);
    }
    
    // call service for new devices
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("device_name", gateway.getCurrentDevice().getName());
    map.put("application_name", application_name);
    for (UpDevice device : new_devices) {
      try {
        gateway.callService(device, "receiveRequest", KeyboardReceptionDriver.TRANSMISSION_DRIVER, null, null, map);
      } catch (ServiceCallException e) {
      }
    }
    
    old_devices = current_devices;
    */
  }
  
  public void keyPressed(java.awt.event.KeyEvent e) {
    keyboard.forceKeyPressed(e.getKeyCode());
  }

  public void keyReleased(java.awt.event.KeyEvent e) {
    keyboard.forceKeyReleased(e.getKeyCode());
  }

  public void keyTyped(java.awt.event.KeyEvent e) {
    
  }
  
  public void externalRequestAccepted(String device_name) {
    
  }
  
  public void externalKeyboardClosed(String device_name) {
    try {
      KeyboardDevice keyboard_device = external_keyboards.get(device_name);
      if (keyboard_device != null)
        keyboard_device.unplug();
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }
  
  public void externalKeyDown(String device_name, int unicodeChar) {
    KeyboardDevice keyboard_device = external_keyboards.get(device_name);
    if (keyboard_device != null) {
      if (keyboard_device.isPlugged())
        keyboard_device.forceKeyPressed(unicodeChar);
    }
  }
  
  public void externalKeyUp(String device_name, int unicodeChar) {
    KeyboardDevice keyboard_device = external_keyboards.get(device_name);
    if (keyboard_device != null) {
      if (keyboard_device.isPlugged())
        keyboard_device.forceKeyReleased(unicodeChar);
    }
  }
}
