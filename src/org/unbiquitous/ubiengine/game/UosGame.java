package org.unbiquitous.ubiengine.game;

import java.util.Map;

import org.unbiquitous.ubiengine.game.state.GameState;
import org.unbiquitous.ubiengine.game.state.QuitException;
import org.unbiquitous.ubiengine.resources.input.keyboard.KeyboardManager;
import org.unbiquitous.ubiengine.resources.input.mouse.MouseManager;
import org.unbiquitous.ubiengine.resources.time.DeltaTime;
import org.unbiquitous.ubiengine.resources.video.Screen;
import org.unbiquitous.ubiengine.util.ComponentContainer;
import org.unbiquitous.ubiengine.util.observer.ObservationStack;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.applicationManager.UosApplication;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyDeploy;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyStart;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyUndeploy;

public abstract class UosGame implements UosApplication {
  private ComponentContainer components = new ComponentContainer();
  private GameState state;
  
  public abstract Map<String, Object> getSettings();

  private void init(Gateway gateway) {
    // Gateway component
    components.put(Gateway.class, gateway);
    
    // DeltaTime component
    DeltaTime deltatime = new DeltaTime();
    components.put(DeltaTime.class, deltatime);

    // Settings component
    Settings initial_settings = new Settings(getSettings());
    components.put(Settings.class, initial_settings);

    // Screen component
    components.put(Screen.class, new Screen(
        (String) initial_settings.get("window_title"),
        ((Integer) initial_settings.get("window_width")).intValue(),
        ((Integer) initial_settings.get("window_height")).intValue(),
        deltatime
    ));

    // ObservationStack component
    ObservationStack observer_stack = new ObservationStack();
    components.put(ObservationStack.class, observer_stack);

    // KeyboardManager component
    components.put(KeyboardManager.class, new KeyboardManager(components));

    // MouseManager component
    components.put(MouseManager.class, new MouseManager(components));

    // loading first state
    try {
      state = (GameState) Class.forName((String) initial_settings.get("first_state"))
      .getDeclaredConstructor(ComponentContainer.class)
      .newInstance(components);
    }
    catch (Throwable e) {
      throw new Error(e);
    }
  }
  
  private void run() {
    try {
      DeltaTime deltatime = components.get(DeltaTime.class);
      
      while (true) {
        deltatime.start();
        input();
        update();
        render();
        deltatime.finish();
      }
    }
    catch (QuitException e) {
      
    }
    catch (Throwable e) {
      
    }
  }
  
  private void input() throws Throwable {
    components.get(KeyboardManager.class).update();
    components.get(MouseManager.class).update();
    state.input();
  }
  
  private void update() throws Throwable {
    state.update();
  }
  
  private void render() throws Throwable {
    state.render();
    components.get(Screen.class).update();
  }
  
  public void init(OntologyDeploy ontology, String appId) {
    
  }
  
  public void tearDown(OntologyUndeploy ontology) {
    
  }
  
  public void start(Gateway gateway, OntologyStart ontology) {
    init(gateway);
    run();
  }
  
  public void stop() {
    
  }
}
