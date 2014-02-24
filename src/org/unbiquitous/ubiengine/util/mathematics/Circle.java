package org.unbiquitous.ubiengine.util.mathematics;

public class Circle {
  private float x, y, r;

  public Circle() {
    setX(0);
    setY(0);
    setR(0);
  }
  
  public Circle(float x, float y, float r) {
    setX(x);
    setY(y);
    setR(r);
  }
  
  public Circle(Circle other) {
    setX(other.x);
    setY(other.y);
    setR(other.r);
  }
  
  public float getX() {
    return x;
  }

  public void setX(float x) {
    this.x = x;
  }

  public float getY() {
    return y;
  }

  public void setY(float y) {
    this.y = y;
  }

  public float getR() {
    return r;
  }

  public void setR(float r) {
    this.r = (r < 0 ? 0 : r);
  }

  public float getArea() {
    return (float) (Math.PI*r*r);
  }
}
