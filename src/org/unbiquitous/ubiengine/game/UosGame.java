//TODO implementar o texto de fps

package org.unbiquitous.ubiengine.game;

import java.lang.Object;
import java.util.Map;

import org.unbiquitous.ubiengine.game.state.QuitException;
import org.unbiquitous.ubiengine.game.state.State;
import org.unbiquitous.ubiengine.resources.input.InputManager;
import org.unbiquitous.ubiengine.resources.input.KeyboardReceptionDriverManager;
import org.unbiquitous.ubiengine.resources.network.NetworkManager;
import org.unbiquitous.ubiengine.resources.time.DeltaTime;
import org.unbiquitous.ubiengine.resources.video.Screen;
import org.unbiquitous.ubiengine.util.SingletonContainer;
import org.unbiquitous.ubiengine.util.observer.Stack;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.applicationManager.UosApplication;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyDeploy;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyStart;
import org.unbiquitous.uos.core.ontologyEngine.api.OntologyUndeploy;


public abstract class UosGame implements UosApplication {
  private SingletonContainer singletons = new SingletonContainer();
  private State state;
  
  public abstract Map<String, Object> getSettings();
  
  public void init(OntologyDeploy ontology, String appId) {
    // DeltaTime singleton
    DeltaTime deltatime = new DeltaTime();
    singletons.put(DeltaTime.class, deltatime);

    // Settings singleton
    Settings initial_settings = new Settings(getSettings());
    singletons.put(Settings.class, initial_settings);

    // Screen singleton
    singletons.put(Screen.class, new Screen(
        (String) initial_settings.get("window_title"),
        ((Integer) initial_settings.get("window_width")).intValue(),
        ((Integer) initial_settings.get("window_height")).intValue(),
        deltatime
    ));

    // observer.Stack singleton
    Stack observer_stack = new Stack();
    singletons.put(Stack.class, observer_stack);

    // InputManager singleton
    singletons.put(InputManager.class, new InputManager(singletons));

    // NetworkManager singleton
    singletons.put(NetworkManager.class, new NetworkManager());
    
    // loading first state
    try {
      state = (State) Class.forName((String) initial_settings.get("first_state"))
      .getDeclaredConstructor(SingletonContainer.class)
      .newInstance(singletons);
    }
    catch (Throwable e) {
      throw new Error(e);
    }
  }
  
  public void tearDown(OntologyUndeploy ontology) {
    
  }
  
  public void start(Gateway gateway, OntologyStart ontology) {
    try {
      // Gateway singleton
      singletons.put(Gateway.class, gateway);
      
      DeltaTime deltatime = singletons.get(DeltaTime.class);
      NetworkManager network_manager = singletons.get(NetworkManager.class);
      
      // Network initialization with Gateway singleton
      network_manager.setGateway(gateway);
      network_manager.setApplicationName((String) singletons.get(Settings.class).get("window_title"));
      KeyboardReceptionDriverManager.init(singletons.get(InputManager.class), gateway);
      
      while (true) {
        deltatime.start();
        network_manager.update();
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
  
  public void stop() {
    
  }
  
  private void input() throws Throwable {
    singletons.get(InputManager.class).update();
    state.input();
  }
  
  private void update() throws Throwable {
    state.update();
  }
  
  private void render() throws Throwable {
    state.render();
    singletons.get(Screen.class).update();
  }
}
