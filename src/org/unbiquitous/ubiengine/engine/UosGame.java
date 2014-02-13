package org.unbiquitous.ubiengine.engine;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListResourceBundle;

import org.unbiquitous.ubiengine.engine.resources.input.keyboard.KeyboardManager;
import org.unbiquitous.ubiengine.engine.resources.input.keyboard.KeyboardReceptionDriver;
import org.unbiquitous.ubiengine.engine.resources.input.mouse.MouseManager;
import org.unbiquitous.ubiengine.engine.resources.time.DeltaTime;
import org.unbiquitous.ubiengine.engine.resources.video.Screen;
import org.unbiquitous.ubiengine.util.ComponentContainer;
import org.unbiquitous.ubiengine.util.Logger;
import org.unbiquitous.uos.core.UOS;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.applicationManager.UosApplication;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyDeploy;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyStart;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyUndeploy;
import org.unbiquitous.uos.network.socket.connectionManager.TCPConnectionManager;
import org.unbiquitous.uos.network.socket.radar.PingRadar;

/**
 * The game engine class. Extend it only to implement getSettings().
 * @author Pimenta
 *
 */
public abstract class UosGame implements UosApplication {
  /**
   * Just a "typedef" for HashMap<String, Object>.
   * @author Pimenta
   *
   */
  @SuppressWarnings("serial")
  public class Settings extends HashMap<String, Object> {
    
  }
  
  /**
   * Must be implemented by the game class.
   * @return Reference to the game initial settings.
   */
  protected abstract Settings getSettings();
  
  /**
   * Use this method in main() to start the game.
   * @param game Class that extends UosGame.
   */
  public static void start(final Class<?> game) {
    new UOS().init(new ListResourceBundle() {
      protected Object[][] getContents() {
        return new Object[][] {
          {"ubiquitos.connectionManager", TCPConnectionManager.class.getName()},
          {"ubiquitos.radar", PingRadar.class.getName()},
          {"ubiquitos.eth.tcp.port", "14984"},
          {"ubiquitos.eth.tcp.passivePortRange", "14985-15000"},
          {"ubiquitos.uos.deviceName","compDevice"},
          {"ubiquitos.driver.deploylist", KeyboardReceptionDriver.class.getName()},
          {"ubiquitos.application.deploylist", game.getName()}
        };
      }
    });
  }
  
  private ComponentContainer components = new ComponentContainer();
  private LinkedList<GameState> states = new LinkedList<GameState>();
  private Screen screen;
  private DeltaTime deltatime;
  private KeyboardManager keyboard_manager;
  private MouseManager mouse_manager;
  
  private void init(Gateway gateway) {
    components.put(UosGame.class, this);
    
    components.put(Gateway.class, gateway);
    
    deltatime = new DeltaTime();
    components.put(DeltaTime.class, deltatime);
    
    Settings initial_settings = getSettings();
    components.put(Settings.class, initial_settings);
    
    screen = new Screen(
        (String) initial_settings.get("window_title"),
        ((Integer) initial_settings.get("window_width")).intValue(),
        ((Integer) initial_settings.get("window_height")).intValue(),
        deltatime
    );
    components.put(Screen.class, screen);
    
    keyboard_manager = new KeyboardManager(components);
    components.put(KeyboardManager.class, keyboard_manager);
    
    mouse_manager = new MouseManager(components);
    components.put(MouseManager.class, mouse_manager);
    
      try {
        states.add(
          ((GameState) Class.forName((String) initial_settings.get("first_state"))
          .getConstructor().newInstance()).setComponents(components)
        );
      } catch (Exception e) {
        throw new Error(e.getMessage());
      }
  }
  
  private void close() {
    screen.close();
  }
  
  private void run() {
    while (states.size() > 0) {
      deltatime.start();
      input();
      update();
      render();
      deltatime.finish();
      checkStateChange();
    }
  }
  
  private void input() {
    keyboard_manager.update();
    mouse_manager.update();
    for (GameState gs : states)
      gs.input();
  }
  
  private void update() {
    for (GameState gs : states)
      gs.update();
  }
  
  private void render() {
    for (GameState gs : states)
      gs.render();
    screen.update();
  }
  
  private enum ChangeOption {
    NA,
    CHANGE,
    PUSH,
    POP
  }
  
  private GameState state_change = null;
  private GameState.Args pop_args = null;
  private ChangeOption change_option = ChangeOption.NA;
  
  private void checkStateChange() {
    switch (change_option) {
      case CHANGE:
        states.removeLast();
        states.add(state_change);
        break;
        
      case PUSH:
        states.add(state_change);
        break;
        
      case POP:
        states.removeLast();
        if (states.size() > 0)
          states.getLast().wakeup(pop_args);
        break;
        
      default:
        break;
    }
    state_change = null;
    change_option = ChangeOption.NA;
  }
  
  public void change(GameState state) {
    state_change = state;
    change_option = ChangeOption.CHANGE;
  }
  
  public void push(GameState state) {
    state_change = state;
    change_option = ChangeOption.PUSH;
  }
  
  public void pop(GameState.Args args) {
    pop_args = args;
    change_option = ChangeOption.POP;
  }
  
  public void start(Gateway gateway, OntologyStart ontology) {
    try {
      init(gateway);
      run();
    } catch (Error e) {
      Logger.log(e);
    }
  }
  
  public void stop() {
    close();
  }
  
  public void init(OntologyDeploy ontology, String appId) {
    
  }
  
  public void tearDown(OntologyUndeploy ontology) {
    
  }
}
