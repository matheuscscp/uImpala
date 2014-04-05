package org.unbiquitous.uImpala.engine.io;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.unbiquitous.uImpala.engine.core.GameComponents;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.adaptabitilyEngine.NotifyException;
import org.unbiquitous.uos.core.driverManager.DriverData;

/**
 * Class for keyboards management.
 * @author Pimenta
 *
 */
public class KeyboardManager implements InputManager {
  public KeyboardManager() {
    gateway = GameComponents.get(Gateway.class);
  }
  
  public IOResource alloc() {
    if (availableKeyboards.size() == 0)
      return null;
    
    Iterator<Entry<String, KeyboardSource>> i = availableKeyboards.entrySet().iterator();
    Entry<String, KeyboardSource> e = i.next();
    KeyboardSource ks = e.getValue();
    try {
      gateway.register(
        ks,
        ks.driver.getDevice(),
        ks.driver.getDriver().getName(),
        ks.driver.getInstanceID(),
        "EVENT_KEY_DOWN",
        null
      );
      gateway.register(
        ks,
        ks.driver.getDevice(),
        ks.driver.getDriver().getName(),
        ks.driver.getInstanceID(),
        "EVENT_KEY_UP",
        null
      );
      ks.updating = true;
    } catch (NotifyException e1) {
      ks.updating = false;
    }
    busyKeyboards.put(e.getKey(), ks);
    i.remove();
    return ks;
  }
  
  public boolean free(IOResource rsc) {
    // TODO Auto-generated method stub
    return false;
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  public void update() {
    updateLists();
    for (KeyboardSource ks : screenKeyboards)
      ks.update();
    for (Entry<String, KeyboardSource> e : busyKeyboards.entrySet())
      e.getValue().update();
  }
  
  public void close() {
    
  }
  
  private void updateLists() {
    // get all drivers
    List<DriverData> currentDrivers = gateway.listDrivers("KEYBOARD_DRIVER");
    
    // clear the list if no driver was found
    if (currentDrivers == null) {
      clear(availableKeyboards);
      clear(busyKeyboards);
      return;
    }
    
    // checking for new devices
    HashSet<String> currentDriversIDs = new HashSet<String>();
    for (DriverData driver : currentDrivers) {
      String id = driver.getDevice().getName() + driver.getInstanceID();
      currentDriversIDs.add(id);
      if (availableKeyboards.get(id) == null && busyKeyboards.get(id) == null)
        availableKeyboards.put(id, new KeyboardSource(256, driver));
    }
    
    // checking for disconnected devices
    checkDisconnected(availableKeyboards, currentDriversIDs);
    checkDisconnected(busyKeyboards, currentDriversIDs);
  }
  
  private void checkDisconnected(HashMap<String, KeyboardSource> container, HashSet<String> currentDriversIDs) {
    for (Iterator<Entry<String, KeyboardSource>> i = container.entrySet().iterator(); i.hasNext();) {
      Entry<String, KeyboardSource> e = i.next();
      if (!currentDriversIDs.contains(e.getKey())) {
        e.getValue().close();
        i.remove();
      }
    }
  }
  
  private void clear(HashMap<String, KeyboardSource> container) {
    for (Iterator<Entry<String, KeyboardSource>> i = container.entrySet().iterator(); i.hasNext();) {
      i.next().getValue().close();
      i.remove();
    }
  }
  
  /**
   * Engine's private use.
   */
  public void add(KeyboardSource ks) {
    screenKeyboards.add(ks);
  }
  
  /**
   * Engine's private use.
   */
  public void remove(KeyboardSource ks) {
    screenKeyboards.remove(ks);
  }
  
  /**
   * Engine's private use.
   */
  protected HashSet<KeyboardSource> screenKeyboards = new HashSet<KeyboardSource>();
  
  private Gateway gateway;
  private HashMap<String, KeyboardSource> availableKeyboards = new HashMap<String, KeyboardSource>();
  private HashMap<String, KeyboardSource> busyKeyboards = new HashMap<String, KeyboardSource>();
}
