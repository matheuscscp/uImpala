package org.unbiquitous.uImp67.engine.asset;

import org.unbiquitous.uImp67.engine.io.Speaker;

/**
 * Class to start a playback of an audio file.
 * @author Pimenta
 *
 */
public class Audio {
  /**
   * Constructor.
   * @param assets Object to open the audio file.
   * @param path String path to file.
   */
  public Audio(AssetManager assets, String path) {
    this.assets = assets;
    this.path = path;
  }
  
  /**
   * Start playback.
   * @param speaker Speaker on which this audio will be played.
   * @param volume A number in [0, 1].
   * @param loop Pass true to loop the playback.
   * @return A handler to pause, resume, stop and adjust volume.
   */
  public AudioControl play(Speaker speaker, float volume, boolean loop) {
    return new AudioControl(speaker, assets.getOggInputStream(path), volume, loop);
  }
  
  private AssetManager assets;
  private String path;
}
