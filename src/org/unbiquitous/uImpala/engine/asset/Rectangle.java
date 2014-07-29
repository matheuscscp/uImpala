package org.unbiquitous.uImpala.engine.asset;

import org.unbiquitous.uImpala.util.Color;
import org.unbiquitous.uImpala.util.math.Point;

public abstract class Rectangle extends Shape {
	protected float width;
	protected float height;
	
	public Rectangle(Point center, Color paint, float width, float height) {
		super(center, paint);
		this.width = width;
		this.height = height;
	}
	
	public float width() {		return width;	}
	public void width(float width) {		this.width = width;	}
	public float height() {	return height;	}
	public void height(float height) {	this.height = height;	}

}
