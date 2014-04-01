package org.unbiquitous.uImpala.engine.util.shapes;

import java.awt.Color;
import java.awt.Point;

import org.lwjgl.opengl.GL11;

public class SimetricShape extends Shape{
	float radius;
	int sides;
	
	public SimetricShape(Point center, Color paint, float radius, int sides) {
		super(center, paint);
		this.radius = radius;
		this.sides = sides;
	}
	
	public void render(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		paint.bind();
		GL11.glLoadIdentity();
		GL11.glBegin(GL11.GL_POLYGON);
		renderCIrcle();
		GL11.glEnd();
	}

	private void renderCIrcle() {
		for (double i = 0; i < 360; i+=((float)360)/sides) {
			plotVertex(center, radius, i);
		}
	}

	private void plotVertex(Point center, float radius, double degrees) {
		double degInRad = Math.toRadians(degrees);
		double x = center.x + Math.cos(degInRad) * radius;
		double y = center.y + Math.sin(degInRad) * radius;
		GL11.glVertex2d(x,y);
	}
	
	public float radius(){	return radius;	}
}