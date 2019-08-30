package Worlds;

import Game.Entities.Static.Apple;
import Game.GameStates.GameState;
import Main.Handler;
import Main.ScreenRes;

import java.awt.*;
import java.util.Random;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class WorldOne extends WorldBase{

    public WorldOne (Handler handler) {
        super(handler);

        //has to be a number bigger than 20 and even
        GridWidthHeightPixelCount = ScreenRes.GridWidthHeightPixelCount;
        
        GridPixelsize = ScreenRes.GridPixelsize;
        GridSize = ScreenRes.GridSize;
        playerLocation = new Boolean[GameState.boardLocationEndX][GridWidthHeightPixelCount];
        appleLocation = new Boolean[GameState.boardLocationEndX][GridWidthHeightPixelCount];
        playerLocation2 = new Boolean[GameState.boardLocationEndX][GridWidthHeightPixelCount];
        appleLocation2 = new Boolean[GameState.boardLocationEndX][GridWidthHeightPixelCount];

    }

    @Override
    public void tick() {
        super.tick();
        player.tick();
        player2.tick();
        if(!appleOnBoard){
            appleOnBoard=true;
            int appleX = new Random().nextInt(handler.getWorld().GridWidthHeightPixelCount-1) + GameState.boardLocationStartX;
            int appley = new Random().nextInt(handler.getWorld().GridWidthHeightPixelCount-1);

            //change coordinates till one is selected in which the player isnt standing
            boolean goodCoordinates=false;
            do{
                if(!handler.getWorld().playerLocation[appleX][appley] && !handler.getWorld().playerLocation2[appleX][appley]){
                    goodCoordinates=true;
                }
            }while(!goodCoordinates);

            apple = new Apple(handler,appleX,appley);
            appleLocation[appleX][appley]=true;
            appleLocation2[appleX][appley]=true;

        }
    }

    @Override
    public void render(Graphics g){
        super.render(g);
        player.render(g,playerLocation);
        player2.render(g, playerLocation2);
    }

}
