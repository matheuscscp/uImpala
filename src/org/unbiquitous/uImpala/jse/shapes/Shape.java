package org.unbiquitous.uImpala.jse.shapes;

import java.awt.Color;
import java.awt.Point;

public class Shape {
	Point center;
	public Shape(Point center, Color paint) {
		center(center);
		this.paint = fromColor(paint);
	}
	org.newdawn.slick.Color paint;
	
	org.newdawn.slick.Color fromColor(Color paint) {
		return new org.newdawn.slick.Color(paint.getRed(), paint.getGreen(), paint.getBlue(), paint.getAlpha());
	}
	
	public Point center(){	return (Point) center.clone();	}
	public void center(Point center){	this.center =  (Point) center.clone();	}
}
