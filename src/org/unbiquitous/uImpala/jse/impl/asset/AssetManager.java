package org.unbiquitous.uImpala.jse.impl.asset;

import java.awt.Font;
import java.io.IOException;
import java.util.HashSet;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.unbiquitous.uImpala.engine.core.GameComponents;
import org.unbiquitous.uImpala.engine.core.GameSettings;

public final class AssetManager extends org.unbiquitous.uImpala.engine.asset.AssetManager {
  public Sprite newSprite(String path) {
    return new Sprite(getTexture(path));
  }
  
  private Texture getTexture(String path) {
    Texture asset = (Texture)assets.get(path);
    if (asset != null)
      return asset;
    
    try {
      asset = TextureLoader.getTexture(getFormat(path),
        ResourceLoader.getResourceAsStream(
          GameComponents.get(GameSettings.class).get("root_path") + "/" + path
        )
      );
    } catch (IOException e) {
      throw new Error(e);
    }
    
    assets.put(path, asset);
    textures.add(asset);
    return asset;
  }
  
  public Text newText(String fontPath, String text) {
    return new Text(getFont(fontPath).deriveFont(24f), text);
  }
  
  private Font getFont(String path) {
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
  
  public Text newText(Font font, String text) {
    return new Text(font, text);
  }
  
  public Audio newAudio(String path) {
    return new Audio(path);
  }
  
  public void destroy() {
    for (Texture t : textures)
      t.release();
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
  
  protected HashSet<Texture> textures = new HashSet<Texture>();
}
