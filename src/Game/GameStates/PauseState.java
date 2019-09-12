package Game.GameStates;

import java.awt.Graphics;

import Main.Handler;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

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
        
        uiManager.addObjects(new UIImageButton(handler.getWidth()/33, handler.getHeight()/4-handler.getHeight()/43, handler.getWidth()/13, handler.getWidth()/15, Images.Resume, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().gameState);
            Resources.Soundtrack.stopSound();
            if (!Game.GameStates.OptionsState.soundOff) {
            	Resources.Soundtrack.playSound(1);
            }
        }));

        uiManager.addObjects(new UIImageButton(handler.getWidth()/33, handler.getHeight()/3, handler.getWidth()/13, handler.getWidth()/15, Images.Options, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().optionsState);
        }));

        uiManager.addObjects(new UIImageButton(handler.getWidth()/33, (handler.getHeight()/3+handler.getHeight()/10), handler.getWidth()/13, handler.getWidth()/15, Images.BTitle, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
            Resources.Soundtrack.stopSound();
            if (!Game.GameStates.OptionsState.soundOff) {
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
        g.drawImage(Images.Pause,0,0,handler.getWidth(),handler.getHeight(),null);
        uiManager.Render(g);
        

    }
}
