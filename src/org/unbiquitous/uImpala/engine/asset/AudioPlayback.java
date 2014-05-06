package org.unbiquitous.uImpala.engine.asset;

/**
 * Interface to handle audio playback.
 * @author Pimenta
 *
 */
public interface AudioPlayback {
  /**
   * State of the playback.
   * @author Pimenta
   *
   */
  public enum State {
    PLAYING, PAUSED, STOPPED
  }
  
  /**
   * Query playback state.
   * @return Playback state.
   */
  public AudioPlayback.State state();
  
  /**
   * Pause playback.
   */
  public void pause();
  
  /**
   * Resume playback.
   */
  public void resume();
  
  /**
   * Stop playback.
   */
  public void stop();
  
  /**
   * Set playback volume.
   * @param volume A number in [0, 1].
   */
  public void volume(float volume);
}
