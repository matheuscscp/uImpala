package org.unbiquitous.uImp67.engine.asset;

import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.OggInputStream;
import org.unbiquitous.uImp67.engine.io.Speaker;

/**
 * Class to start a playback of an audio file.
 * @author Pimenta
 *
 */
public class Audio {
  public static final int PLAYING = AL10.AL_PLAYING;
  public static final int PAUSED  = AL10.AL_PAUSED;
  public static final int STOPPED = AL10.AL_STOPPED;
  
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
    return new AudioControl(speaker, this, volume, loop);
  }
  
  /**
   * Engine's private use.
   */
  protected OggInputStream stream() {
    return assets.getOggInputStream(path);
  }
  
  private AssetManager assets;
  private String path;
}
