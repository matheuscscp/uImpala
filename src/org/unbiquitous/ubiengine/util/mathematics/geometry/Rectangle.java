package org.unbiquitous.ubiengine.util.mathematics.geometry;

public class Rectangle {
  private float x, y, w, h;

  public Rectangle() {
    setX(0);
    setY(0);
    setW(0);
    setH(0);
  }
  
  public Rectangle(float x, float y, float w, float h) {
    setX(x);
    setY(y);
    setW(w);
    setH(h);
  }
  
  public Rectangle(Rectangle other) {
    setX(other.x);
    setY(other.y);
    setW(other.w);
    setH(other.h);
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

  public float getW() {
    return w;
  }

  public void setW(float w) {
    this.w = (w < 0 ? 0 : w);
  }

  public float getH() {
    return h;
  }

  public void setH(float h) {
    this.h = (h < 0 ? 0 : h);
  }
  
  public float getArea() {
    return w*h;
  }
}
