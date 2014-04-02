package org.unbiquitous.uImpala.util;

/**
 * A color.
 * @author Pimenta
 *
 */
public class Color {
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
