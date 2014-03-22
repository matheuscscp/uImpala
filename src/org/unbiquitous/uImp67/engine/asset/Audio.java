package org.unbiquitous.uImp67.engine.asset;

import java.io.IOException;

import org.newdawn.slick.openal.OggInputStream;
import org.newdawn.slick.util.ResourceLoader;
import org.unbiquitous.uImp67.engine.core.GameComponents;
import org.unbiquitous.uImp67.engine.core.GameSettings;

/**
 * Class to load OGG audio files.
 * @author Lucas Carvalho
 *
 */
public class Audio {
  private OggInputStream stream;
  
  public Audio(String path) {
    try {
      stream = new OggInputStream(ResourceLoader.getResourceAsStream(
        GameComponents.get(GameSettings.class).get("root_path") + "/" + path)
      );
    } catch (IOException e) {
      throw new Error(e);
    }
  }
  
  public int ReadBlock(byte[] buffer) {
    int count = 0;
    
    try {
      count = stream.read(buffer);
    } catch (IOException e) {
      count = -1;
    }
    
    if (count > 0)
      currentPosition += count;
    
    return count;
  }
  
  int currentPosition;
  
  public int getPosition() {
    return currentPosition;
  }
  
  public void setPosition(int pos) {
    try {
      stream.reset();
      stream.skip(currentPosition = pos);
    } catch (IOException e) {
    }
  }
  
  public int getRate() {
    return stream.getRate();
  }
  
  public int getChannels() {
    return stream.getChannels();
  }
}

