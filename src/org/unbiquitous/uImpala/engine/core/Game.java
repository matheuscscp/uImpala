package org.unbiquitous.uImpala.engine.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListResourceBundle;

import org.unbiquitous.uImpala.engine.io.InputManager;
import org.unbiquitous.uImpala.engine.io.OutputManager;
import org.unbiquitous.uImpala.engine.time.DeltaTime;
import org.unbiquitous.uImpala.util.Logger;
import org.unbiquitous.uos.core.InitialProperties;
import org.unbiquitous.uos.core.UOS;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.applicationManager.UosApplication;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyDeploy;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyStart;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyUndeploy;
import org.unbiquitous.uos.network.socket.connectionManager.TCPConnectionManager;
import org.unbiquitous.uos.network.socket.radar.MulticastRadar;

/**
 * The game class. Extend it only to implement getSettings().
 * @author Pimenta
 *
 */
public abstract class Game implements UosApplication {
  /**
   * Use this method in main() to start the game.
   * @param game Class{@literal <}?{@literal >} that extends UosGame.
   * @param args Command line arguments.
   */
  protected static void run(final String className, final GameSettings settings) {
    new UOS().init(new ListResourceBundle() {
      protected Object[][] getContents() {
        return new Object[][] {
          {"ubiquitos.connectionManager", TCPConnectionManager.class.getName()},
          {"ubiquitos.radar", MulticastRadar.class.getName()},
          {"ubiquitos.eth.tcp.port", "14984"},
          {"ubiquitos.eth.tcp.passivePortRange", "14985-15000"},
          {"ubiquitos.application.deploylist", className},
          {"uImpala.gameSettings", settings}
        };
      }
    });
  }
  
  /**
   * Method to initialize an engine implementation.
   */
  protected abstract void initImpl();
  
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
        
        // update input
        for (InputManager im : inputs)
          im.update();
        
        // update scenes
        while (deltatime.dtReachedLimit()) {
          for (GameScene gs : scenes) {
            if (!gs.frozen)
              gs.update();
          }
        }
        deltatime.accumulate();
        
        // render scenes
        for (GameScene gs : scenes) {
          if (!gs.frozen || (gs.frozen && gs.visible))
            gs.render();
        }
        
        // update output
        for (OutputManager om : outputs)
          om.update();
        
        updateStack();
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
  public void init(OntologyDeploy knowledgeBase, InitialProperties properties, String appId) {
    settings = (GameSettings)properties.get("uImpala.gameSettings");
  }
  
  /**
   * uOS's private use.
   */
  public void tearDown(OntologyUndeploy ontology) {
    
  }
  
  @SuppressWarnings("unchecked")
  private void init(Gateway gateway) {
    initImpl();
    
    validateSettings();
    GameComponents.put(GameSettings.class, settings);
    GameComponents.put(Game.class, this);
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
  
  private void validateSettings() {
    if (settings == null)
      throw new Error("GameSettings not defined!");
    if (settings.get("first_scene") == null)
      throw new Error("First game scene not defined!");
    if (settings.get("output_managers") == null)
      throw new Error("Cannot start game with no output managers!");
    if (settings.get("root_path") == null)
      settings.put("root_path", ".");
  }
  
  private void close() {
    for (InputManager im : inputs)
      im.close();
    for (OutputManager om : outputs)
      om.close();
  }
  
  private void updateStack() {
    GameScene tmp;
    switch (change_option) {
      case NA:
        break;
        
      case CHANGE:
        tmp = scenes.removeLast();
        tmp.assets.destroy();
        tmp.destroy();
        scenes.add(scene_change);
        break;
        
      case PUSH:
        scenes.add(scene_change);
        break;
        
      case POP:
        tmp = scenes.removeLast();
        tmp.assets.destroy();
        tmp.destroy();
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
