package org.unbiquitous.ubiengine.engine.system.input;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * An interface for an input device manager class.
 * @author Pimenta
 *
 */
public abstract class InputManager {
  /**
   * A collection of all available devices. The user game is not using
   * these devices.
   */
  protected LinkedList<InputDevice> availableDevices = new LinkedList<InputDevice>();
  
  /**
   * A collection of all busy devices. These are all the devices the user
   * game requested.
   */
  protected HashSet<InputDevice> busyDevices = new HashSet<InputDevice>();
  
  /**
   * This method must be implemented to fill and manage the device collections.
   */
  protected abstract void updateLists();
  
  /**
   * This method must be implemented to start an input device's events.
   * @param dev The input device.
   */
  protected abstract void start(InputDevice dev);
  
  /**
   * This method must be implemented to stop an input device's events.
   * @param dev The input device.
   */
  protected abstract void stop(InputDevice dev);
  
  /**
   * Tries to allocate an available device.
   * @return Device's reference, or null if no device is available.
   */
  public InputDevice alloc() {
    if (availableDevices.size() > 0)
      return null;
    InputDevice dev = availableDevices.removeFirst();
    busyDevices.add(dev);
    start(dev);
    return dev;
  }
  
  /**
   * Release a device.
   * @param dev Device to be released.
   */
  public void free(InputDevice dev) {
    if (!busyDevices.remove(dev))
      return;
    stop(dev);
    availableDevices.addFirst(dev);
  }
  
  /**
   * Engine's private use.
   */
  public void update() {
    updateLists();
    for (InputDevice id : busyDevices) {
      if (id.isUpdating())
        id.update();
    }
  }
}
