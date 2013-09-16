package org.unbiquitous.ubiengine.util.observer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class to implement observation resources for subject objects.
 * 
 * @author Matheus
 */
public final class SubjectDevice {
  // An observation holds data about the observer object, or method,
  // and makes the notification.
  private static final class Observation {
    private Object observer;
    private Method handler;
    
    public Observation(Object observer, Method handler) {
      this.observer = observer;
      this.handler = handler;
      if (handler != null)
        this.handler.setAccessible(true);
    }
    
    protected void notifyEvent(Event event) throws InvocationTargetException {
      try {
        handler.invoke(observer, event);
      } catch (IllegalAccessException e) {
      } catch (IllegalArgumentException e) {
        throw new Error("Observer callback \"" + handler.toString() + "\" argument exception");
      }
    }
    
    public boolean equals(Object obj) {
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
  
  // Container of observations for an Event type.
  private static final class EventObservations {
    public HashSet<Observation> observers = new HashSet<Observation>();
    public boolean broadcasting = false;
    
    public EventObservations clone() {
      EventObservations other = new EventObservations();
      
      Iterator<Observation> it = observers.iterator();
      while (it.hasNext())
        other.observers.add(it.next());
      
      return other;
    }
  }
  
  // Container for EventObservations by Event types.
  private HashMap<String, EventObservations> events = new HashMap<String, EventObservations>();
  
  // ===========================================================================
  // SubjectDevice methods
  // ===========================================================================
  
  /**
   * @param events String array of all event types that this subject will broadcast.
   */
  public SubjectDevice(String... events) {
    addEvents(events);
  }
  
  /**
   * Method to add new events to this device.
   * 
   * @param events String array with all new events.
   */
  public void addEvents(String... events) {
    if (events == null)
      return;
    
    for (String event : events) {
      if (event != null)
        this.events.put(event, new EventObservations());
    }
  }
  
  /** Creates a new SubjectDevice by cloning all observations. */
  public SubjectDevice clone() {
    SubjectDevice other = new SubjectDevice();
    
    Iterator<?> it = events.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<?, ?> entry = (Map.Entry<?, ?>) it.next();
      other.events.put((String) entry.getKey(), ((EventObservations) entry.getValue()).clone());
    }
    
    return other;
  }
  
  /** Creates an ArrayList with all events this device broadcasts. */
  public List<String> listEvents() {
    List<String> events = new ArrayList<String>();
    
    Iterator<?> it = this.events.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<?, ?> entry = (Map.Entry<?, ?>) it.next();
      events.add((String) entry.getKey());
    }
    
    return events;
  }
  
  /**
   * The subject object owner of this device must call this method to broadcast its events.
   * This method iterates over all the observations of the event type, calling the handler method.
   * 
   * @param event_type Event to broadcast.
   * @param event Event data.
   * @throws Exception
   */
  public void broadcast(String event_type, Event event) throws Exception {
    EventObservations subj_event = events.get(event_type);
    
    if (subj_event == null)
      throw new Error("Event type \"" + event_type + "\" missing");
    
    if (subj_event.broadcasting)
      throw new Error("Trying to recursively broadcast an event of type: \"" + event_type + "\"");
    
    if (event == null)
      event = new Event();
    
    subj_event.broadcasting = true;
    try {
      Iterator<Observation> it = subj_event.observers.iterator();
      while (it.hasNext() && !event.stop)
        it.next().notifyEvent(event);
    }
    catch (InvocationTargetException e) {
      subj_event.broadcasting = false;
      throw (Exception) e.getCause();
    }
    subj_event.broadcasting = false;
  }
  
  /**
   * Event without data. Calls broadcast(event_type, null).
   * 
   * @param event_type Event to broadcast.
   * @throws Exception
   */
  public void broadcast(String event_type) throws Exception {
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
    event.observers.add(new Observation(null, handler));
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
    event.observers.add(new Observation(observer, handler));
  }
  
  /**
   * Destroys all observations of the observer method.
   * 
   * @param handler Observer method.
   */
  public void disconnect(Method handler) {
    Iterator<?> it = events.entrySet().iterator();
    while (it.hasNext())
      ((EventObservations) ((Map.Entry<?, ?>) it.next()).getValue()).observers.remove(new Observation(null, handler));
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
    event.observers.remove(new Observation(null, handler));
  }
  
  /**
   * Destroys all observations of the observer object.
   * 
   * @param observer Observer object.
   */
  public void disconnect(Object observer) {
    Iterator<?> it = events.entrySet().iterator();
    while (it.hasNext())
      ((EventObservations) ((Map.Entry<?, ?>) it.next()).getValue()).observers.remove(new Observation(observer, null));
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
    event.observers.remove(new Observation(observer, null));
  }
}
