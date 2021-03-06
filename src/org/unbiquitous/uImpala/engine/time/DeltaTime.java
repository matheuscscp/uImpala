package org.unbiquitous.uImpala.engine.time;

/**
 * Class to handle update rate. The initial rate is 32 Hz.
 * @author Pimenta
 *
 */
public class DeltaTime {
  /**
   * The fixed value of update rate.
   * @return Frequency in updates per second.
   */
  public int getUPS() {
    return ups;
  }
  
  /**
   * Sets the fixed value of update rate.
   * @param ups Frequency in updates per second.
   */
  public void setUPS(int ups) {
    if (ups > 0) {
      this.ups = ups;
      dtFloat = 1/(float)ups;
      dtFixed = 1000/ups;
    }
  }
  
  /**
   * The time interval between two updates.
   * @return Time in seconds.
   */
  public float getDT() {
    return dtFloat;
  }
  
  /**
   * @return Update's unique ID.
   */
  public long updateID() {
    return updateID;
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  private long last = 0;            // unit: milliseconds
  private int ups = 32;             // unit: updates/second
  private float dtFloat = 0.03125f; // unit: seconds
  private int dtFixed = 31;         // unit: milliseconds
  private long dt = 0;              // unit: milliseconds
  private long updateID = 0;
  
  /**
   * Engine's private use.
   */
  public DeltaTime() {
    last = Time.get() - dtFixed;
  }
  
  /**
   * Engine's private use.
   */
  public void update() {
    long now = Time.get();
    dt = now - last;
    last = now;
    updateID = now - 1;
  }
  
  public void sleepDt(){
	  long now = Time.get();
	  long deltaSpent = now - last;
	  long deltaLeft = dtFixed - deltaSpent; 
	  if (deltaLeft > 0) {
	      try {
			Thread.sleep(deltaLeft);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	      updateID++;
	  }
  }
  
  /**
   * Engine's private use.
   */
  public void accumulate() {
//    last -= dt;
  }
}
