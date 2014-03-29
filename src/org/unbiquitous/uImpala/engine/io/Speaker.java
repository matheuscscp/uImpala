package org.unbiquitous.uImpala.engine.io;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;

/**
 * Class for sound speaker resource.
 * @author Pimenta
 *
 */
public final class Speaker extends OutputResource {
  protected Speaker() {
    try {
      AL.create();
    } catch (LWJGLException e) {
      throw new Error(e);
    }
  }
  
  protected void update() {
    
  }
  
  public void close() {
    AL.destroy();
  }
  
  public boolean isUpdating() {
    return true;
  }
  
  public float getVolume() {
    return volume;
  }
  
  /**
   * @param volume Must be in the interval [0, 1].
   */
  public void setVolume(float volume) {
    this.volume = volume < 0 ? 0 : (volume > 1 ? 1 : volume);
  }
  
  private float volume = 1.0f;
}
