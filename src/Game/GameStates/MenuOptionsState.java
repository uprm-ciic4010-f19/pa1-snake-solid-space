package Game.GameStates;

import java.awt.Color;
import java.awt.Graphics;

import Main.Handler;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class MenuOptionsState extends State {

	private int count = 0;
	private UIManager uiManager;

	public MenuOptionsState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUimanager(uiManager);

		uiManager.addObjects(new UIImageButton(handler.getWidth()/33, handler.getHeight()/4-handler.getHeight()/43, handler.getWidth()/13, handler.getWidth()/15, Images.Back, () -> {
			handler.getMouseManager().setUimanager(null);
			Resources.Soundtrack.stopSound();
			if (!Resources.Soundtrack.getSoundOff()) {
				Resources.Soundtrack.playSound(0);
			}
			State.setState(handler.getGame().modeState);
		}));

		uiManager.addObjects(new UIImageButton(handler.getWidth()/8, handler.getHeight()/2, handler.getWidth()/13, handler.getWidth()/15, Images.On, () -> {
			handler.getMouseManager().setUimanager(null);
			Resources.Soundtrack.stopSound();
			Resources.Soundtrack.playSound(2);
			Resources.Soundtrack.setSoundOff(false);
		}));

		uiManager.addObjects(new UIImageButton(handler.getWidth()/5, handler.getHeight()/2, handler.getWidth()/13, handler.getWidth()/15, Images.Off, () -> {
			handler.getMouseManager().setUimanager(null);
			Resources.Soundtrack.stopSound();
			Resources.Soundtrack.setSoundOff(true);
		}));

		uiManager.addObjects(new UIImageButton(handler.getWidth()/33, handler.getHeight()/3, handler.getWidth()/13, handler.getWidth()/15, Images.BTitle, () -> {
			handler.getMouseManager().setUimanager(null);
			State.setState(handler.getGame().menuState);
			Resources.Soundtrack.stopSound();
			if (!Resources.Soundtrack.getSoundOff()) {
				Resources.Soundtrack.playSound(0);
			}
		}));





	}

	@Override
	public void tick() {
		handler.getMouseManager().setUimanager(uiManager);
		uiManager.tick();
		count++;
		if( count>=30){
			count=30;
		}
		if(handler.getKeyManager().pbutt && count>=30){
			count=0;

			State.setState(handler.getGame().gameState);
		}


	}

	@Override
	public void render(Graphics g) {
		//menu background
		g.drawImage(Images.options,0,0,handler.getWidth(),handler.getHeight(),null);
		uiManager.Render(g);

		//hide paused text
		/*g.setColor(Color.BLACK);
        g.fillRect(0,0,handler.getWidth()/2,handler.getHeight()/2);
        uiManager.Render(g);*/

		//options
		g.drawImage(Images.Sound,handler.getWidth()/33, handler.getHeight()/2, handler.getWidth()/13, handler.getWidth()/15,null);
		uiManager.Render(g);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, handler.getHeight()/2-handler.getHeight()/24, handler.getWidth()/3+handler.getWidth()/18, 3);


	}
}