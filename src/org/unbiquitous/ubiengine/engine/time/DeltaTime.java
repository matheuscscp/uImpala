package org.unbiquitous.ubiengine.engine.time;

public final class DeltaTime {
  /** Time in milliseconds. */
  public long getBegin() {             // unit: millisecond
    return begin;
  }

  public float getIdealFPS() {         // unit: frame/second
    return ideal_FPS;
  }

  public float getRealFPS() {          // unit: frame/second
    if (real_DT == 0)
      return 0;
    return 1000.0f/real_DT;
  }

  /** Time in seconds. */
  public float getIdealDT() {          // unit: second
    if (real_DT == 0)
      return 0;
    return 1/ideal_FPS;
  }

  /** Time in seconds. */
  public float getRealDT() {           // unit: second
    return real_DT/1000.0f;
  }
  
  public void setIdealFPS(float FPS) { // unit: frame/second
    if (FPS > 0)
      ideal_FPS = FPS;
  }

  /** @param DT Time in seconds. */
  public void setIdealDT(float DT) {   // unit: second
    if (DT > 0)
      ideal_FPS = 1/DT;
  }
  // ===========================================================================
  // nothings else matters from here to below
  // ===========================================================================
  private long begin;            // unit: millisecond
  private float ideal_FPS = 30;  // unit: frame/second
  private long real_DT = 0;      // unit: millisecond
  
  public void start() {
    begin = System.currentTimeMillis();
  }
  
  public void finish() {
    real_DT = System.currentTimeMillis() - begin;
    
    // delay
    long diff = 1000/((long) ideal_FPS) - real_DT;
    if (diff > 0) {
      try {
        Thread.sleep(diff);
      }
      catch (InterruptedException e) {
      }
      real_DT = System.currentTimeMillis() - begin;
    }
  }
}
