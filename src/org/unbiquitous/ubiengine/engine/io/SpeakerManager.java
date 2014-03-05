package org.unbiquitous.ubiengine.engine.io;

/**
 * Class for sound speakers management.
 * @author Pimenta
 *
 */
public final class SpeakerManager extends OutputManager {
  /**
   * Constructor to add the default speaker in availableResources.
   */
  public SpeakerManager() {
    availableResources.add(new Speaker());
  }
  
  protected void updateLists() {
    
  }
  
  protected void start(IOResource rsc) {
    
  }
  
  protected void stop(IOResource rsc) {
    
  }
}
