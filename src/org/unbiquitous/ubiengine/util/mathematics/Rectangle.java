package org.unbiquitous.ubiengine.util.mathematics;

public class Rectangle {
  private float x, y, w, h, angle;
  
  public Rectangle() {
    setX(0);
    setY(0);
    setW(0);
    setH(0);
    setAngle(0);
  }
  
  public Rectangle(float x, float y, float w, float h, float angle) {
    setX(x);
    setY(y);
    setW(w);
    setH(h);
    setAngle(angle);
  }
  
  public Rectangle(Rectangle other) {
    setX(other.x);
    setY(other.y);
    setW(other.w);
    setH(other.h);
    setAngle(other.angle);
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
  
  public float getAngle() {
    return angle;
  }
  
  public void setAngle(float angle) {
    this.angle = angle;
  }
  
  public float getArea() {
    return w*h;
  }
  
  public boolean isPointInside(float x1, float y1) {
    Vector3 v = new Vector3(x1-x,y1-y,0);
    v.setrotate(-angle, new Vector3(0,0,1));
    return v.x() >= -w/2 && v.x() <= w/2 && v.y() >= -h/2 && v.y() <= h/2;
  }
}
