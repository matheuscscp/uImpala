package org.unbiquitous.ubiengine.util.observer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
    private Integer level;
    private Object observer;
    private Method handler;
    
    public Observation(Stack stack, Object observer, Method handler) {
      if (stack != null)
        this.level = stack.getLevel();
      this.observer = observer;
      this.handler = handler;
      this.handler.setAccessible(true);
    }
    
    protected void notifyEvent(Stack stack, org.unbiquitous.ubiengine.util.observer.Event event) {
      if (stack != null) {
        if (level != stack.getLevel())
          return;
      }
      
      try {
        handler.invoke(observer, event);
      } catch (IllegalAccessException e) {
      } catch (IllegalArgumentException e) {
      } catch (InvocationTargetException e) {
      }
    }
    
    public boolean equals(Object obj) {
      Observation other = (Observation) obj;
      return ((level == other.level) &&
              ((observer == null && other.observer == null && handler.equals(other.handler)) ||
               (observer != null && observer == other.observer)));
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
  
  // Stack reference
  private Stack stack;
  
  // ===========================================================================
  // SubjectDevice methods
  // ===========================================================================
  
  /**
   * @param stack Stack reference to block stacked observations. Pass null to ignore observer stack.
   * @param events String array of all event types that this subject will broadcast.
   */
  public SubjectDevice(Stack stack, String... events) {
    if (events.length == 0)
      throw new Error("Trying to create subject without events");
    
    this.stack = stack;
    
    for (String event : events) {
      if (event != null)
        this.events.put(event, new EventObservations());
    }
  }
  
  // Constructor to clone subject devices.
  private SubjectDevice(Stack stack) {
    this.stack = stack;
  }
  
  /** Creates a new Subject by cloning all observations. */
  public SubjectDevice clone() {
    SubjectDevice other = new SubjectDevice(stack);
    
    Iterator<?> it = events.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<?, ?> entry = (Map.Entry<?, ?>) it.next();
      other.events.put((String) entry.getKey(), ((EventObservations) entry.getValue()).clone());
    }
    
    return other;
  }
  
  /**
   * The subject object owner of this device must call this method to broadcast its events.
   * This method iterates over all the observations of the event type, calling the handler method.
   * 
   * @param event Event to broadcast.
   * @throws Throwable
   */
  public void broadcast(org.unbiquitous.ubiengine.util.observer.Event event) throws Throwable {
    EventObservations subj_event = events.get(event.getType());
    
    if (subj_event.broadcasting)
      throw new Error("Trying to recursively broadcast an event of type: " + event.getType());
    
    subj_event.broadcasting = true;
    try {
      Iterator<Observation> it = subj_event.observers.iterator();
      while (it.hasNext() && !event.stop)
        it.next().notifyEvent(stack, event);
    }
    catch (Throwable e) {
      subj_event.broadcasting = false;
      throw e;
    }
    subj_event.broadcasting = false;
  }
  
  /**
   * Method to create an observation for an observer method.
   * 
   * @param event_type String that describes the event type.
   * @param handler Observer method.
   * @throws MissingEventType
   */
  public void connect(String event_type, Method handler) throws MissingEventType {
    if (handler == null)
      return;
    
    EventObservations event = events.get(event_type);
    if (event == null)
      throw new MissingEventType();
    event.observers.add(new Observation(stack, null, handler));
  }
  
  /**
   * Method to create an observation for an observer object.
   * 
   * @param event_type String to describe the event type.
   * @param observer Observer object.
   * @param handler Handler method for notification.
   * @throws MissingEventType
   */
  public void connect(String event_type, Object observer, Method handler) throws MissingEventType {
    if (observer == null || handler == null)
      return;
    
    EventObservations event = events.get(event_type);
    if (event == null)
      throw new MissingEventType();
    event.observers.add(new Observation(stack, observer, handler));
  }
  
  /**
   * Destroys all observations of the observer method in the current stack level.
   * 
   * @param handler Observer method.
   */
  public void disconnect(Method handler) {
    if (handler == null)
      return;
    
    Iterator<?> it = events.entrySet().iterator();
    while (it.hasNext())
      ((EventObservations) ((Map.Entry<?, ?>) it.next()).getValue()).observers.remove(new Observation(stack, null, handler));
  }
  
  /**
   * Destroy the observation of the observer method for the given event type in the current stack level.
   * 
   * @param event_type String to describe the event type.
   * @param handler Observer method.
   * @throws MissingEventType
   */
  public void disconnect(String event_type, Method handler) throws MissingEventType {
    if (event_type == null || handler == null)
      return;
    
    EventObservations event = events.get(event_type);
    if (event == null)
      throw new MissingEventType();
    event.observers.remove(new Observation(stack, null, handler));
  }
  
  /**
   * Destroys all observations of the observer object in the current stack level.
   * 
   * @param observer Observer object.
   */
  public void disconnect(Object observer) {
    if (observer == null)
      return;
    
    Iterator<?> it = events.entrySet().iterator();
    while (it.hasNext())
      ((EventObservations) ((Map.Entry<?, ?>) it.next()).getValue()).observers.remove(new Observation(stack, observer, null));
  }
  
  /**
   * Destroy the observation of the observer object for the given event type in the current stack level.
   * 
   * @param event_type String to describe the event type.
   * @param observer Observer object.
   * @throws MissingEventType
   */
  public void disconnect(String event_type, Object observer) throws MissingEventType {
    if (event_type == null || observer == null)
      return;
    
    EventObservations event = events.get(event_type);
    if (event == null)
      throw new MissingEventType();
    event.observers.remove(new Observation(stack, observer, null));
  }
}
