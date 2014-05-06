package org.unbiquitous.uImpala.util;

/**
 * A color.
 * @author Pimenta
 *
 */
public class Color {
  public static final Color black   = new Color(0.0f, 0.0f, 0.0f);
  public static final Color gray    = new Color(0.5f, 0.5f, 0.5f);
  public static final Color white   = new Color(1.0f, 1.0f, 1.0f);
  public static final Color red     = new Color(1.0f, 0.0f, 0.0f);
  public static final Color green   = new Color(0.0f, 1.0f, 0.0f);
  public static final Color blue    = new Color(0.0f, 0.0f, 1.0f);
  public static final Color cyan    = new Color(0.0f, 1.0f, 1.0f);
  public static final Color magenta = new Color(1.0f, 0.0f, 1.0f);
  public static final Color yellow  = new Color(1.0f, 1.0f, 0.0f);
  
  public float r, g, b, a;
  
  /**
   * Assignment constructor.
   * @param r Red component.
   * @param g Green component.
   * @param b Blue component.
   */
  public Color(float r, float g, float b) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = 1f;
  }
  
  /**
   * Assignment constructor.
   * @param r Red component.
   * @param g Green component.
   * @param b Blue component.
   * @param a Alpha component.
   */
  public Color(float r, float g, float b, float a) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;
  }
}
