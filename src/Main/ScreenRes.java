package Main;

import java.awt.Dimension;
import java.awt.Toolkit;

public class ScreenRes {
	
	//Gets the user's screen resolution so the game is always rendered at an acceptable size.
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	//Downscales the game so it runs in a smaller windows.
	public static int downscale = 2;
	
	public static int GridWidthHeightPixelCount = 60;
	
	public static int height = (int)screenSize.getHeight()/downscale;
	public static int GridPixelsize = (height/GridWidthHeightPixelCount);
	public static int GridSize = GridPixelsize*GridWidthHeightPixelCount;
	public static int width = (int)screenSize.getWidth()/downscale;
	
	
	
	

}
