package org.unbiquitous.ubiengine.engine.io;

import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.unbiquitous.json.JSONException;
import org.unbiquitous.ubiengine.engine.core.UbiGame.Settings;
import org.unbiquitous.ubiengine.util.ComponentContainer;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.adaptabitilyEngine.ServiceCallException;
import org.unbiquitous.uos.core.driverManager.DriverData;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDevice;

public final class KeyboardManagerOld extends InputManager implements KeyListener {

  private static final class DeviceTuple {
    public UpDevice uos_device;
    public KeyboardSourceOld engine_device = new KeyboardSourceOld();
    
    public DeviceTuple(UpDevice uos_device) {
      this.uos_device = uos_device;
    }
  }
  
  private KeyboardSourceOld main_keyboard;
  private HashMap<String, DeviceTuple> keyboards = new HashMap<String, DeviceTuple>();
  private Gateway gateway;
  private Queue<String> plugged_devices = new LinkedList<String>();
  private Queue<String> down_devices = new LinkedList<String>();
  private Map<String, Object> request_map;
  
  public KeyboardManagerOld(ComponentContainer components) {
    gateway = components.get(Gateway.class);
    
    main_keyboard = new KeyboardSourceOld();
    main_keyboard.active(true);
    components.get(ScreenOld.class).addKeyListener(this);
    
    KeyboardReceptionDriverManager.init(this, gateway);
    
    request_map = new HashMap<String, Object>();
    try {
      request_map.put("receiver_device", gateway.getCurrentDevice().toJSON().toString());
    } catch (JSONException e) {
    }
    request_map.put("application_name", (String) components.get(Settings.class).get("window_title"));
  }
  
  public KeyboardSourceOld getMainKeyboard() {
    return main_keyboard;
  }
  
  public void update() {
    updateDeviceList();
    
    // updating plugged devices
    while (!plugged_devices.isEmpty()) {
      String transmitter_device = plugged_devices.poll();
      DeviceTuple d_tuple = keyboards.get(transmitter_device);
      d_tuple.engine_device.active(true);
      broadcastDevicePlugged(d_tuple.engine_device);
    }
    
    // updating down devices
    while (!down_devices.isEmpty()) {
      KeyboardSourceOld kdev = keyboards.remove(down_devices.poll()).engine_device;
      kdev.active(false);
      broadcastDeviceDown(kdev);
    }
    
    main_keyboard.update();
    
    // updating external keyboards
    Iterator<?> it = keyboards.entrySet().iterator();
    while (it.hasNext())
      ((DeviceTuple) ((Map.Entry<?, ?>) it.next()).getValue()).engine_device.update();
  }
  
  private void updateDeviceList() {
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
    plugged_devices.add(transmitter_device);
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

  public void sendRequest(InputResource input_device) {
    Iterator<?> it = keyboards.entrySet().iterator();
    while (it.hasNext()) {
      DeviceTuple device_tuple = (DeviceTuple) ((Map.Entry<?, ?>) it.next()).getValue();
      if ((InputResource) device_tuple.engine_device == input_device) {
        try {
          gateway.callService(device_tuple.uos_device, "receiveRequest", KeyboardReceptionDriver.TRANSMISSION_DRIVER, null, null, request_map);
        } catch (ServiceCallException e) {
          e.printStackTrace();
        }
        return;
      }
    }
  }
}
