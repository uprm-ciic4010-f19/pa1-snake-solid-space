package Worlds;

import Game.Entities.Dynamic.Player;
import Game.Entities.Dynamic.Tail;
import Game.Entities.Static.Apple;
import Main.Handler;
import Main.ScreenRes;
import Resources.Images;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.DataLine.Info;
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

	public int GridSize;
	
	private int count;

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
