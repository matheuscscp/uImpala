package org.unbiquitous.ubiengine.engine.system.io;

/**
 * Class for mouses management.
 * @author Pimenta
 *
 */
public final class MouseManager extends InputManager {
  /**
   * Constructor to add the default mouse in availableResources.
   */
  public MouseManager() {//FIXME
    availableResources.add(new MouseSource());
  }
  
  protected void updateLists() {
    // TODO Auto-generated method stub
  }
  
  protected void start(IOResource rsc) {
    // TODO Auto-generated method stub
  }
  
  protected void stop(IOResource rsc) {
    // TODO Auto-generated method stub
  }
}
