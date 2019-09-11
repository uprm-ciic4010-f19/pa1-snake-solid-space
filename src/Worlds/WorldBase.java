package Worlds;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import Game.Entities.Dynamic.Player;
import Game.Entities.Dynamic.PlayerTwo;
import Game.Entities.Dynamic.Tail;
import Game.Entities.Dynamic.TailTwo;
import Game.Entities.Static.Apple;
import Game.GameStates.GameState;
import Main.Handler;
import Resources.Images;


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


		/*for (int i = 0; i <= handler.getWidth(); i = i + GridPixelsize){
			//if (i == 1080) {
			//Color PURPLE = new Color(102,0,153);
			//g.setColor(Color.BLACK);
			//g.drawLine(0, i, handler.getWidth() , i);
			//g.drawLine(i,0,i,handler.getHeight());
			//}


		}*/
		Color GO = new Color(0,171,102);

		// -------------------------left panel---------------------- //

		//draw background for the score panel
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GridPixelsize*GameState.boardLocationStartX, handler.getHeight());

		//draw score header border
		g.setColor(GO);
		g.drawRect(0, 0, GridPixelsize*GameState.boardLocationStartX-2, handler.getHeight()/15);

		//player one picture
		g.drawImage(Images.playOne, 0, handler.getHeight()/2, GridPixelsize*GameState.boardLocationStartX-2, handler.getHeight()/2,null);

		//draw panel border
		g.setColor(GO);
		g.drawRect(0, 1, GridPixelsize*GameState.boardLocationStartX-1, handler.getHeight()-2);
		g.drawRect(0, 2, GridPixelsize*GameState.boardLocationStartX-2, handler.getHeight()-4);
		g.drawRect(0, 3, GridPixelsize*GameState.boardLocationStartX-3, handler.getHeight()-6);

		//score image
		g.drawImage(Images.score,0 + (GridPixelsize*GameState.boardLocationStartX)/10,10,handler.getHeight()/3,handler.getHeight()/16,null);
		g.drawImage(Images.playerOneScore,0 + (GridPixelsize*GameState.boardLocationStartX)/13,handler.getHeight()/2-handler.getHeight()/20,handler.getHeight()/3,handler.getHeight()/18,null);

		//---------------------right panel---------------------------//


		if(!Game.GameStates.ModeState.singlePlayerMode) {
			//draw background for the score panel
			g.setColor(Color.BLACK);
			g.fillRect(GridPixelsize*GameState.boardLocationEndX, 0, handler.getWidth(), handler.getHeight());

			//draw score title image border
			g.setColor(GO);
			g.drawRect(GridPixelsize*GameState.boardLocationEndX+1, 0, GridPixelsize*GameState.boardLocationStartX, handler.getHeight()/15);

			//player two picture
			g.drawImage(Images.playerTwo, GridPixelsize*GameState.boardLocationEndX, handler.getHeight()/2, handler.getWidth() - GridPixelsize*GameState.boardLocationEndX - 2, handler.getHeight()/2,null);
		
			//score image
			g.drawImage(Images.score,GridPixelsize*GameState.boardLocationEndX + (GridPixelsize*GameState.boardLocationStartX)/10,10,handler.getHeight()/3,handler.getHeight()/16,null);
			g.drawImage(Images.playerTwoScore,GridPixelsize*GameState.boardLocationEndX + (GridPixelsize*GameState.boardLocationStartX)/15,handler.getHeight()/2-handler.getHeight()/20,handler.getHeight()/3,handler.getHeight()/18,null);

		}else {
			g.drawImage(Images.noiseTv, GridPixelsize*GameState.boardLocationEndX, 0, handler.getWidth() - GridPixelsize*GameState.boardLocationEndX - 2, handler.getHeight(),null);
		}
		//draw game border
		g.setColor(GO);
		g.drawRect(GridPixelsize*GameState.boardLocationStartX-1, 1, GridPixelsize*GridSize-3, handler.getHeight()-3);
		g.drawRect(GridPixelsize*GameState.boardLocationStartX-1, 2, GridPixelsize*GridSize-3, handler.getHeight()-3);
		g.drawRect(GridPixelsize*GameState.boardLocationStartX-1, 3, GridPixelsize*GridSize-3, handler.getHeight()-3);
		
		//draw panel border
		g.setColor(GO);
		g.drawRect(GridPixelsize*GameState.boardLocationEndX+1, 1, GridPixelsize*GameState.boardLocationStartX-15, handler.getHeight()-2);
		g.drawRect(GridPixelsize*GameState.boardLocationEndX+2, 2, GridPixelsize*GameState.boardLocationStartX-16, handler.getHeight()-4);
		g.drawRect(GridPixelsize*GameState.boardLocationEndX+3, 3, GridPixelsize*GameState.boardLocationStartX-17, handler.getHeight()-6);

		//crt effect
		g.drawImage(Images.crt,0,0,handler.getWidth(),handler.getHeight(),null);








	}

}
