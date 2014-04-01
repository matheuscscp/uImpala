package org.unbiquitous.uImpala.engine.jse.impl;

public final class OggInputStream extends org.unbiquitous.uImpala.engine.asset.OggInputStream {
  org.newdawn.slick.openal.OggInputStream oggInputStream;
  
  public OggInputStream(org.newdawn.slick.openal.OggInputStream ogg) {
    oggInputStream = ogg;
  }
}
