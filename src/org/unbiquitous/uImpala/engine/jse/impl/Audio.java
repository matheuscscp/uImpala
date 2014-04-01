package org.unbiquitous.uImpala.engine.jse.impl;

import org.unbiquitous.uImpala.engine.io.Speaker;

public class Audio implements org.unbiquitous.uImpala.engine.asset.Audio {
  public Audio(AssetManager assets, String path) {
    this.assets = assets;
    this.path = path;
  }
  
  public AudioPlayback play(Speaker speaker, float volume, boolean loop) {
    return new AudioPlayback(speaker, this, volume, loop);
  }
  
  protected OggInputStream stream() {
    return assets.getOggInputStream(path);
  }
  
  private AssetManager assets;
  private String path;
}
