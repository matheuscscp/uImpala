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
import org.unbiquitous.ubiengine.engine.core.GameSettings;

/**
 * Class to manage assets of a game scene.
 * @author Pimenta
 *
 */
public final class AssetManager {
  /**
   * Load a texture.
   * @param path Texture path.
   * @return Texture loaded.
   */
  public Texture getTexture(String path) {
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
    return asset;
  }
  
  /**
   * Load audio.
   * @param path Audio path.
   * @return Audio loaded.
   */
  public Audio getAudio(String path) {
    Audio asset = (Audio)assets.get(path);
    if (asset != null)
      return asset;
    
    try {
      asset = AudioLoader.getAudio(getFormat(path),
        ResourceLoader.getResourceAsStream(
          GameComponents.get(GameSettings.class).get("root_path") + "/" + path
        )
      );
    } catch (IOException e) {
      throw new Error(e);
    }
    
    assets.put(path, asset);
    return asset;
  }
  
  /**
   * Load streaming audio.
   * @param path Audio path.
   * @return Audio loaded.
   */
  public Audio getStreamingAudio(String path) {
    Audio asset = (Audio)assets.get(path);
    if (asset != null)
      return asset;
    
    try {
      asset = AudioLoader.getStreamingAudio(getFormat(path),
        ResourceLoader.getResource(
          GameComponents.get(GameSettings.class).get("root_path") + "/" + path
        )
      );
    } catch (IOException e) {
      throw new Error(e);
    }
    
    assets.put(path, asset);
    return asset;
  }
  
  /**
   * Load font.
   * @param path Font path.
   * @return Font loaded.
   */
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
  
  /**
   * Load map from a text file.
   * @param path Text file path.
   * @return Map loaded.
   */
  public int[][] getMap(String path) {
    int[][] asset = (int[][])assets.get(path);
    if (asset != null)
      return asset;
    
    Scanner sc;
    try {
      sc = new Scanner(new FileReader(
        GameComponents.get(GameSettings.class).get("root_path") + "/" + path
      ));
    } catch (FileNotFoundException e) {
      throw new Error(e);
    }
    sc.useDelimiter(",");
    int rows = sc.nextInt();
    int cols = sc.nextInt();
    sc.nextLine();
    asset = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++)
        asset[i][j] = sc.nextInt();
      sc.nextLine();
    }
    sc.close();
    
    assets.put(path, asset);
    return asset;
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
  
  private HashMap<String, Object> assets = new HashMap<String, Object>();
}
