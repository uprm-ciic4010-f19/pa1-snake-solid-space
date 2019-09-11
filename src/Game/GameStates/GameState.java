package Game.GameStates;

import java.awt.Graphics;

import Game.Entities.Dynamic.Player;
import Game.Entities.Dynamic.PlayerTwo;
import Main.Handler;
import Worlds.WorldBase;
import Worlds.WorldOne;


/**
 * Created by AlexVR on 7/1/2018.
 */
public class GameState extends State {

    private WorldBase world;
    
    //Sets where the playing area will be located
    public static int boardLocationStartX = 24;
    public static int boardLocationEndX = boardLocationStartX + 60;

    public GameState(Handler handler){
        super(handler);
        world = new WorldOne(handler);
        handler.setWorld(world);
        handler.getWorld().player= new Player(handler);
        handler.getWorld().player2= new PlayerTwo(handler);
        for (int i = boardLocationStartX; i < boardLocationEndX; i++) { //handler.getWorld().GridWidthHeightPixelCount
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

                handler.getWorld().playerLocation[i][j]=false;
                handler.getWorld().appleLocation[i][j]=false;
                handler.getWorld().playerLocation2[i][j]=false;
                handler.getWorld().appleLocation2[i][j]=false;

            }
        }
        handler.getWorld().playerLocation[handler.getWorld().player.xCoord][handler.getWorld().player.yCoord] =true;
        handler.getWorld().playerLocation[handler.getWorld().player2.xCoord2][handler.getWorld().player2.yCoord2] =true;


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
