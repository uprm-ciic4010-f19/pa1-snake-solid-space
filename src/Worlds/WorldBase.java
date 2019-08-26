package Worlds;

import Game.Entities.Dynamic.Player;
import Game.Entities.Dynamic.Tail;
import Game.Entities.Static.Apple;
import Main.Handler;
import Main.ScreenRes;
import Resources.Images;

import java.awt.*;
import java.util.LinkedList;

import javax.swing.ImageIcon;


/**
 * Created by AlexVR on 7/2/2018.
 */
public abstract class WorldBase {

	//How many pixels are from left to right
    //How many pixels are from top to bottom
    //Must be equal
    public int GridWidthHeightPixelCount;

    //automatically calculated, depends on previous input.
    //The size of each box, the size of each box will be GridPixelsize x GridPixelsize.
    public int GridPixelsize;

    public Player player;

    protected Handler handler;


    public Boolean appleOnBoard;
    protected Apple apple;
    public Boolean[][] appleLocation;


    public Boolean[][] playerLocation;

    public LinkedList<Tail> body = new LinkedList<>();


    public WorldBase(Handler handler){
        this.handler = handler;

        appleOnBoard = false;


    }
    public void tick(){



    }
    
    private ImageIcon titleImage;
    
    public void render(Graphics g){
    	

        for (int i = 0; i <= ScreenRes.width; i = i + GridPixelsize){
        	//if (i == 1080) {
        		//Color PURPLE = new Color(102,0,153);
                //g.setColor(Color.BLACK);
                //g.drawLine(0, i, handler.getWidth() , i);
                //g.drawLine(i,0,i,handler.getHeight());
        	//}


        }
        
       Color GO = new Color(0,171,102);  
       //draw background for the score panel
       g.setColor(Color.BLACK);
       g.fillRect(1020, 0, ScreenRes.width, ScreenRes.height);
       
       //draw title image border
       g.setColor(GO);
       g.drawRect(1080, 10, 350, 55);
        
       //draw game border
       g.setColor(Color.BLACK);
       g.drawRect(0, 0, 1020, ScreenRes.height);
       
       g.setColor(Color.BLACK);
       g.drawRect(1, 1, 1020, ScreenRes.height);
       
       g.setColor(Color.BLACK);
       g.drawRect(2, 2, 1020, ScreenRes.height);
        
       //draw title image
       g.drawImage(Images.crt,0,0,handler.getWidth(),handler.getHeight(),null);
       
       //dialog
       g.drawImage(Images.dialog, 1020, 600, 464, 464,null);
       
       //draw panel border
       g.setColor(GO);
       g.drawRect(1020, 0, 464, ScreenRes.height);
       
       //score image
       g.drawImage(Images.score,1080,10,355,55,null);
       
        
        



    }

}
