package Game.GameStates;

import Game.Entities.Dynamic.Player;
import Main.Handler;
import Worlds.WorldBase;
import Worlds.WorldOne;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 * Created by AlexVR on 7/1/2018.
 */
public class GameState extends State {

    private WorldBase world;

    public GameState(Handler handler){
        super(handler);
        world = new WorldOne(handler);
        handler.setWorld(world);
        handler.getWorld().player= new Player(handler);
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

                handler.getWorld().playerLocation[i][j]=false;
                handler.getWorld().appleLocation[i][j]=false;

            }
        }
        handler.getWorld().playerLocation[handler.getWorld().player.xCoord][handler.getWorld().player.yCoord] =true;


    }
    
    

    @Override
    public void tick() {

        handler.getWorld().tick();

    }
    
    int count = 0;
    
    @Override
    public void render(Graphics g) {
    	

        handler.getWorld().render(g);

    }

}
