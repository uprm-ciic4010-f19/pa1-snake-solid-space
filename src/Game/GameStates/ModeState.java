package Game.GameStates;

import Main.Handler;
import Main.ScreenRes;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

import java.awt.*;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class ModeState extends State{

	private int count = 0;
	private UIManager uiManager;
	public static Boolean  singlePlayerMode;
	public static Boolean  optionMode = true;

	public ModeState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUimanager(uiManager);

		uiManager.addObjects(new UIImageButton(handler.getWidth()/10+(640/ScreenRes.downscale), handler.getHeight()/2-handler.getHeight()/13 +(75/ScreenRes.downscale), handler.getWidth()/8, handler.getWidth()/15, Images.singlePlayer, () -> {
			singlePlayerMode = true;
			handler.getMouseManager().setUimanager(null);
			handler.getGame().reStart();
			Resources.Soundtrack.stopSound();
			if (!Game.GameStates.OptionsState.soundOff) {
				Resources.Soundtrack.playSound(1);
			}
			State.setState(handler.getGame().gameState);
		}));


		uiManager.addObjects(new UIImageButton(handler.getWidth()/10+(690/ScreenRes.downscale), handler.getHeight()/2-handler.getHeight()/13 +(205/ScreenRes.downscale), handler.getWidth()/14, handler.getWidth()/15, Images.versus, () -> {
			singlePlayerMode = false;
			handler.getMouseManager().setUimanager(null);
			handler.getGame().reStart();
			Resources.Soundtrack.stopSound();
			if (!Game.GameStates.OptionsState.soundOff) {
				Resources.Soundtrack.playSound(1);
			}
			State.setState(handler.getGame().gameState);
		}));

		
        uiManager.addObjects(new UIImageButton(handler.getWidth()/10+(690/ScreenRes.downscale), handler.getHeight()/2-handler.getHeight()/13 +(335/ScreenRes.downscale), handler.getWidth()/14, handler.getWidth()/15, Images.titleOptions, () -> {
            optionMode = true;
        	handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().optionsState);
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
		g.drawImage(Images.titleAni, 0-(71/ScreenRes.downscale), 0, handler.getWidth()+(71/ScreenRes.downscale)*2, handler.getHeight(), null);
		g.drawImage(Images.titleFix, 0-(71/ScreenRes.downscale), 0, handler.getWidth()+(71/ScreenRes.downscale)*2, handler.getHeight(), null);
		uiManager.Render(g);


	}
}
