package org.unbiquitous.uImpala.engine.io;

/**
 * Class for sound speaker resource.
 * @author Pimenta
 *
 */
public abstract class Speaker extends OutputResource {
  protected static interface Factory {
    public Speaker create();
  }
  
  protected static Factory factory = null;
  
  /**
   * Constructor.
   * @return Speaker created.
   */
  public static synchronized Speaker create() {
    return factory.create();
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
