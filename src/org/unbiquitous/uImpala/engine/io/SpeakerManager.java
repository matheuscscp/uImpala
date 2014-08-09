package org.unbiquitous.uImpala.engine.io;

import java.util.Arrays;
import java.util.List;

/**
 * Class for sound speakers management.
 * @author Pimenta
 *
 */
public class SpeakerManager implements OutputManager {
  public IOResource alloc() {
    if (busy)
      return null;
    busy = true;
    return speaker;
  }
  
  public boolean free(IOResource rsc) {
    if (!busy || speaker != (Speaker)rsc)
      return false;
    busy = false;
    return true;
  }
  
  public void update() {
    speaker.update();
  }
  
  public void close() {
    speaker.close();
    speaker = null;
  }
  
  private Speaker speaker = Speaker.create();
  private boolean busy = false;
  
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List list() {
		return Arrays.asList(speaker);
	}
}
