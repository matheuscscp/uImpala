package org.unbiquitous.ubiengine.engine.time;

/**
 * Class to handle frame rate.
 * @author Pimenta
 *
 */
public final class DeltaTime {
  /**
   * Can be used as frame's unique id.
   * @return Time in milliseconds.
   */
  public long getBegin() {
    return begin;
  }
  
  /**
   * The fixed value of frame rate.
   * @return Frequency in frames per second.
   */
  public float getIdealFPS() {
    return ideal_FPS;
  }
  
  /**
   * The value of frame rate achieved in the last frame.
   * @return Frequency in frames per second.
   */
  public float getRealFPS() {
    if (real_DT == 0)
      return 0;
    return 1000.0f/real_DT;
  }
  
  /**
   * The desired value of frame's duration.
   * @return Time in seconds.
   */
  public float getIdealDT() {
    if (real_DT == 0)
      return 0;
    return 1/ideal_FPS;
  }
  
  /**
   * The value of last frame's duration.
   * @return Time in seconds.
   */
  public float getRealDT() {
    return real_DT/1000.0f;
  }
  
  /**
   * Sets the fixed value of frame rate.
   * @param FPS Frequency in frames per second.
   */
  public void setIdealFPS(float FPS) {
    if (FPS > 0)
      ideal_FPS = FPS;
  }
  
  /**
   * Sets the desired value of frame's duration.
   * @param DT Time in seconds.
   */
  public void setIdealDT(float DT) {
    if (DT > 0)
      ideal_FPS = 1/DT;
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  private long begin;            // unit: millisecond
  private float ideal_FPS = 30;  // unit: frame/second
  private long real_DT = 0;      // unit: millisecond
  
  /**
   * Engine's private use.
   */
  public void start() {
    begin = System.currentTimeMillis();
  }
  
  /**
   * Engine's private use.
   */
  public void finish() {
    real_DT = System.currentTimeMillis() - begin;
    long diff = 1000/((long) ideal_FPS) - real_DT;
    if (diff <= 0) // frame bigger than desired
      return;
    try {
      Thread.sleep(diff);
    }
    catch (InterruptedException e) {
    }
    real_DT = System.currentTimeMillis() - begin;
  }
}
