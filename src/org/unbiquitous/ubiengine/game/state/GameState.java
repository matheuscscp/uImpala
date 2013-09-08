package org.unbiquitous.ubiengine.game.state;

import org.unbiquitous.ubiengine.resources.input.keyboard.KeyboardDevice;
import org.unbiquitous.ubiengine.resources.input.keyboard.KeyboardManager;
import org.unbiquitous.ubiengine.resources.input.mouse.MouseDevice;
import org.unbiquitous.ubiengine.resources.input.mouse.MouseManager;
import org.unbiquitous.ubiengine.util.ComponentContainer;
import org.unbiquitous.ubiengine.util.observer.Event;

public abstract class GameState {
  
  public abstract void init(GameStateArgs args);
  public abstract void close();
  
  public abstract void input();
  public abstract void update();
  public abstract void render();

  protected ComponentContainer components;
  
  public GameState(ComponentContainer components, GameStateArgs args) {
    this.components = components;
    
    KeyboardManager keyboard_manager = components.get(KeyboardManager.class);
    MouseManager mouse_manager = components.get(MouseManager.class);
    
    try {
      // connecting the game state to the KeyboardManager.NEWDEVICE event
      keyboard_manager.connect(
        KeyboardManager.NEWDEVICE,
        this,
        GameState.class.getDeclaredMethod("handleNewKeyboardDevice", Event.class)
      );
  
      // connecting the game state to the KeyboardManager.DEVICEDOWN event
      keyboard_manager.connect(
        KeyboardManager.DEVICEDOWN,
        this,
        GameState.class.getDeclaredMethod("handleKeyboardDeviceDown", Event.class)
      );
  
      // connecting the game state to the MouseManager.NEWDEVICE event
      mouse_manager.connect(
        MouseManager.NEWDEVICE,
        this,
        GameState.class.getDeclaredMethod("handleNewMouseDevice", Event.class)
      );
      
      // connecting the game state to the MouseManager.DEVICEDOWN event
      mouse_manager.connect(
        MouseManager.DEVICEDOWN,
        this,
        GameState.class.getDeclaredMethod("handleMouseDeviceDown", Event.class)
      );
    }
    catch (NoSuchMethodException e) {
    }
    
    init(args);
  }
  
  public void delete() {
    close();
    
    components.get(KeyboardManager.class).disconnect(this);
    components.get(MouseManager.class).disconnect(this);
  }
  
  public void handleUnstack(GameStateArgs args) throws Exception {
    
  }

  public boolean handleQuit() {
    return true;
  }
  
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
