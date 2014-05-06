package org.unbiquitous.uImpala.engine.asset;

import org.unbiquitous.uImpala.engine.io.Speaker;

/**
 * Class to start a playback of an audio file.
 * @author Pimenta
 *
 */
public interface Audio {
  /**
   * Start playback.
   * @param speaker Speaker on which this audio will be played.
   * @param volume A number in [0, 1].
   * @param loop Pass true to loop the playback.
   * @return A handler to pause, resume, stop and adjust volume.
   */
  public AudioPlayback play(Speaker speaker, float volume, boolean loop);
}
