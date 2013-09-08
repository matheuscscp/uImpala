package org.unbiquitous.ubiengine.resources.input;

import java.lang.reflect.Method;

import org.unbiquitous.ubiengine.util.observer.Event;
import org.unbiquitous.ubiengine.util.observer.Subject;
import org.unbiquitous.ubiengine.util.observer.SubjectDevice;

public abstract class InputManager implements Subject {

  public static final class DeviceEvent extends Event {

    private InputDevice input_device;
    
    public DeviceEvent(String event, InputDevice input_device) {
      super(event);
      this.input_device = input_device;
    }
    
    public InputDevice getDevice() {
      return input_device;
    }
  }

  public static String NEWDEVICE  = "NEWDEVICE";
  public static String DEVICEDOWN = "DEVICEDOWN";
  
  protected SubjectDevice subject;
  
  public InputManager() {
    subject = new SubjectDevice(NEWDEVICE, DEVICEDOWN);
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
  
  protected void broadcastNewDevice(InputDevice input_device) throws Throwable {
    subject.broadcast(new DeviceEvent(NEWDEVICE, input_device));
  }
  
  protected void broadcastDeviceDown(InputDevice input_device) throws Throwable {
    subject.broadcast(new DeviceEvent(DEVICEDOWN, input_device));
  }
  
  public abstract void externalRequestAccepted(String transmitter_device);
  public abstract void externalDeviceClosed(String transmitter_device);
  public abstract void sendRequest(InputDevice input_device);
  public abstract boolean isDevicePlugged(InputDevice input_device);
}
