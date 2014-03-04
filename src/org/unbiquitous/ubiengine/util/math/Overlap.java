package org.unbiquitous.ubiengine.util.math;

/**
 * Class to implement overlap detection between geometrical shapes.
 * @author Pimenta
 *
 */
public final class Overlap {
  /**
   * Checks overlapping between two circles.
   * @param c1 First circle.
   * @param c2 Second circle.
   * @return Returns true if overlaps.
   */
  public static boolean circleCircle(Circle c1, Circle c2) {
    float dx = c1.x - c2.x, dy = c1.y - c2.y;
    return Math.sqrt(dx*dx + dy*dy) <= c1.r + c2.r;
  }
  
  /**
   * Checks overlapping between two rectangles.
   * @param r1 First rectangle.
   * @param r2 Second rectangle.
   * @return Returns true if overlaps.
   */
  public static boolean rectRect(Rectangle r1, Rectangle r2) {
    //TODO
    return false;
  }
  
  /**
   * Checks overlapping between a circle and a rectangle.
   * @param c Circle.
   * @param r Rectangle.
   * @return Returns true if overlaps.
   */
  public static boolean circleRect(Circle c, Rectangle r) {
    //TODO
    return false;
  }
  
  /**
   * @see Overlap#circleRect
   */
  public static boolean rectCircle(Rectangle r, Circle c) {
    return circleRect(c, r);
  }
}
