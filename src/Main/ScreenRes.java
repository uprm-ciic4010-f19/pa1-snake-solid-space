package Main;

import java.awt.Dimension;
import java.awt.Toolkit;

public class ScreenRes {
	
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int width = (int)screenSize.getWidth() - 60;
	public static int height = (int)screenSize.getHeight() - 60;

}
