package org.unbiquitous.uImp67.util.math;

/**
 * Class to represent a three-dimensional vector.
 * Can also be used as two-dimensional vector, if z coordinate remain null.
 * 
 * @author Matheus
 */
public class Vector3 {
  private float x_;
  private float y_;
  private float z_;
  private float length_;
  private boolean has_set;
  
  public Vector3() {
    x_ = 0;
    y_ = 0;
    z_ = 0;
    has_set = true;
  }
  
  public Vector3(float x, float y, float z) {
    x_ = x;
    y_ = y;
    z_ = z;
    has_set = true;
  }
  
  public Vector3(Vector3 other) {
    x_ = other.x_;
    y_ = other.y_;
    z_ = other.z_;
    has_set = true;
  }
  
  // getters
  
  public float x() {
    return x_;
  }
  
  public float y() {
    return y_;
  }
  
  public float z() {
    return z_;
  }
  
  public float length() {
    if (has_set) {
      has_set = false;
      length_ = (float) Math.sqrt(x_*x_ + y_*y_ + z_*z_);
    }
    return length_;
  }
  
  // setters
  
  public void setx(float x) {
    has_set = true;
    x_ = x;
  }
  
  public void sety(float y) {
    has_set = true;
    y_ = y;
  }
  
  public void setz(float z) {
    has_set = true;
    z_ = z;
  }
  
  public void addx(float dx) {
    has_set = true;
    x_ += dx;
  }
  
  public void addy(float dy) {
    has_set = true;
    y_ += dy;
  }
  
  public void addz(float dz) {
    has_set = true;
    z_ += dz;
  }
  
  public Vector3 add(Vector3 other) {
    has_set = true;
    x_ += other.x_;
    y_ += other.y_;
    z_ += other.z_;
    return this;
  }
  
  public Vector3 sub(Vector3 other) {
    has_set = true;
    x_ -= other.x_;
    y_ -= other.y_;
    z_ -= other.z_;
    return this;
  }
  
  public Vector3 mult(float scalar) {
    has_set = true;
    x_ *= scalar;
    y_ *= scalar;
    z_ *= scalar;
    return this;
  }
  
  public Vector3 div(float scalar) {
    if (scalar == 0)
      throw new Error("Division by zero");
    
    has_set = true;
    x_ /= scalar;
    y_ /= scalar;
    z_ /= scalar;
    return this;
  }
  
  public void setopposite() {
    x_ = -x_;
    y_ = -y_;
    z_ = -z_;
  }
  
  // arithmetic operations
  
  public Vector3 sum(Vector3 other) {
    Vector3 v = new Vector3();
    v.x_ = x_ + other.x_;
    v.y_ = y_ + other.y_;
    v.z_ = z_ + other.z_;
    return v;
  }
  
  public Vector3 subtract(Vector3 other) {
    Vector3 v = new Vector3();
    v.x_ = x_ - other.x_;
    v.y_ = y_ - other.y_;
    v.z_ = z_ - other.z_;
    return v;
  }
  
  public Vector3 multiply(float scalar) {
    Vector3 v = new Vector3();
    v.x_ = x_*scalar;
    v.y_ = y_*scalar;
    v.z_ = z_*scalar;
    return v;
  }
  
  public Vector3 divide(float scalar) {
    if (scalar == 0)
      throw new Error("Division by zero");
    
    Vector3 v = new Vector3();
    v.x_ = x_/scalar;
    v.y_ = y_/scalar;
    v.z_ = z_/scalar;
    return v;
  }
  
  public Vector3 opposite() {
    Vector3 v = new Vector3();
    v.x_ = -x_;
    v.y_ = -y_;
    v.z_ = -z_;
    return v;
  }
  
  // logical operations
  
  public boolean equal(Vector3 other) {
    return (x_ == other.x_ && y_ == other.y_ && z_ == other.z_);
  }
  
  public boolean notequal(Vector3 other) {
    return (x_ != other.x_ || y_ != other.y_ || z_ != other.z_);
  }

  public boolean greater(Vector3 other) {
    return (length() > other.length());
  }
  
  public boolean less(Vector3 other) {
    return (length() < other.length());
  }

  public boolean greaterorequal(Vector3 other) {
    return (length() >= other.length());
  }
  
  public boolean lessorequal(Vector3 other) {
    return (length() <= other.length());
  }
  
  //this dot other
  public float dot(Vector3 other) {
    return x_*other.x_ + y_*other.y_ + z_*other.z_;
  }
  
  // angle in degrees between this and other
  public float angle(Vector3 other) {
    if (length() == 0 || other.length() == 0)
      throw new Error("Division by zero");
    
    return (float) Math.toDegrees(Math.acos(dot(other)/(length_*other.length_)));
  }
  
  // this unit vector
  public Vector3 unitvec() {
    if (length() == 0)
      throw new Error("Division by zero");
    
    Vector3 v = new Vector3();
    v.x_ = x_/length_;
    v.y_ = y_/length_;
    v.z_ = z_/length_;
    return v;
  }
  public void setunitvec() {
    if (length() == 0)
      throw new Error("Division by zero");
    
    x_ /= length_;
    y_ /= length_;
    z_ /= length_;
  }
  
  // this projection over other
  public Vector3 proj(Vector3 other) {
    if (other.length() == 0)
      throw new Error("Division by zero");
    
    Vector3 v = new Vector3();
    float scalar = dot(other)/(other.length_*other.length_);
    v.x_ = other.x_*scalar;
    v.y_ = other.y_*scalar;
    v.z_ = other.z_*scalar;
    return v;
  }
  public void setproj(Vector3 other) {
    if (other.length() == 0)
      throw new Error("Division by zero");
    
    float scalar = dot(other)/(other.length_*other.length_);
    x_ = other.x_*scalar;
    y_ = other.y_*scalar;
    z_ = other.z_*scalar;
  }
  
  // this rejection over other
  public Vector3 rej(Vector3 other) {
    if (other.length() == 0)
      throw new Error("Division by zero");
    
    Vector3 v = new Vector3();
    float scalar = dot(other)/(other.length_*other.length_);
    v.x_ = x_ - other.x_*scalar;
    v.y_ = y_ - other.y_*scalar;
    v.z_ = z_ - other.z_*scalar;
    return v;
  }
  public void setrej(Vector3 other) {
    if (other.length() == 0)
      throw new Error("Division by zero");
    
    float scalar = dot(other)/(other.length_*other.length_);
    x_ -= other.x_*scalar;
    y_ -= other.y_*scalar;
    z_ -= other.z_*scalar;
  }
  
  // this scalar projection over other
  public float scalarproj(Vector3 other) {
    if (other.length() == 0)
      throw new Error("Division by zero");
    
    return (x_*other.x_ + y_*other.y_ + z_*other.z_)/other.length_;
  }
  
  // this cross other
  public Vector3 cross(Vector3 other) {
    Vector3 v = new Vector3();
    v.x_ = y_*other.z_ - z_*other.y_;
    v.y_ = z_*other.x_ - x_*other.z_;
    v.z_ = x_*other.y_ - y_*other.x_;
    return v;
  }
  public void setcross(Vector3 other) {
    Vector3 v = new Vector3();
    v.x_ = y_*other.z_ - z_*other.y_;
    v.y_ = z_*other.x_ - x_*other.z_;
    v.z_ = x_*other.y_ - y_*other.x_;
    x_ = v.x_;
    y_ = v.y_;
    z_ = v.z_;
  }
  
  // rotates this around other by angle degrees
  public Vector3 rotate(float angle, Vector3 other) {
    Vector3 v = new Vector3();
    Vector3 u = other.unitvec();
    angle = (float) Math.toRadians(angle);
    float sint = (float) Math.sin(angle);
    float cost = (float) Math.cos(angle);
    v.x_ = x_*(u.x_*u.x_*(1 - cost) +      cost)  +  y_*(u.x_*u.y_*(1 - cost) - u.z_*sint)  +  z_*(u.x_*u.z_*(1 - cost) + u.y_*sint);
    v.y_ = x_*(u.x_*u.y_*(1 - cost) + u.z_*sint)  +  y_*(u.y_*u.y_*(1 - cost) +      cost)  +  z_*(u.y_*u.z_*(1 - cost) - u.x_*sint);
    v.z_ = x_*(u.x_*u.z_*(1 - cost) - u.y_*sint)  +  y_*(u.y_*u.z_*(1 - cost) + u.x_*sint)  +  z_*(u.z_*u.z_*(1 - cost) +      cost);
    return v;
  }
  public void setrotate(float angle, Vector3 other) {
    Vector3 v = new Vector3();
    Vector3 u = other.unitvec();
    angle = (float) Math.toRadians(angle);
    float sint = (float) Math.sin(angle);
    float cost = (float) Math.cos(angle);
    v.x_ = x_*(u.x_*u.x_*(1 - cost) +      cost)  +  y_*(u.x_*u.y_*(1 - cost) - u.z_*sint)  +  z_*(u.x_*u.z_*(1 - cost) + u.y_*sint);
    v.y_ = x_*(u.x_*u.y_*(1 - cost) + u.z_*sint)  +  y_*(u.y_*u.y_*(1 - cost) +      cost)  +  z_*(u.y_*u.z_*(1 - cost) - u.x_*sint);
    v.z_ = x_*(u.x_*u.z_*(1 - cost) - u.y_*sint)  +  y_*(u.y_*u.z_*(1 - cost) + u.x_*sint)  +  z_*(u.z_*u.z_*(1 - cost) +      cost);
    x_ = v.x_;
    y_ = v.y_;
    z_ = v.z_;
  }
  
  // shows the vector values
  public void show() {
    System.out.println("Vector3 " + hashCode() + ": x = " + x_ + ", y = " + y_ + ", z = " + z_ + ", length = " + length());
  }
}
