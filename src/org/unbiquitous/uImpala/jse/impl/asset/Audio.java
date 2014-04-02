package org.unbiquitous.uImpala.jse.impl.asset;

import java.io.IOException;

import org.newdawn.slick.openal.OggInputStream;
import org.newdawn.slick.util.ResourceLoader;
import org.unbiquitous.uImpala.engine.core.GameComponents;
import org.unbiquitous.uImpala.engine.core.GameSettings;
import org.unbiquitous.uImpala.engine.io.Speaker;

public class Audio implements org.unbiquitous.uImpala.engine.asset.Audio {
  protected Audio(String path) {
    this.path = path;
  }
  
  public AudioPlayback play(Speaker speaker, float volume, boolean loop) {
    return new AudioPlayback(speaker, this, volume, loop);
  }
  
  protected OggInputStream stream() {
    try {
      org.newdawn.slick.openal.OggInputStream slickOgg;
      slickOgg = new org.newdawn.slick.openal.OggInputStream(
        ResourceLoader.getResourceAsStream(
          GameComponents.get(GameSettings.class).get("root_path") + "/" + path
        )
      );
      return new OggInputStream(slickOgg);
    } catch (IOException e) {
      throw new Error(e);
    }
  }
  
  private String path;
}
