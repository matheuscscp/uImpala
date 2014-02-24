package org.unbiquitous.ubiengine.engine.core;

import java.util.HashMap;

/**
 * Just a "typedef" for
 * <code>HashMap{@literal <}String, Object{@literal >}</code>.
 * @author Pimenta
 *
 */
@SuppressWarnings("serial")
public class GameSettings extends HashMap<String, Object> {
  /**
   * Engine's private use.
   */
  protected GameSettings validate() {
    if (get("root_path") == null)
      put("root_path", ".");
    if (get("first_state") == null)
      throw new Error("First game state not defined!");
    if (get("input_managers") == null)
      throw new Error("Cannot start game with no input managers!");
    if (get("output_managers") == null)
      throw new Error("Cannot start game with no output managers!");
    return this;
  }
}
