package org.unbiquitous.ubiengine.engine.system.io;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Base class for input resources.
 * @author Pimenta
 *
 */
public abstract class InputResource extends IOResource {
  /**
   * Event queue to be polled in <code>update()</code>.
   */
  protected Queue<InputEvent> events = new LinkedList<InputEvent>();
}
