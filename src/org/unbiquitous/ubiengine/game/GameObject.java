package org.unbiquitous.ubiengine.game;

import org.unbiquitous.ubiengine.util.mathematics.linearalgebra.Vector3;

public abstract class GameObject {
  public abstract void input();
  public abstract void update();
  public abstract void render();
  
  protected Vector3 pos = new Vector3();
  
  public Vector3 getPos() {
    return pos;
  }

  public void setPos(Vector3 pos) {
    this.pos = pos;
  }
}
