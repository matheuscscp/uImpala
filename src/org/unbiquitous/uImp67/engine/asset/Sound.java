package org.unbiquitous.uImp67.engine.asset;

import org.newdawn.slick.openal.Audio;

/**
 * Supported formats: OGG, WAV, AIF/AIFF
 * @author Pimenta
 *
 */
public class Sound {
  public Sound(AssetManager assets, String path) {
    audio = assets.getAudio(path);
  }
  
  public int getBufferID() {
    return audio.getBufferID();
  }
  
  public float getPosition() {
    return audio.getPosition();
  }
  
  public boolean isPlaying() {
    return audio.isPlaying();
  }
  
  public int play(float pitch, float gain, boolean loop) {
    return audio.playAsSoundEffect(pitch, gain, loop);
  }
  
  public int play(float pitch, float gain, boolean loop, float x, float y, float z) {
    return audio.playAsSoundEffect(pitch, gain, loop, x, y, z);
  }
  
  public boolean setPosition(float pos) {
    return audio.setPosition(pos);
  }
  
  public void stop() {
    audio.stop();
  }
  
  private Audio audio;
}
