package org.unbiquitous.uImpala.util.math;
import static java.lang.String.format;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Point implements Cloneable, Serializable{

	public int x,y;

	public Point() {
		this(0,0);
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int distanceTo(Point d) {
		return Math.abs(this.x-d.x) + Math.abs(this.y-d.y);
	}
	
	public Point clone(){
		return new Point(x,y);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Point) {
            Point pt = (Point)o;
            return (x == pt.x) && (y == pt.y);
        }
        return super.equals(o);
	}
	
	@Override
	public int hashCode() {
		return x + y;
	}
	
	@Override
	public String toString() {
		return format("(%s,%s)", x,y);
	}
}
