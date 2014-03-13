package org.unbiquitous.uImp67.util.math;

public class Circle {
  protected float x, y, r;
  
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
  
  public boolean isPointInside(float x1, float y1) {
    float dx = x1 = x, dy = y1 - y;
    return Math.sqrt(dx*dx + dy*dy) <= r;
  }
}
