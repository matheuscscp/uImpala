package org.unbiquitous.ubiengine.engine.system.io;

/**
 * Class for screens management.
 * @author Pimenta
 *
 */
public class ScreenManager extends OutputManager {
  /**
   * Constructor to add the default screen in availableResources.
   */
  public ScreenManager() {//FIXME
    availableResources.add(new Screen());
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
