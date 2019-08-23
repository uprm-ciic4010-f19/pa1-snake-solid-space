package Game.Entities.Dynamic;

import java.awt.Color;


public class SnakeColor {

	public static Color colorChange() {
		Color colorChange = new Color(redShade(), greenShade(), blueShade());
		return colorChange;
	}
	
	public static int redShade() {
		int minColorValue = 240;
		int maxColorValue = 255;
		int redValue = (minColorValue + (int)(Math.random() * ((maxColorValue - minColorValue) + 1)));
		return redValue;
	}

	public static int greenShade() {
		int minColorValue = 100;
		int maxColorValue = 255;
		int greenValue = (minColorValue + (int)(Math.random() * ((maxColorValue - minColorValue) + 1)));
		return greenValue;
	}
	
	public static int blueShade() {
		int minColorValue = 0;
		int maxColorValue = 200;
		int blueValue = (minColorValue + (int)(Math.random() * ((maxColorValue - minColorValue) + 1)));
		return blueValue;
	}
}
