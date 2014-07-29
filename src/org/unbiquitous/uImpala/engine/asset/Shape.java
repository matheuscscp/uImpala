package org.unbiquitous.uImpala.engine.asset;

import org.unbiquitous.uImpala.util.Color;
import org.unbiquitous.uImpala.util.math.Point;

public abstract class Shape {
	protected Point center;
	protected Color color;
	
	public Shape(Point center, Color paint) {
		center(center);
		color(paint);
	}
	
	public abstract void render();
	public abstract void rotate(float angleInDegrees);
	
	public Color color(){ return color;}
	public void color(Color color){this.color = color;}
	public Point center(){	return (Point) center.clone();	}
	public void center(Point center){	this.center =  (Point) center.clone();	}
}
