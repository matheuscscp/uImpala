package org.unbiquitous.uImpala.jse.shapes;

import java.awt.Color;
import java.awt.Point;

import org.lwjgl.opengl.GL11;

public class Rectangle extends Shape {
	private final float width;
	private final float height;
	
	public Rectangle(Point center, Color paint, float width, float height) {
		super(center, paint);
		this.width = width;
		this.height = height;
	}

	public void render(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		paint.bind();
		GL11.glLoadIdentity();
		GL11.glBegin(GL11.GL_POLYGON);
		GL11.glVertex2d(center.x - width/2,center.y - height/2);
		GL11.glVertex2d(center.x + width/2,center.y - height/2);
		GL11.glVertex2d(center.x + width/2,center.y + height/2);
		GL11.glVertex2d(center.x - width/2,center.y + height/2);
		
		GL11.glEnd();
	}
	
	public float width() {		return width;	}
	public float height() {	return height;	}
}
