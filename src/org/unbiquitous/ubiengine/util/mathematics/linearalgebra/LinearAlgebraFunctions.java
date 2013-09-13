package org.unbiquitous.ubiengine.util.mathematics.linearalgebra;

/**
 * Class to implement some Linear Algebra functions.
 * 
 * @author Matheus
 */
public class LinearAlgebraFunctions {
  /**
   * Calculates the determinant of a two-dimensional matrix.
   * 
   * @param a First column vector to represent the matrix.
   * @param b Second column vector to represent the matrix.
   * @return Determinant of two-dimensional matrix.
   */
  public static float det2(Vector3 a, Vector3 b) {
    return a.x()*b.y() - a.y()*b.x();
  }
  
  /**
   * Calculates the determinant of a three-dimensional matrix.
   * 
   * @param a First column vector to represent the matrix.
   * @param b Second column vector to represent the matrix.
   * @param c Third column vector to represent the matrix.
   * @return Determinant of three-dimensional matrix.
   */
  public static float det3(Vector3 a, Vector3 b, Vector3 c) {
    return
        a.x()*b.y()*c.z() + b.x()*c.y()*a.z() + c.x()*a.y()*b.z()
      - a.z()*b.y()*c.x() - b.z()*c.y()*a.x() - c.z()*a.y()*b.x()
    ;
  }
  
  /**
   * Solves a two-dimensional linear system.
   * 
   * @param a First column vector to represent the matrix of the linear system.
   * @param b Second column vector to represent the matrix of the linear system.
   * @param c Column vector to represent the constants of the linear system.
   * @param ret Array that must have at least two elements to return the solutions.
   * @return Returns true if the linear system has solutions.
   */
  public static boolean linearsystem2(Vector3 a, Vector3 b, Vector3 c, float[] ret) {
    float D = det2(a, b);
    if (D == 0) {
      ret[0] = 0;
      ret[1] = 0;
      return false;
    }
    
    float Dx = det2(c, b);
    float Dy = det2(a, c);
    
    ret[0] = Dx/D;
    ret[1] = Dy/D;
    
    return true;
  }
  
  /**
   * Solves a three-dimensional linear system.
   * 
   * @param a First column vector to represent the matrix of the linear system.
   * @param b Second column vector to represent the matrix of the linear system.
   * @param c Third column vector to represent the matrix of the linear system.
   * @param d Column vector to represent the constants of the linear system.
   * @param ret Array that must have at least three elements to return the solutions.
   * @return Returns true if the linear system has solutions.
   */
  public static boolean linearsystem3(Vector3 a, Vector3 b, Vector3 c, Vector3 d, float[] ret) {
    float D = det3(a, b, c);
    if (D == 0) {
      ret[0] = 0;
      ret[1] = 0;
      ret[2] = 0;
      return false;
    }
    
    float Dx = det3(d, b, c);
    float Dy = det3(a, d, c);
    float Dz = det3(a, b, d);
    
    ret[0] = Dx/D;
    ret[1] = Dy/D;
    ret[2] = Dz/D;
    
    return true;
  }
}
