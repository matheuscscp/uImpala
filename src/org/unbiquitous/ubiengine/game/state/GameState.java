package org.unbiquitous.ubiengine.game.state;

import org.unbiquitous.ubiengine.resources.input.keyboard.KeyboardDevice;
import org.unbiquitous.ubiengine.resources.input.keyboard.KeyboardManager;
import org.unbiquitous.ubiengine.resources.input.mouse.MouseDevice;
import org.unbiquitous.ubiengine.resources.input.mouse.MouseManager;
import org.unbiquitous.ubiengine.util.ComponentContainer;
import org.unbiquitous.ubiengine.util.observer.Event;
import org.unbiquitous.ubiengine.util.observer.MissingEventType;

public abstract class GameState {
  protected ComponentContainer components;
  
  public GameState(ComponentContainer components) {
    this.components = components;
    
    try {
      // connecting the game state to the KeyboardManager.NEWDEVICE event
      components.get(KeyboardManager.class).connect(
        KeyboardManager.NEWDEVICE,
        this,
        GameState.class.getDeclaredMethod("handleNewKeyboardDevice", Event.class)
      );

      // connecting the game state to the KeyboardManager.DEVICEDOWN event
      components.get(KeyboardManager.class).connect(
        KeyboardManager.DEVICEDOWN,
        this,
        GameState.class.getDeclaredMethod("handleKeyboardDeviceDown", Event.class)
      );

      // connecting the game state to the MouseManager.NEWDEVICE event
      components.get(MouseManager.class).connect(
          MouseManager.NEWDEVICE,
        this,
        GameState.class.getDeclaredMethod("handleNewMouseDevice", Event.class)
      );
      
      // connecting the game state to the MouseManager.DEVICEDOWN event
      components.get(MouseManager.class).connect(
          MouseManager.DEVICEDOWN,
        this,
        GameState.class.getDeclaredMethod("handleMouseDeviceDown", Event.class)
      );
    } catch (NoSuchMethodException e) {
    } catch (SecurityException e) {
    } catch (MissingEventType e) {
    }
  }
  
  public abstract void input();
  public abstract void update();
  public abstract void render();

  protected void handleNewKeyboardDevice(Event event) {
    handleNewKeyboardDevice((KeyboardDevice) ((KeyboardManager.DeviceEvent) event).getDevice());
  }

  protected void handleNewKeyboardDevice(KeyboardDevice keyboard_device) {
    
  }

  protected void handleKeyboardDeviceDown(Event event) {
    handleKeyboardDeviceDown((KeyboardDevice) ((KeyboardManager.DeviceEvent) event).getDevice());
  }

  protected void handleKeyboardDeviceDown(KeyboardDevice keyboard_device) {
    
  }
  
  protected void handleNewMouseDevice(Event event) {
    handleNewMouseDevice((MouseDevice) ((MouseManager.DeviceEvent) event).getDevice());
  }
  
  protected void handleNewMouseDevice(MouseDevice mouse_device) {
    
  }
  
  protected void handleMouseDeviceDown(Event event) {
    handleMouseDeviceDown((MouseDevice) ((MouseManager.DeviceEvent) event).getDevice());
  }
  
  protected void handleMouseDeviceDown(MouseDevice mouse_device) {
    
  }
}
