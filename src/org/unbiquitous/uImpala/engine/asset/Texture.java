package org.unbiquitous.uImpala.engine.asset;

/**
 * Texture represents an image generated OpenGL texture.
 * @author Pimenta
 *
 */
public interface Texture {
  /**
   * Get texture width.
   * @return Width in pixels.
   */
  public int getTextureWidth();
  
  /**
   * Get texture height.
   * @return Height in pixels.
   */
  public int getTextureHeight();
  
  /**
   * Get image width.
   * @return Width in pixels.
   */
  public int getImageWidth();
  
  /**
   * Get image height.
   * @return Height in pixels.
   */
  public int getImageHeight();
  
  /**
   * Bind the OpenGL texture.
   */
  public void bind();
  
  /**
   * Release the OpenGL texture.
   */
  public void release();
}
