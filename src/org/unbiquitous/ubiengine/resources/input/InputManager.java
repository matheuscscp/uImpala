package org.unbiquitous.ubiengine.resources.input;

import java.lang.reflect.Method;

import org.unbiquitous.ubiengine.util.observer.Event;
import org.unbiquitous.ubiengine.util.observer.Subject;
import org.unbiquitous.ubiengine.util.observer.SubjectDevice;

public abstract class InputManager implements Subject {

  public static final class DeviceEvent extends Event {

    private InputDevice input_device;
    
    public DeviceEvent(InputDevice input_device) {
      this.input_device = input_device;
    }
    
    public InputDevice getDevice() {
      return input_device;
    }
  }
  
  public abstract void update() throws Exception;
  
  public static String NEWDEVICE      = "NEWDEVICE";
  public static String DEVICEPLUGGED  = "DEVICEPLUGGED";
  public static String DEVICEDOWN     = "DEVICEDOWN";
  
  protected SubjectDevice subject;
  
  public InputManager() {
    subject = new SubjectDevice(NEWDEVICE, DEVICEPLUGGED, DEVICEDOWN);
  }
  
  public void connect(String event_type, Method handler) {
    subject.connect(event_type, handler);
  }

  public void connect(String event_type, Object observer, Method handler) {
    subject.connect(event_type, observer, handler);
  }

  public void disconnect(Method handler) {
    subject.disconnect(handler);
  }

  public void disconnect(String event_type, Method handler) {
    subject.disconnect(event_type, handler);
  }

  public void disconnect(Object observer) {
    subject.disconnect(observer);
  }

  public void disconnect(String event_type, Object observer) {
    subject.disconnect(event_type, observer);
  }
  
  protected void broadcastNewDevice(InputDevice input_device) throws Exception {
    subject.broadcast(NEWDEVICE, new DeviceEvent(input_device));
  }
  
  protected void broadcastDevicePlugged(InputDevice input_device) throws Exception {
    subject.broadcast(DEVICEPLUGGED, new DeviceEvent (input_device));
  }
  
  protected void broadcastDeviceDown(InputDevice input_device) throws Exception {
    subject.broadcast(DEVICEDOWN, new DeviceEvent(input_device));
  }
  
  public abstract void externalRequestAccepted(String transmitter_device);
  public abstract void externalDeviceClosed(String transmitter_device);
  public abstract void sendRequest(InputDevice input_device);
}
