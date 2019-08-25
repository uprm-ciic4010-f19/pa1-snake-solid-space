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

	public static Color appleColorChange() {
		Color colorChange = new Color(redShade1(), greenShade2(), blueShade3());
		return colorChange;
	}

	public static int redShade1() {
		int minColorValue = 200;
		int maxColorValue = 255;
		int redValue = (minColorValue + (int)(Math.random() * ((maxColorValue - minColorValue) + 1)));
		return redValue;
	}

	public static int greenShade2() {
		int minColorValue = 0;
		int maxColorValue = 100;
		int greenValue = (minColorValue + (int)(Math.random() * ((maxColorValue - minColorValue) + 1)));
		return greenValue;
	}

	public static int blueShade3() {
		int minColorValue = 0;
		int maxColorValue = 50;
		int blueValue = (minColorValue + (int)(Math.random() * ((maxColorValue - minColorValue) + 1)));
		return blueValue;
	}
	
	public static Color badAppleColorChange() {
		Color colorChange = new Color(redShadeBad(), greenShadeBad(), blueShadeBad());
		return colorChange;
	}

	public static int redShadeBad() {
		int minColorValue = 200;
		int maxColorValue = 255;
		int redValue = (minColorValue + (int)(Math.random() * ((maxColorValue - minColorValue) + 1)));
		return redValue;
	}

	public static int greenShadeBad() {
		int minColorValue = 0;
		int maxColorValue = 50;
		int greenValue = (minColorValue + (int)(Math.random() * ((maxColorValue - minColorValue) + 1)));
		return greenValue;
	}

	public static int blueShadeBad() {
		int minColorValue = 240;
		int maxColorValue = 255;
		int blueValue = (minColorValue + (int)(Math.random() * ((maxColorValue - minColorValue) + 1)));
		return blueValue;
	}

}
