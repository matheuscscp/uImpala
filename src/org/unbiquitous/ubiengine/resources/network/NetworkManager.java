package org.unbiquitous.ubiengine.resources.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.unbiquitous.ubiengine.resources.input.KeyboardReceptionDriver;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.adaptabitilyEngine.ServiceCallException;
import org.unbiquitous.uos.core.driverManager.DriverData;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDevice;


public final class NetworkManager {
  
  private Gateway gateway = null;
  private List<UpDevice> old_devices = null;
  private String application_name = null;

  public void setGateway(Gateway gateway) {
    this.gateway = gateway;
  }

  public void setApplicationName(String application_name) {
    this.application_name = application_name;
  }
  
  public void update() {
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
  }
}
