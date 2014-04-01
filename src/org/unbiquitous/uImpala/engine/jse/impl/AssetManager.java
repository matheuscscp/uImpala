package org.unbiquitous.uImpala.engine.jse.impl;

import java.awt.Font;
import java.io.IOException;

import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.unbiquitous.uImpala.engine.core.GameComponents;
import org.unbiquitous.uImpala.engine.core.GameSettings;

public final class AssetManager extends org.unbiquitous.uImpala.engine.asset.AssetManager {
  public org.unbiquitous.uImpala.engine.asset.Texture getTexture(String path) {
    Texture asset = (Texture)assets.get(path);
    if (asset != null)
      return asset;
    
    try {
      org.newdawn.slick.opengl.Texture slickTex;
      slickTex = TextureLoader.getTexture(getFormat(path),
        ResourceLoader.getResourceAsStream(
          GameComponents.get(GameSettings.class).get("root_path") + "/" + path
        )
      );
      asset = new Texture(slickTex);
    } catch (IOException e) {
      throw new Error(e);
    }
    
    assets.put(path, asset);
    textures.add(asset);
    return asset;
  }
  
  public Font getFont(String path) {
    Font asset = (Font)assets.get(path);
    if (asset != null)
      return asset;
    
    try {
      asset = Font.createFont(Font.TRUETYPE_FONT,
        ResourceLoader.getResourceAsStream(
          GameComponents.get(GameSettings.class).get("root_path") + "/" + path
        )
      );
    } catch (Exception e) {
      throw new Error(e);
    }
    
    assets.put(path, asset);
    return asset;
  }
  
  public OggInputStream getOggInputStream(String path) {
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
  
  private static String getFormat(String fn) {
    int i;
    for (i = fn.length() - 1; i >= 0 && fn.charAt(i) != '.'; i--);
    if (i < 0)
      throw new Error("Invalid path for asset: " + fn);
    String fmt = "";
    for (int j = i + 1; j < fn.length(); j++)
      fmt += fn.charAt(j);
    fmt = fmt.toUpperCase();
    if (fmt.equals("AIFF"))
      return "AIF";
    return fmt;
  }
}
