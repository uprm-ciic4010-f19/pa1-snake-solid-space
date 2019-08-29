package Main;

import java.awt.Dimension;
import java.awt.Toolkit;

public class ScreenRes {
	
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static int downscale = 1;
	
	public static int GridWidthHeightPixelCount = 60;
	
	public static int height = ((int)screenSize.getHeight() - GridWidthHeightPixelCount*2)/downscale;
	public static int GridPixelsize = (height/GridWidthHeightPixelCount);
	public static int GridSize = GridPixelsize*GridWidthHeightPixelCount;
	public static int width = (GridSize + ((int)screenSize.getWidth() - GridWidthHeightPixelCount)/(4*downscale));
	
	
	
	

}
