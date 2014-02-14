package org.unbiquitous.ubiengine.engine;

import java.util.LinkedList;
import java.util.List;

public abstract class ContainerGameState extends GameState {
  private List<GameObject> objects = new LinkedList<GameObject>();
  
  protected void add(GameObject o) {
    objects.add(o.setComponents(components));
  }
  
  public void update() {
    for (GameObject o : objects)
      o.update();
  }
  
  public void render() {
    for (GameObject o : objects)
      o.update();
  }
  
  public void wakeup(Object... args) {
    for (GameObject o : objects)
      o.wakeup(args);
  }
  
  public void close() {
    for (GameObject o : objects)
      o.close();
  }
}
