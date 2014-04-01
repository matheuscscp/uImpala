package org.unbiquitous.uImpala.engine.util.shapes;

import java.awt.Color;
import java.awt.Point;

public class Circle extends SimetricShape{
	float radius;
	
	public Circle(Point center, Color paint, float radius) {
		super(center, paint, radius, 360);
	}
}
