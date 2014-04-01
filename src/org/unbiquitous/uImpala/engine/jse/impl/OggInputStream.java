package org.unbiquitous.uImpala.engine.jse.impl;

import java.io.IOException;

public final class OggInputStream extends org.unbiquitous.uImpala.engine.asset.OggInputStream {
  org.newdawn.slick.openal.OggInputStream oggInputStream;
  
  public OggInputStream(org.newdawn.slick.openal.OggInputStream ogg) {
    oggInputStream = ogg;
  }
  
  public int getChannels() {
    return oggInputStream.getChannels();
  }
  
  public int getRate() {
    return oggInputStream.getRate();
  }
  
  public void close() {
    try {
      oggInputStream.close();
    } catch (IOException e) {
    }
  }
  
  public int read(byte[] b) throws IOException {
    return oggInputStream.read(b);
  }
}
