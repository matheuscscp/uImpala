package org.unbiquitous.ubiengine.util.observer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

/**
 * Class to implement observation resources for subject objects.
 * 
 * @author Matheus
 */
public final class SubjectDevice {
  private static final class Observation {
    private Object observer;
    private Method handler;
    
    private Observation(Object o, Method h) {
      observer = o;
      handler = h;
      if (handler != null)
        handler.setAccessible(true);
    }
    
    private void notifyEvent(Event event) {
      try {
        handler.invoke(observer, event);
      } catch (Exception e) {
        throw new Error(e);
      }
    }
    
    public boolean equals(Object obj) {
      // the observation is just a function?
      // then the function uniquely identifies the observation
      // else the object uniquely identifies the observation
      Observation other = (Observation) obj;
      return ((observer == null && other.observer == null && handler.equals(other.handler)) ||
              (observer != null && observer == other.observer));
    }
    
    public int hashCode() {
      // the observation is just a function?
      // then the function uniquely identifies the observation
      // else the object uniquely identifies the observation
      return (observer == null ? handler.hashCode() : observer.hashCode());
    }
  }
  
  @SuppressWarnings("serial")
  private static final class EventObservations extends HashSet<Observation> {
    private boolean broadcasting = false;
    
    private EventObservations() {
      
    }
    
    private EventObservations(EventObservations other) {
      for (Observation obs : other)
        add(obs);
    }
  }
  
  @SuppressWarnings("serial")
  private static final class Events extends HashMap<String, EventObservations> {
    private Events() {
      
    }
    
    private Events(Events other) {
      for (Entry<String, EventObservations> entry : other.entrySet())
        put(entry.getKey(), new EventObservations(entry.getValue()));
    }
  }
  
  private Events events;
  
  // ===========================================================================
  // SubjectDevice methods
  // ===========================================================================
  
  /**
   * Initializes the Observation container for each event passed.
   * @param evs List of events the device will have.
   */
  public SubjectDevice(String... evs) {
    setEvents(evs);
  }
  
  /**
   * Copy constructor.
   * @param other Source.
   */
  public SubjectDevice(SubjectDevice other) {
    events = new Events(other.events);
  }
  
  /**
   * Method to add new events to this device.
   * @param evs List of events to add.
   */
  public void setEvents(String... evs) {
    events = new Events();
    for (String event : evs) {
      if (event != null)
        events.put(event, new EventObservations());
    }
  }
  
  /**
   * Lists the events of a SubjectDevice with a String List.
   * @return List of String events.
   */
  public List<String> listEvents() {
    List<String> evs = new ArrayList<String>();
    for (Entry<String, ?> entry : events.entrySet())
      evs.add(entry.getKey());
    return evs;
  }
  
  /**
   * The subject object owner of this device must call this method to broadcast its events.
   * This method iterates over all the observations of the event type, calling the handler method.
   * 
   * @param event_type Event to broadcast.
   * @param event Event data.
   */
  public void broadcast(String event_type, Event event) {
    EventObservations subj_event = events.get(event_type);
    if (subj_event == null)
      throw new Error("Event type \"" + event_type + "\" missing");
    if (subj_event.broadcasting)
      throw new Error("Trying to recursively broadcast an event of type: \"" + event_type + "\"");
    if (event == null)
      event = new Event();
    subj_event.broadcasting = true;
    for (Observation obs : subj_event) {
      obs.notifyEvent(event);
      if (event.stop)
        break;
    }
    subj_event.broadcasting = false;
  }
  
  /**
   * Event without data. Calls broadcast(event_type, null).
   * 
   * @param event_type Event to broadcast.
   */
  public void broadcast(String event_type) {
    broadcast(event_type, null);
  }
  
  /**
   * Method to create an observation for an observer method.
   * 
   * @param event_type String that describes the event type.
   * @param handler Observer method.
   */
  public void connect(String event_type, Method handler) {
    if (handler == null)
      throw new Error("Cannot connect null handler");
    EventObservations event = events.get(event_type);
    if (event == null)
      throw new Error("Event type \"" + event_type + "\" missing");
    event.add(new Observation(null, handler));
  }
  
  /**
   * Method to create an observation for an observer object.
   * 
   * @param event_type String to describe the event type.
   * @param observer Observer object.
   * @param handler Handler method for notification.
   */
  public void connect(String event_type, Object observer, Method handler) {
    if (observer == null)
      throw new Error("Cannot connect null observer");
    if (handler == null)
      throw new Error("Cannot connect null handler");
    EventObservations event = events.get(event_type);
    if (event == null)
      throw new Error("Event type \"" + event_type + "\" missing");
    event.add(new Observation(observer, handler));
  }
  
  /**
   * Destroys all observations of the observer method.
   * 
   * @param handler Observer method.
   */
  public void disconnect(Method handler) {
    for (Entry<?, EventObservations> entry : events.entrySet())
      entry.getValue().remove(new Observation(null, handler));
  }
  
  /**
   * Destroy the observation of the observer method for the given event type.
   * 
   * @param event_type String to describe the event type.
   * @param handler Observer method.
   */
  public void disconnect(String event_type, Method handler) {
    EventObservations event = events.get(event_type);
    if (event == null)
      return;
    event.remove(new Observation(null, handler));
  }
  
  /**
   * Destroys all observations of the observer object.
   * 
   * @param observer Observer object.
   */
  public void disconnect(Object observer) {
    for (Entry<?, EventObservations> entry : events.entrySet())
      entry.getValue().remove(new Observation(observer, null));
  }
  
  /**
   * Destroy the observation of the observer object for the given event type.
   * 
   * @param event_type String to describe the event type.
   * @param observer Observer object.
   */
  public void disconnect(String event_type, Object observer) {
    EventObservations event = events.get(event_type);
    if (event == null)
      return;
    event.remove(new Observation(observer, null));
  }
}
