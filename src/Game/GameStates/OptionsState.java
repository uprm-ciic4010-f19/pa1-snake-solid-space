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
public class OptionsState extends State {

    private int count = 0;
    public static Boolean soundOff = false;
    private UIManager uiManager;

    public OptionsState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);
        
        if(ModeState.optionMode) {
        	uiManager.addObjects(new UIImageButton(ScreenRes.width/33, ScreenRes.height/4-ScreenRes.height/43, ScreenRes.width/13, ScreenRes.width/15, Images.Resume, () -> {
    			handler.getMouseManager().setUimanager(null);
				Game.GameStates.AudioPlay.stopSound();
				if (!Game.GameStates.OptionsState.soundOff) {
					Game.GameStates.AudioPlay.playSound(0);
				}
    			State.setState(handler.getGame().modeState);
    			ModeState.optionMode = false;
			}));
        }else {
        uiManager.addObjects(new UIImageButton(ScreenRes.width/33, ScreenRes.height/4-ScreenRes.height/43, ScreenRes.width/13, ScreenRes.width/15, Images.Resume, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().gameState);
            Game.GameStates.AudioPlay.stopSound();
            if (!soundOff) {
            	Game.GameStates.AudioPlay.playSound(1);
            }
        }));
        }
        
        uiManager.addObjects(new UIImageButton(ScreenRes.width/8, ScreenRes.height/2, ScreenRes.width/13, ScreenRes.width/15, Images.On, () -> {
            handler.getMouseManager().setUimanager(null);
            Game.GameStates.AudioPlay.stopSound();
            Game.GameStates.AudioPlay.playSound(2);
            soundOff = false;
        }));
        
        uiManager.addObjects(new UIImageButton(ScreenRes.width/5, ScreenRes.height/2, ScreenRes.width/13, ScreenRes.width/15, Images.Off, () -> {
        	handler.getMouseManager().setUimanager(null);
        	Game.GameStates.AudioPlay.stopSound();
        	soundOff = true;
        }));

        /*uiManager.addObjects(new UIImageButton(ScreenRes.width/33, ScreenRes.height/3, ScreenRes.width/13, ScreenRes.width/15, Images.Options, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
            Game.GameStates.AudioPlay.stopSound();
            Game.GameStates.AudioPlay.playSound(0);
        }));*/

        uiManager.addObjects(new UIImageButton(ScreenRes.width/33, ScreenRes.height/3, ScreenRes.width/13, ScreenRes.width/15, Images.BTitle, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
            Game.GameStates.AudioPlay.stopSound();
            if (!soundOff) {
            	Game.GameStates.AudioPlay.playSound(0);
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
    	g.drawImage(Images.options,0,0,ScreenRes.width,ScreenRes.height,null);
        uiManager.Render(g);
        
        //hide paused text
        /*g.setColor(Color.BLACK);
        g.fillRect(0,0,ScreenRes.width/2,ScreenRes.height/2);
        uiManager.Render(g);*/
        
        //options
        g.drawImage(Images.Sound,ScreenRes.width/33, ScreenRes.height/2, ScreenRes.width/13, ScreenRes.width/15,null);
        uiManager.Render(g);
        
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, ScreenRes.height/2-ScreenRes.height/24, ScreenRes.width/3+ScreenRes.width/18, 3);
        

    }
}