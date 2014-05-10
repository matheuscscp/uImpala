package org.unbiquitous.uImpala.dalvik.impl.asset;

import java.awt.Font;

public class AssetManager extends org.unbiquitous.uImpala.engine.asset.AssetManager {
  private static class Factory implements org.unbiquitous.uImpala.engine.asset.AssetManager.Factory {
    public AssetManager create() {
      return new AssetManager();
    }
  }
  
  public static synchronized void initImpl() {
    if (factory == null)
      factory = new Factory();
  }
  
  @Override
  public Sprite newSprite(String path) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Text newText(String fontPath, String text) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Text newText(Font font, String text) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Audio newAudio(String path) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void destroy() {
    // TODO Auto-generated method stub
    
  }
  
}
