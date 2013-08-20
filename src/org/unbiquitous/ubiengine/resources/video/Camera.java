package org.unbiquitous.ubiengine.resources.video;

import org.unbiquitous.ubiengine.util.linearalgebra.Vector3;

public class Camera {
  public void update() {
    
  }
  
  protected Vector3 pos = new Vector3();
  
  public Vector3 getPos() {
    return pos;
  }

  public void setPos(Vector3 pos) {
    this.pos = pos;
  }
}
