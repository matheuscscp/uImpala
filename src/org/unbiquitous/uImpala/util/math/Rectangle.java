package org.unbiquitous.uImpala.util.math;

public class Rectangle {
  protected float width, height, angle;
  protected Point center = new Point();
  
  public Rectangle() {
    setX(0);
    setY(0);
    setWidth(0);
    setHeight(0);
    setAngle(0);
  }
  
  
  
  public Rectangle(Point center, float width, float height) {
	this.center = center;
	this.width = width;
	this.height = height;
}



/**
   * Assignment constructor.
   * @param x Coordinate x of the center of the rectangle.
   * @param y Coordinate y of the center of the rectangle.
   * @param w Rectangle width.
   * @param h Rectangle height.
   * @param angle Angle in degrees of rotation around the center.
   */
  public Rectangle(float x, float y, float w, float h, float angle) {
    setX(x);
    setY(y);
    setWidth(w);
    setHeight(h);
    setAngle(angle);
  }
  
  public Rectangle(Rectangle other) {
    setX(other.center.x);
    setY(other.center.y);
    setWidth(other.width);
    setHeight(other.height);
    setAngle(other.angle);
  }
  
  public float getX() {
    return center.x;
  }
  
  /**
   * 
   * @param x Coordinate x of the center of the rectangle.
   */
  public void setX(float x) {
    this.center.x = (int) x;
  }
  
  public float getY() {
    return center.y;
  }
  
  /**
   * 
   * @param y Coordinate y of the center of the rectangle.
   */
  public void setY(float y) {
    this.center.y = (int) y;
  }
  
  public float getW() {
    return width;
  }
  
  /**
   * 
   * @param w Rectangle width.
   */
  public void setWidth(float w) {
    this.width = (w < 0 ? 0 : w);
  }
  
  public float getH() {
    return height;
  }
  
  /**
   * 
   * @param h Rectangle height.
   */
  public void setHeight(float h) {
    this.height = (h < 0 ? 0 : h);
  }
  
  public float getAngle() {
    return angle;
  }
  
  /**
   * 
   * @param angle Angle in degrees of rotation around the center.
   */
  public void setAngle(float angle) {
    this.angle = angle;
  }
  
  public float getArea() {
    return width*height;
  }
  
  public boolean isPointInside(float x1, float y1) {
    Vector3 v = new Vector3(x1-center.x,y1-center.y,0);
    v.setrotate(-angle, new Vector3(0,0,1));
    return v.x() >= -width/2 && v.x() <= width/2 && v.y() >= -height/2 && v.y() <= height/2;
  }
}
