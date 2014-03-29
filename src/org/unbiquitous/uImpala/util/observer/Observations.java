package org.unbiquitous.uImpala.util.observer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

/**
 * Class to hold all observations of a subject.
 * @author Pimenta
 *
 */
public final class Observations {
  /**
   * Initializes the observation container for each event passed.
   * @param evs List of events.
   */
  public Observations(Subject sub, int... evs) {
    subject = sub;
    setEvents(evs);
  }
  
  /**
   * Copy constructor.
   * @param other Source.
   */
  public Observations(Subject sub, Observations other) {
    subject = sub;
    events = new Events(other.events);
  }
  
  /**
   * Method to set the event list.
   * @param evs List of events.
   */
  public void setEvents(int... evs) {
    events = new Events();
    for (int event : evs)
      events.put(event, new EventObservations());
  }
  
  /**
   * Method to add events.
   * @param evs Events to be added.
   */
  public void addEvents(int... evs) {
    for (int event : evs)
      events.put(event, new EventObservations());
  }
  
  /**
   * Method to list all events currently registered.
   * @return List of events.
   */
  public int[] listEvents() {
    int[] tmp = new int[events.size()];
    int i = 0;
    for (Entry<Integer, ?> entry : events.entrySet())
      tmp[i++] = entry.getKey();
    return tmp;
  }
  
  /**
   * Method to broadcast an event.
   * @param event_type Event type.
   * @param event Event data.
   */
  public void broadcast(Integer event_type, Event event) {
    EventObservations subj_event = events.get(event_type);
    
    // check event type's existence
    if (subj_event == null)
      throw new Error("Event type " + event_type + " missing");
    
    // add event occurrence to queue
    queue.add(new EventOccurrence(event, subj_event));
    
    // return if this is already broadcasting
    if (broadcasting)
      return;
    
    // process queue
    broadcasting = true;
    while (queue.size() > 0) // process
      queue.poll().notifyObservers(subject);
    broadcasting = false;
  }
  
  /**
   * Add an observation to an event. If eventType is null, the observation
   * will be added to all events.
   * @param eventType Event type.
   * @param obs Observation to be connected.
   */
  public void connect(Integer eventType, Observation obs) {
    if (eventType == null) {
      for (Entry<?, EventObservations> entry : events.entrySet())
        entry.getValue().add(obs);
      return;
    }
    EventObservations event = events.get(eventType);
    if (event == null)
      throw new Error("Event type " + eventType + " missing");
    event.add(obs);
  }
  
  /**
   * Remove an observation from an event. If eventType is null, the observation
   * will be removed from all events.
   * @param eventType Event type.
   * @param obs Observation to be disconnected.
   */
  public void disconnect(Integer eventType, Observation obs) {
    if (eventType == null) {
      for (Entry<?, EventObservations> entry : events.entrySet())
        entry.getValue().remove(obs);
      return;
    }
    EventObservations event = events.get(eventType);
    if (event == null)
      return;
    event.remove(obs);
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  @SuppressWarnings("serial")
  private static final class EventObservations extends HashSet<Observation> {
    private EventObservations() {
      
    }
    private EventObservations(EventObservations other) {
      for (Observation obs : other)
        add(obs);
    }
  }
  
  @SuppressWarnings("serial")
  private static final class Events extends HashMap<Integer, EventObservations> {
    private Events() {
      
    }
    private Events(Events other) {
      for (Entry<Integer, EventObservations> entry : other.entrySet())
        put(entry.getKey(), new EventObservations(entry.getValue()));
    }
  }
  
  private static final class EventOccurrence {
    private Event data;
    private EventObservations observations;
    private EventOccurrence(Event d, EventObservations obs) {
      data = d != null ? d : new Event();
      observations = new EventObservations(obs);
    }
    private void notifyObservers(Subject subject) {
      for (Observation obs : observations) {
        obs.notifyEvent(data, subject);
        if (data.stop)
          return;
      }
    }
  }
  
  private Events events;
  private Queue<EventOccurrence> queue = new LinkedList<EventOccurrence>();
  private boolean broadcasting = false;
  private Subject subject;
}
