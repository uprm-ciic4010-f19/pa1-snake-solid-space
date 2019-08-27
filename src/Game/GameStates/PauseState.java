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
public class PauseState extends State {

    private int count = 0;
    private UIManager uiManager;

    public PauseState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);
        
        uiManager.addObjects(new UIImageButton(ScreenRes.width/33, ScreenRes.height/4-ScreenRes.height/43, ScreenRes.width/13, ScreenRes.width/15, Images.Resume, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().gameState);
            Game.GameStates.AudioPlay.stopSound();
            if (!Game.GameStates.OptionsState.soundOff) {
            	Game.GameStates.AudioPlay.playSound(1);
            }
        }));

        uiManager.addObjects(new UIImageButton(ScreenRes.width/33, ScreenRes.height/3, ScreenRes.width/13, ScreenRes.width/15, Images.Options, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().optionsState);
        }));

        uiManager.addObjects(new UIImageButton(ScreenRes.width/33, (ScreenRes.height/3+ScreenRes.height/10), ScreenRes.width/13, ScreenRes.width/15, Images.BTitle, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
            Game.GameStates.AudioPlay.stopSound();
            if (!Game.GameStates.OptionsState.soundOff) {
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
        g.drawImage(Images.Pause,0,0,ScreenRes.width,ScreenRes.height,null);
        uiManager.Render(g);
        

    }
}
