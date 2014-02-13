package org.unbiquitous.ubiengine.engine;

import org.unbiquitous.ubiengine.engine.time.DeltaTime;
import org.unbiquitous.ubiengine.util.mathematics.linearalgebra.Vector3;

/**
 * Use this class to implement game objects (position and time).
 * @author Pimenta
 *
 */
public class GameObject {
  /**
   * Game object position.
   */
  protected Vector3 pos = new Vector3();
  
  /**
   * DeltaTime component for physics simulations.
   */
  protected DeltaTime deltatime;
  
  /**
   * Assignment constructor.
   * @param deltatime DeltaTime component reference.
   */
  public GameObject(DeltaTime deltatime) {
    this.deltatime = deltatime;
  }
  
  /**
   * Position access method.
   * @return Vector3 with the position.
   */
  public Vector3 getPos() {
    return pos;
  }
  
  /**
   * Position modifier method.
   * @param pos Vector3 with the position.
   */
  public void setPos(Vector3 pos) {
    this.pos = pos;
  }
}
