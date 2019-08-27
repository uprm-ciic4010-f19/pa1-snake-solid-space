package Game.GameStates;


import Main.Handler;
import Main.ScreenRes;
import Resources.Images;
import UI.ClickListlener;
import UI.UIImageButton;
import UI.UIManager;

import java.awt.*;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class MenuState extends State {

    private UIManager uiManager;

    public MenuState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);


        uiManager.addObjects(new UIImageButton(handler.getWidth()/2-(64/ScreenRes.downscale), handler.getHeight()-(250/ScreenRes.downscale), 140/ScreenRes.downscale, 50/ScreenRes.downscale, Images.butstart, new ClickListlener() {
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
        }));
        
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
        uiManager.Render(g);

    }


}
