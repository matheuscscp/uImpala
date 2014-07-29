package org.unbiquitous.uImpala.util;

/**
 * A color.
 * 
 * @author Pimenta
 * 
 */
public class Color {
	public static final Color BLACK = new Color(0.0f, 0.0f, 0.0f);
	public static final Color GRAY = new Color(0.5f, 0.5f, 0.5f);
	public static final Color DARK_GRAY = GRAY.darker();
	public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f);
	public static final Color RED = new Color(1.0f, 0.0f, 0.0f);
	public static final Color GREEN = new Color(0.0f, 1.0f, 0.0f);
	public static final Color BLUE = new Color(0.0f, 0.0f, 1.0f);
	public static final Color CYAN = new Color(0.0f, 1.0f, 1.0f);
	public static final Color MAGENTA = new Color(1.0f, 0.0f, 1.0f);
	public static final Color YELLOW = new Color(1.0f, 1.0f, 0.0f);

	public float red, green, blue, alpha;

	public Color(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = 1f;
	}

	public Color(int red, int green, int blue) {
		this.red = ((float)red)/255f;
		this.green = ((float)green)/255f;
		this.blue = ((float)blue)/255f;
		this.alpha = 1;
	}
	
	public Color(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public Color(int red, int green, int blue, int alpha) {
		this.red = ((float)red)/255f;
		this.green = ((float)green)/255f;
		this.blue = ((float)blue)/255f;
		this.alpha = ((float)alpha)/255f;
	}

	public float getRed() {
		return red;
	}

	public float getGreen() {
		return green;
	}

	public float getBlue() {
		return blue;
	}

	public float getAlpha() {
		return alpha;
	}

	private static final double FACTOR = 0.7;

	public Color darker() {
		return new Color(Math.max((float) (getRed() * FACTOR), 0), Math.max(
				(float) (getGreen() * FACTOR), 0), Math.max(
				(float) (getBlue() * FACTOR), 0));
	}

}
