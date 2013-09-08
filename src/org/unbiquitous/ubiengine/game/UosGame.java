package org.unbiquitous.ubiengine.game;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.unbiquitous.ubiengine.game.state.GameStateArgs;
import org.unbiquitous.ubiengine.game.state.CommonChange;
import org.unbiquitous.ubiengine.game.state.GameState;
import org.unbiquitous.ubiengine.game.state.GameStateList;
import org.unbiquitous.ubiengine.game.state.ChangeState;
import org.unbiquitous.ubiengine.game.state.StackUpChange;
import org.unbiquitous.ubiengine.game.state.UnstackChange;
import org.unbiquitous.ubiengine.resources.input.keyboard.KeyboardManager;
import org.unbiquitous.ubiengine.resources.input.mouse.MouseManager;
import org.unbiquitous.ubiengine.resources.time.DeltaTime;
import org.unbiquitous.ubiengine.resources.video.Screen;
import org.unbiquitous.ubiengine.util.ComponentContainer;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.applicationManager.UosApplication;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyDeploy;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyStart;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyUndeploy;

public abstract class UosGame implements UosApplication {
  private ComponentContainer components = new ComponentContainer();
  private LinkedList<GameState> states;
  private Screen screen;
  private DeltaTime deltatime;
  private KeyboardManager keyboard_manager;
  private MouseManager mouse_manager;
  
  public abstract Map<String, Object> getSettings();

  private void init(Gateway gateway) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
    // Gateway component
    components.put(Gateway.class, gateway);
    
    // DeltaTime component
    deltatime = new DeltaTime();
    components.put(DeltaTime.class, deltatime);

    // GameSettings component
    GameSettings initial_settings = new GameSettings(getSettings());
    components.put(GameSettings.class, initial_settings);

    // Screen component
    screen = new Screen(
        (String) initial_settings.get("window_title"),
        ((Integer) initial_settings.get("window_width")).intValue(),
        ((Integer) initial_settings.get("window_height")).intValue(),
        deltatime
    );
    components.put(Screen.class, screen);

    // KeyboardManager component
    keyboard_manager = new KeyboardManager(components);
    components.put(KeyboardManager.class, keyboard_manager);

    // MouseManager component
    mouse_manager = new MouseManager(components);
    components.put(MouseManager.class, mouse_manager);

    // GameStateList component
    GameStateList gs_list = new GameStateList();
    components.put(GameStateList.class, gs_list);
    states = gs_list.states;
    
    // loading first state
    states.add(
      (GameState) Class.forName((String) initial_settings.get("first_state"))
      .getDeclaredConstructor(ComponentContainer.class, GameStateArgs.class)
      .newInstance(components, null)
    );
  }
  
  private void close() {
    screen.close();
  }
  
  private void run() throws Throwable {
    boolean quit = false;
    
    while (!quit) {
      try {
        deltatime.start();
        input();
        update();
        render();
        deltatime.finish();
        
        if (screen.quit_requested) {
          quit = true;
          
          // notify all game states of quit request
          Iterator<GameState> it = states.iterator();
          while (it.hasNext() && quit)
            quit = it.next().handleQuit();
          
          if (!quit)
            screen.quit_requested = false;
        }
      }
      catch (ChangeState e) {
        handleStateChange(e);
      }
    }
  }
  
  private void handleStateChange(ChangeState cs) throws InvocationTargetException, IllegalAccessException, Throwable {
    if (cs instanceof StackUpChange) {
      states.addFirst(((StackUpChange) cs).newInstance(components));
    }
    else if (cs instanceof UnstackChange) {
      if (states.size() == 1)
        throw new Error("Trying to drop all game states");
      states.removeFirst().delete();
      try {
        states.element().handleUnstack(cs.getArgs());
      }
      catch (ChangeState e) {
        handleStateChange(e);
      }
    }
    else if (cs instanceof CommonChange) {
      states.removeFirst().delete();
      states.addFirst(((CommonChange) cs).newInstance(components));
    }
  }
  
  private void input() throws Throwable {
    keyboard_manager.update();
    mouse_manager.update();
    
    Iterator<GameState> it = states.iterator();
    while (it.hasNext())
      it.next().input();
  }
  
  private void update() throws Throwable {
    Iterator<GameState> it = states.iterator();
    while (it.hasNext())
      it.next().update();
  }
  
  private void render() throws Throwable {
    Iterator<GameState> it = states.iterator();
    while (it.hasNext())
      it.next().render();
    
    screen.update();
  }
  
  public void init(OntologyDeploy ontology, String appId) {
    
  }
  
  public void tearDown(OntologyUndeploy ontology) {
    
  }
  
  public void start(Gateway gateway, OntologyStart ontology) {
    try {
      init(gateway);
      run();
      close();
    }
    catch (Throwable e) {
      org.unbiquitous.ubiengine.util.Logger.log(new Error(e));
    }
    System.exit(0);
  }
  
  public void stop() {
    
  }
}
