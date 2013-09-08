package org.unbiquitous.ubiengine.resources.input.keyboard;

import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.unbiquitous.json.JSONException;
import org.unbiquitous.ubiengine.game.GameSettings;
import org.unbiquitous.ubiengine.resources.input.InputDevice;
import org.unbiquitous.ubiengine.resources.input.InputManager;
import org.unbiquitous.ubiengine.resources.video.Screen;
import org.unbiquitous.ubiengine.util.ComponentContainer;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.adaptabitilyEngine.ServiceCallException;
import org.unbiquitous.uos.core.driverManager.DriverData;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDevice;

public final class KeyboardManager extends InputManager implements KeyListener {

  private static final class DeviceTuple {
    public boolean plugged = false;
    public UpDevice uos_device;
    public KeyboardDevice engine_device;
    
    public DeviceTuple(UpDevice uos_device) {
      this.uos_device = uos_device;
      engine_device = new KeyboardDevice();
    }
  }
  
  private KeyboardDevice main_keyboard;
  private HashMap<String, DeviceTuple> keyboards = new HashMap<String, DeviceTuple>();
  private Gateway gateway;
  private Queue<String> down_devices = new LinkedList<String>();
  private Map<String, Object> request_map;
  
  public KeyboardManager(ComponentContainer components) {
    gateway = components.get(Gateway.class);
    
    main_keyboard = new KeyboardDevice();
    components.get(Screen.class).addKeyListener(this);
    
    KeyboardReceptionDriverManager.init(this, gateway);
    
    request_map = new HashMap<String, Object>();
    try {
      request_map.put("receiver_device", gateway.getCurrentDevice().toJSON().toString());
    } catch (JSONException e) {
    }
    request_map.put("application_name", (String) components.get(GameSettings.class).get("window_title"));
  }
  
  public KeyboardDevice getMainKeyboard() {
    return main_keyboard;
  }
  
  public void update() throws Throwable {
    updateDeviceList();
    
    // updating down devices
    while (!down_devices.isEmpty())
      broadcastDeviceDown(keyboards.remove(down_devices.poll()).engine_device);
    
    main_keyboard.update();
    
    // updating external keyboards
    Iterator<?> it = keyboards.entrySet().iterator();
    while (it.hasNext())
      ((DeviceTuple) ((Map.Entry<?, ?>) it.next()).getValue()).engine_device.update();
  }
  
  private void updateDeviceList() throws Throwable {
    // get all the transmission drivers in the smart space
    List<DriverData> current_drivers = gateway.listDrivers(KeyboardReceptionDriver.TRANSMISSION_DRIVER);
    if (current_drivers == null) {
      // clear device list
      Iterator<?> it = keyboards.entrySet().iterator();
      while (it.hasNext())
        down_devices.add((String) ((Map.Entry<?, ?>) it.next()).getKey());
      
      return;
    }
    
    // get drivers devices
    HashSet<String> current_devices = new HashSet<String>();
    for (DriverData driver : current_drivers) {
      UpDevice device = driver.getDevice();
      String device_name = device.getName();
      current_devices.add(device_name);
      
      // checking for new devices
      if (keyboards.get(device_name) == null) {
        DeviceTuple device_tuple = new DeviceTuple(device);
        keyboards.put(device_name, device_tuple);
        broadcastNewDevice(device_tuple.engine_device);
      }
    }
    
    // checking for down devices
    Iterator<?> it = keyboards.entrySet().iterator();
    while (it.hasNext()) {
      String device = (String) ((Map.Entry<?, ?>) it.next()).getKey();
      if (!current_devices.contains(device))
        down_devices.add(device);
    }
  }
  
  public void keyPressed(java.awt.event.KeyEvent e) {
    main_keyboard.forceKeyPressed(e.getKeyCode());
  }

  public void keyReleased(java.awt.event.KeyEvent e) {
    main_keyboard.forceKeyReleased(e.getKeyCode());
  }

  public void keyTyped(java.awt.event.KeyEvent e) {
    
  }
  
  public void externalRequestAccepted(String transmitter_device) {
    keyboards.get(transmitter_device).plugged = true;
  }
  
  public void externalDeviceClosed(String transmitter_device) {
    down_devices.add(transmitter_device);
  }
  
  public void externalKeyDown(String transmitter_device, int unicode_char) {
    keyboards.get(transmitter_device).engine_device.forceKeyPressed(unicode_char);
  }
  
  public void externalKeyUp(String transmitter_device, int unicode_char) {
    keyboards.get(transmitter_device).engine_device.forceKeyReleased(unicode_char);
  }

  public void sendRequest(InputDevice input_device) {
    Iterator<?> it = keyboards.entrySet().iterator();
    while (it.hasNext()) {
      DeviceTuple device_tuple = (DeviceTuple) ((Map.Entry<?, ?>) it.next()).getValue();
      if ((InputDevice) device_tuple.engine_device == input_device) {
        try {
          gateway.callService(device_tuple.uos_device, "receiveRequest", KeyboardReceptionDriver.TRANSMISSION_DRIVER, null, null, request_map);
        } catch (ServiceCallException e) {
          e.printStackTrace();
        }
        return;
      }
    }
  }
  
  public boolean isDevicePlugged(InputDevice input_device) {
    Iterator<?> it = keyboards.entrySet().iterator();
    while (it.hasNext()) {
      DeviceTuple device_tuple = (DeviceTuple) ((Map.Entry<?, ?>) it.next()).getValue();
      if ((InputDevice) device_tuple.engine_device == input_device)
        return device_tuple.plugged;
    }
    return false;
  }
}
