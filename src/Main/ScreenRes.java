package Main;

import java.awt.Dimension;
import java.awt.Toolkit;

public class ScreenRes {
	
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int downscale = 2;
	public static int height = ((int)screenSize.getHeight() - 60)/downscale;
	public static int width = (1020 + ((int)screenSize.getWidth() - 60)/4)/downscale;

}
