package org.unbiquitous.ubiengine.game;

import org.unbiquitous.ubiengine.resources.video.texture.Sprite;
import org.unbiquitous.ubiengine.util.linearalgebra.Vector3;


public class Object {
  public void update() {
    
  }
  
  public void render() {
    if (!hidden)
      sprite.render((int) pos.x(), (int) pos.y(), true);
  }

  protected Sprite sprite = null;
  protected Vector3 pos = new Vector3();
  protected boolean hidden = false;
  
  public Sprite getSprite() {
    return sprite;
  }

  public void setSprite(Sprite sprite) {
    this.sprite = sprite;
  }

  public Vector3 getPos() {
    return pos;
  }

  public void setPos(Vector3 pos) {
    this.pos = pos;
  }

  public boolean isHidden() {
    return hidden;
  }

  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }
}
