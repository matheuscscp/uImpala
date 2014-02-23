package org.unbiquitous.ubiengine.engine.asset;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.unbiquitous.ubiengine.engine.core.GameComponents;
import org.unbiquitous.ubiengine.engine.core.UbiGame.Settings;

/**
 * Class to manage assets.
 * @author Pimenta
 *
 */
public final class Assets {
  /**
   * Load a texture.
   * @param fn Texture path.
   * @return Texture loaded.
   */
  public static Texture loadTexture(String fn) {
    Assets instance = GameComponents.get(Assets.class);
    Asset asset = instance.assets.get(fn);
    if (asset != null) {
      asset.refCount++;
      return (Texture)asset.object;
    }
    
    try {
      asset = new Asset(TextureLoader.getTexture(getFormat(fn),
        ResourceLoader.getResourceAsStream(
          GameComponents.get(Settings.class).get("root_path") + "/" + fn
        )
      ));
    } catch (IOException e) {
      throw new Error(e);
    }
    
    instance.assets.put(fn, asset);
    asset.refCount++;
    return (Texture)asset.object;
  }
  
  /**
   * Load audio.
   * @param fn Audio path.
   * @return Audio loaded.
   */
  public static Audio loadAudio(String fn) {
    Assets instance = GameComponents.get(Assets.class);
    Asset asset = instance.assets.get(fn);
    if (asset != null) {
      asset.refCount++;
      return (Audio)asset.object;
    }
    
    try {
      asset = new Asset(AudioLoader.getAudio(getFormat(fn),
        ResourceLoader.getResourceAsStream(
          GameComponents.get(Settings.class).get("root_path") + "/" + fn
        )
      ));
    } catch (IOException e) {
      throw new Error(e);
    }
    
    instance.assets.put(fn, asset);
    asset.refCount++;
    return (Audio)asset.object;
  }
  
  /**
   * Load streaming audio.
   * @param fn Audio path.
   * @return Audio loaded.
   */
  public static Audio loadStreamingAudio(String fn) {
    Assets instance = GameComponents.get(Assets.class);
    Asset asset = instance.assets.get(fn);
    if (asset != null) {
      asset.refCount++;
      return (Audio)asset.object;
    }
    
    try {
      asset = new Asset(AudioLoader.getStreamingAudio(getFormat(fn),
        ResourceLoader.getResource(
          GameComponents.get(Settings.class).get("root_path") + "/" + fn
        )
      ));
    } catch (IOException e) {
      throw new Error(e);
    }
    
    instance.assets.put(fn, asset);
    asset.refCount++;
    return (Audio)asset.object;
  }
  
  /**
   * Load font.
   * @param fn Font path.
   * @return Font loaded.
   */
  public static Font loadFont(String fn) {
    Assets instance = GameComponents.get(Assets.class);
    Asset asset = instance.assets.get(fn);
    if (asset != null) {
      asset.refCount++;
      return (Font)asset.object;
    }
    
    try {
      asset = new Asset(Font.createFont(Font.TRUETYPE_FONT,
        ResourceLoader.getResourceAsStream(
          GameComponents.get(Settings.class).get("root_path") + "/" + fn
        )
      ));
    } catch (Exception e) {
      throw new Error(e);
    }
    
    instance.assets.put(fn, asset);
    asset.refCount++;
    return (Font)asset.object;
  }
  
  /**
   * Load map from a text file.
   * @param fn Text file path.
   * @return Map loaded.
   */
  public static int[][] loadMap(String fn) {
    Assets instance = GameComponents.get(Assets.class);
    Asset asset = instance.assets.get(fn);
    if (asset != null) {
      asset.refCount++;
      return (int[][])asset.object;
    }
    
    Scanner sc;
    try {
      sc = new Scanner(new FileReader(
        GameComponents.get(Settings.class).get("root_path") + "/" + fn
      ));
    } catch (FileNotFoundException e) {
      throw new Error(e);
    }
    sc.useDelimiter(",");
    int rows = sc.nextInt();
    int cols = sc.nextInt();
    sc.nextLine();
    int[][] map = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++)
        map[i][j] = sc.nextInt();
      sc.nextLine();
    }
    sc.close();
    asset = new Asset(map);
    
    instance.assets.put(fn, asset);
    asset.refCount++;
    return (int[][])asset.object;
  }
  
  /**
   * Release an asset.
   * @param fn Asset path.
   */
  public static void free(String fn) {
    HashMap<String, Asset> assets = GameComponents.get(Assets.class).assets;
    Asset asset = assets.get(fn);
    asset.refCount--;
    if (asset.refCount == 0)
      assets.remove(fn);
  }
//==============================================================================
//nothings else matters from here to below
//==============================================================================
  private static String getFormat(String fn) {
    int i;
    for (i = fn.length() - 1; i >= 0 && fn.charAt(i) != '.'; i--);
    if (i < 0)
      throw new Error("Invalid path for asset: " + fn);
    String fmt = "";
    for (int j = i + 1; j < fn.length(); j++)
      fmt += fn.charAt(j);
    return fmt.toUpperCase();
  }
  
  private static final class Asset {
    private Object object;
    private int refCount = 0;
    private Asset(Object a) {
      object = a;
    }
  }
  
  private HashMap<String, Asset> assets = new HashMap<String, Asset>();
}
