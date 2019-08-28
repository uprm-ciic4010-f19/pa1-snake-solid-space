package Game.GameStates;


import Main.Handler;
import Main.ScreenRes;
import Resources.Images;
import UI.ClickListlener;
import UI.UIImageButton;
import UI.UIManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class MenuState extends State {

	private UIManager uiManager;

	public MenuState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUimanager(uiManager);



		/*uiManager.addObjects(new UIImageButton(handler.getWidth()/2-(64/ScreenRes.downscale), handler.getHeight()-(400/ScreenRes.downscale), 140/ScreenRes.downscale, 50/ScreenRes.downscale, Images.butstart, new ClickListlener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUimanager(null);
                handler.getGame().reStart();
                Game.GameStates.AudioPlay.stopSound();
                if (!Game.GameStates.OptionsState.soundOff) {
                	Game.GameStates.AudioPlay.playSound(1);
                }
                State.setState(handler.getGame().gameState);
            }
        }));*/

		uiManager.addObjects(new UIImageButton(0, 0, ScreenRes.width, ScreenRes.height, Images.titleOverlay, () -> {
			handler.getMouseManager().setUimanager(null);
			handler.getGame().reStart();
			Game.GameStates.AudioPlay.stopSound();
			if (!Game.GameStates.OptionsState.soundOff) {
				Game.GameStates.AudioPlay.playSound(1);
			}
			State.setState(handler.getGame().gameState);
	}));
		System.out.println(ScreenRes.width);

		/*uiManager.addObjects(new UIImageButton(ScreenRes.width/33, ScreenRes.height/4-ScreenRes.height/43, ScreenRes.width/13, ScreenRes.width/15, Images.Resume, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().gameState);
            Game.GameStates.AudioPlay.stopSound();
            if (!Game.GameStates.OptionsState.soundOff) {
            	Game.GameStates.AudioPlay.playSound(1);
            }*/

		/*uiManager.addObjects(new UIImageButton(handler.getWidth()/2-(64/ScreenRes.downscale), handler.getHeight()-(180/ScreenRes.downscale), 140/ScreenRes.downscale, 50/ScreenRes.downscale, Images.Options, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().optionsState);
            Game.GameStates.AudioPlay.stopSound();
            Game.GameStates.AudioPlay.playSound(0);
        }));*/
}

@Override
public void tick() {
	handler.getMouseManager().setUimanager(uiManager);
	uiManager.tick();

}

@Override
public void render(Graphics g) {
	g.setColor(Color.darkGray);
	g.fillRect(0,0,handler.getWidth(),handler.getHeight());
	g.drawImage(Images.title,0,0,handler.getWidth(),handler.getHeight(),null);
	g.drawImage(Images.titleAni, 0-(71/ScreenRes.downscale), 0-(136/ScreenRes.downscale), handler.getWidth()+(71/ScreenRes.downscale)*2, handler.getHeight()+(136/ScreenRes.downscale)*2, null);
	uiManager.Render(g);

}


}
