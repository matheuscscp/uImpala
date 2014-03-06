package org.unbiquitous.ubiengine.engine.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListResourceBundle;

import org.unbiquitous.ubiengine.engine.io.InputManager;
import org.unbiquitous.ubiengine.engine.io.KeyboardReceptionDriver;
import org.unbiquitous.ubiengine.engine.io.OutputManager;
import org.unbiquitous.ubiengine.engine.time.DeltaTime;
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
 * The game class. Extend it only to implement getSettings().
 * @author Pimenta
 *
 */
public abstract class UbiGame implements UosApplication {
  /**
   * Must be implemented by the game class.
   * @return Reference to the game initial settings.
   */
  protected abstract GameSettings getSettings();
  
  /**
   * Use this method in main() to start the game.
   * @param game Class{@literal <}?{@literal >} that extends UosGame.
   */
  protected static void run(final Class<? extends UbiGame> game) {
    new UOS().init(new ListResourceBundle() {
      protected Object[][] getContents() {
        return new Object[][] {
          {"ubiquitos.connectionManager", TCPConnectionManager.class.getName()},
          {"ubiquitos.radar", PingRadar.class.getName()},
          {"ubiquitos.eth.tcp.port", "14984"},
          {"ubiquitos.eth.tcp.passivePortRange", "14985-15000"},
          //{"ubiquitos.uos.deviceName","compDevice"},FIXME
          {"ubiquitos.driver.deploylist", KeyboardReceptionDriver.class.getName()},
          {"ubiquitos.application.deploylist", game.getName()}
        };
      }
    });
  }
  
  /**
   * Call to change the current game scene.
   * @param scene New game scene.
   */
  public void change(GameScene scene) {
    if (scene == null)
      return;
    scene_change = scene;
    change_option = ChangeOption.CHANGE;
  }
  
  /**
   * Call to push a game scene.
   * @param scene Game scene to be pushed.
   */
  public void push(GameScene scene) {
    if (scene == null)
      return;
    scene_change = scene;
    change_option = ChangeOption.PUSH;
  }
  
  /**
   * Call to pop the current game scene.
   * @param args Arguments to be passed to the new current game scene.
   */
  public void pop(Object... args) {
    pop_args = args;
    change_option = ChangeOption.POP;
  }
  
  /**
   * Call to shutdown.
   */
  public void quit() {
    change_option = ChangeOption.QUIT;
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  private GameSettings settings;
  private LinkedList<GameScene> scenes = new LinkedList<GameScene>();
  private List<InputManager> inputs = new ArrayList<InputManager>();
  private List<OutputManager> outputs = new ArrayList<OutputManager>();
  private DeltaTime deltatime = new DeltaTime();
  
  private enum ChangeOption {
    NA, CHANGE, PUSH, POP, QUIT
  }
  
  private GameScene scene_change = null;
  private Object[] pop_args = null;
  private ChangeOption change_option = ChangeOption.NA;
  
  /**
   * uOS's private use.
   */
  public void start(Gateway gateway, OntologyStart ontology) {
    try {
      init(gateway);
      while (scenes.size() > 0) {
        deltatime.update();
        for (InputManager im : inputs)
          im.update();
        for (GameScene gs : scenes) {
          if (!gs.frozen)
            gs.update();
        }
        for (GameScene gs : scenes) {
          if (!gs.frozen || (gs.frozen && gs.visible))
            gs.render();
        }
        for (OutputManager om : outputs)
          om.update();
        updateStack();
        deltatime.sync();
      }
      close();
    } catch (Error e) {
      String root_path;
      try {
        root_path = (String)settings.get("root_path");
      } catch (Exception e1) {
        root_path = ".";
        Logger.log(new Error("Root path not reachable"), root_path + "/ErrorLog.txt");
      }
      Logger.log(e, root_path + "/ErrorLog.txt");
      throw e;
    }
  }
  
  /**
   * uOS's private use.
   */
  public void stop() {
    
  }
  
  /**
   * uOS's private use.
   */
  public void init(OntologyDeploy ontology, String appId) {
    
  }
  
  /**
   * uOS's private use.
   */
  public void tearDown(OntologyUndeploy ontology) {
    
  }
  
  @SuppressWarnings("unchecked")
  private void init(Gateway gateway) {
    GameComponents.put(GameSettings.class, settings = getSettings().validate());
    GameComponents.put(UbiGame.class, this);
    GameComponents.put(Gateway.class, gateway);
    GameComponents.put(DeltaTime.class, deltatime);
    
    try {
      List<Class<?>> ims = (List<Class<?>>)settings.get("input_managers");
      if (ims != null) {
        for (Class<?> imc : ims) {
          InputManager im = (InputManager)imc.newInstance();
          GameComponents.put(imc, im);
          inputs.add(im);
        }
      }
      
      List<Class<?>> oms = (List<Class<?>>)settings.get("output_managers");
      if (oms != null) {
        for (Class<?> omc : oms) {
          OutputManager om = (OutputManager)omc.newInstance();
          GameComponents.put(omc, om);
          outputs.add(om);
        }
      }
      
      scenes.add(((GameScene)((Class<?>)settings.get("first_scene")).newInstance()));
    } catch (Exception e) {
      throw new Error(e);
    }
  }
  
  private void close() {
    for (InputManager im : inputs)
      im.destroy();
    for (OutputManager om : outputs)
      om.destroy();
  }
  
  private void updateStack() {
    switch (change_option) {
      case NA:
        break;
        
      case CHANGE:
        scenes.removeLast().destroy();
        scenes.add(scene_change);
        break;
        
      case PUSH:
        scenes.add(scene_change);
        break;
        
      case POP:
        scenes.removeLast().destroy();
        if (scenes.size() > 0)
          scenes.getLast().wakeup(pop_args);
        break;
        
      case QUIT:
        scenes.clear();
        break;
        
      default:
        throw new Error("Invalid value for ChangeOption in UosGame!");
    }
    scene_change = null;
    pop_args = null;
    change_option = ChangeOption.NA;
  }
}
