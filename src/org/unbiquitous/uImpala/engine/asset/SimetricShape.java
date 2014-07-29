package org.unbiquitous.uImpala.engine.asset;

import org.unbiquitous.uImpala.util.Color;
import org.unbiquitous.uImpala.util.math.Point;

public abstract class SimetricShape extends Shape {

	protected float radius;
	protected int sides;

	public SimetricShape(Point center, Color paint, float radius, int sides) {
		super(center, paint);
		this.radius = radius;
		this.sides = sides;
	}
	
	public float radius(){	return radius;	}
	public void radius(int radius){	this.radius = radius ;	}
	public int sides(){	return sides;	}
	public void sides(int sides){	this.sides = sides ;	}

}
