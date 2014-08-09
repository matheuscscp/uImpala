package org.unbiquitous.uImpala.engine.io;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.unbiquitous.uImpala.engine.core.GameComponents;
import org.unbiquitous.uImpala.util.observer.Observation;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
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
    
    // remove first available keyboard
    Iterator<Entry<String, KeyboardSource>> i = availableKeyboards.entrySet().iterator();
    Entry<String, KeyboardSource> e = i.next();
    String id = e.getKey();
    KeyboardSource ks = e.getValue();
    i.remove();
    
    ks.start();
    
    busyKeyboards.put(id, ks);
    
    return ks;
  }
  
  public boolean free(IOResource rsc) {
    KeyboardSource ks = (KeyboardSource)rsc;
    String id = ks.id();
    
    if (busyKeyboards.remove(id) == null)
      return false;
    
    ks.stop();
    
    availableKeyboards.put(id, ks);
    
    return true;
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
    for (KeyboardSource ks : screenKeyboards)
      ks.close();
    for (Entry<String, KeyboardSource> e : busyKeyboards.entrySet())
      e.getValue().close();
  }
  
  /**
   * Allows to connect to an event in all available sources
   */
  public void connect(Integer eventType, Observation obs){
	  for (KeyboardSource ks : screenKeyboards){
		  ks.connect(eventType, obs);
	  }
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
        e.getValue().kill();
        i.remove();
      }
    }
  }
  
  private void clear(HashMap<String, KeyboardSource> container) {
    for (Iterator<Entry<String, KeyboardSource>> i = container.entrySet().iterator(); i.hasNext();) {
      i.next().getValue().kill();
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
