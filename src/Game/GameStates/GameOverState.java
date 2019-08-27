package Game.GameStates;

import Main.Handler;
import Main.ScreenRes;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

import java.awt.*;

public class GameOverState extends State {

	private int count = 0;
	private UIManager uiManager;

	public GameOverState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUimanager(uiManager);
		

		uiManager.addObjects(new UIImageButton(handler.getWidth()/2-(-52/ScreenRes.downscale), handler.getHeight()-(548/ScreenRes.downscale), 90/ScreenRes.downscale, 15/ScreenRes.downscale, Images.gTitle, () -> {
			handler.getMouseManager().setUimanager(null);
			State.setState(handler.getGame().menuState);
            Game.GameStates.AudioPlay.stopSound();
            if (!Game.GameStates.OptionsState.soundOff) {
            	Game.GameStates.AudioPlay.playSound(0);
            }
		}));
		
		uiManager.addObjects(new UIImageButton(handler.getWidth()/2-(130/ScreenRes.downscale), handler.getHeight()-(548/ScreenRes.downscale), 90/ScreenRes.downscale, 15/ScreenRes.downscale, Images.Continue, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().gameState);
            Game.GameStates.AudioPlay.stopSound();
            if (!Game.GameStates.OptionsState.soundOff) {
            	Game.GameStates.AudioPlay.playSound(1);
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
		handler.getGame().reStart();
		g.drawImage(Images.gameOver,0,0,ScreenRes.width,ScreenRes.height,null);
		uiManager.Render(g);

	}

}
