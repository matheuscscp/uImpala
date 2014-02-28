package org.unbiquitous.ubiengine.engine.system.io;

/**
 * Class for sound speakers management.
 * @author Pimenta
 *
 */
public final class SpeakerManager extends OutputManager {
  /**
   * Constructor to add the default speaker in availableResources.
   */
  public SpeakerManager() {//FIXME
    availableResources.add(new Speaker());
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
