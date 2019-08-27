package Worlds;

import Game.Entities.Dynamic.Player;
import Game.Entities.Dynamic.PlayerTwo;
import Game.Entities.Dynamic.Tail;
import Game.Entities.Dynamic.TailTwo;
import Game.Entities.Static.Apple;
import Main.Handler;
import Main.ScreenRes;
import Resources.Images;

import java.awt.*;

import java.util.LinkedList;


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

	public int GridSize;
	

	public Player player;
	public PlayerTwo player2;

	protected Handler handler;


	public Boolean appleOnBoard;
	protected Apple apple;
	public Boolean[][] appleLocation;
	public Boolean[][] appleLocation2;


	public Boolean[][] playerLocation;
	public Boolean[][] playerLocation2;

	public LinkedList<Tail> body = new LinkedList<>();
	public LinkedList<TailTwo> body2 = new LinkedList<>();


	public WorldBase(Handler handler){
		this.handler = handler;

		appleOnBoard = false;


	}
	public void tick(){



	}


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
		g.fillRect(GridSize, 0, ScreenRes.width, ScreenRes.height);

		//draw score title image border
		g.setColor(GO);
		g.drawRect(GridSize + (ScreenRes.width/17), 10, ScreenRes.width/5, ScreenRes.height/18);


		//crt effect
		g.drawImage(Images.crt,0,0,handler.getWidth(),handler.getHeight(),null);

		//dialog
		g.drawImage(Images.dialog, GridSize, ScreenRes.height/2, ScreenRes.width - GridSize - 2, ScreenRes.height/2,null);

		//draw game border
		g.setColor(GO);
		g.drawRect(1, 1, GridSize-3, ScreenRes.height-3);
		g.drawRect(2, 2, GridSize-3, ScreenRes.height-3);
		g.drawRect(3, 3, GridSize-3, ScreenRes.height-3);

		//draw panel border
		g.setColor(GO);
		g.drawRect(GridSize, 1, ScreenRes.width-GridSize-1, ScreenRes.height-2);
		g.drawRect(GridSize, 2, ScreenRes.width-GridSize-2, ScreenRes.height-4);
		g.drawRect(GridSize, 3, ScreenRes.width-GridSize-3, ScreenRes.height-6);

		//score image
		g.drawImage(Images.score,GridSize + (ScreenRes.width/20),10,ScreenRes.height/3,ScreenRes.height/18,null);
		
		
		





	}

}
