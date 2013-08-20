package org.unbiquitous.ubiengine.util.observer;

/**
 * Event base class.
 * 
 * @author Matheus
 */
public class Event {
  private String type;
  
  /** Assign true to stop propagation. */
  public boolean stop = false;
  
  /** @param type String to describe the event type. */
  public Event(String type) {
    this.type = type;
  }
  
  /** Returns the string that describes the event type. */
  public String getType() {
    return type;
  }
}
