package org.unbiquitous.ubiengine.engine.system.time;


/**
 * Class to handle frame rate. The initial frame rate is 30 FPS.
 * @author Pimenta
 *
 */
public final class DeltaTime {
  /**
   * Can be used as frame's unique id.
   * @return Time in milliseconds.
   */
  public long frameID() {
    return before;
  }
  
  /**
   * The fixed value of frame rate.
   * @return Frequency in frames per second.
   */
  public float getFPS() {
    return idealFPS;
  }
  
  /**
   * The value of frame rate achieved in the last frame.
   * @return Frequency in frames per second.
   */
  public float getRealFPS() {
    if (realDT == 0)
      return 0;
    return 1000.0f/realDT;
  }
  
  /**
   * The desired value of frame's duration.
   * @return Time in seconds.
   */
  public float getDT() {
    if (realDT == 0)
      return 0;
    return 1/idealFPS;
  }
  
  /**
   * The value of last frame's duration.
   * @return Time in seconds.
   */
  public float getRealDT() {
    return realDT/1000.0f;
  }
  
  /**
   * Sets the fixed value of frame rate.
   * @param FPS Frequency in frames per second.
   */
  public void setFPS(float FPS) {
    if (FPS > 0)
      idealFPS = FPS;
  }
  
  /**
   * Sets the desired value of frame's duration.
   * @param DT Time in seconds.
   */
  public void setDT(float DT) {
    if (DT > 0)
      idealFPS = 1/DT;
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  private long before;         // unit: millisecond
  private float idealFPS = 30; // unit: frame/second
  private long realDT = 0;     // unit: millisecond
  
  /**
   * Engine's private use.
   */
  public void update() {
    long now = Time.get();
    realDT = now - before;
    before = now;
  }
  
  /**
   * Engine's private use.
   */
  public void sync() {
    long diff = 1000/((long)idealFPS) - (Time.get() - before);
    if (diff <= 0)
      return;
    try {
      Thread.sleep(diff);
    } catch (InterruptedException e) {
    }
  }
}
