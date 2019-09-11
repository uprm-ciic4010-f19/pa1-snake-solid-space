package Game.GameStates;


import java.awt.Color;
import java.awt.Graphics;

import Main.Handler;
import Main.ScreenRes;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class MenuState extends State {

	private UIManager uiManager;

	public MenuState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUimanager(uiManager);
		
		//Renders transparent button over the whole screen
		uiManager.addObjects(new UIImageButton(0, 0, handler.getWidth(), handler.getHeight(), Images.titleOverlay, () -> {
			handler.getMouseManager().setUimanager(null);
			handler.getGame().reStart();
			State.setState(handler.getGame().modeState);
	}));


}

@Override
public void tick() {
	handler.getMouseManager().setUimanager(uiManager);
	uiManager.tick();

}

@Override
public void render(Graphics g) {
	//Renders title menu animation
	g.setColor(Color.darkGray);
	g.fillRect(0,0,handler.getWidth(),handler.getHeight());
	g.drawImage(Images.title,0,0,handler.getWidth(),handler.getHeight(),null);
	g.drawImage(Images.titleAni, 0-(71/ScreenRes.downscale), 0, handler.getWidth()+(71/ScreenRes.downscale)*2, handler.getHeight(), null);
	uiManager.Render(g);

}


}
