package org.unbiquitous.uImpala.jse.shapes;

import java.awt.Color;
import java.awt.Point;

public class Hexagon extends SimetricShape{
	float radius;
	
	public Hexagon(Point center, Color paint, float radius) {
		super(center, paint, radius, 6);
		this.radius = radius;
	}
	
}
