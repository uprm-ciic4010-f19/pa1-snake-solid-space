package Game.Entities.Dynamic;

import Main.Handler;
import Main.ScreenRes;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.sun.media.jfxmedia.AudioClip;

import javax.sound.sampled.DataLine.Info;

import Game.GameStates.GameState;
import Game.GameStates.PauseState;
import Game.GameStates.State;
import Game.GameStates.GameOverState;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {

	public int lenght;
	public boolean justAte;
	public State pauseState;
	public State gameOverState;
	private Handler handler;
	private int count;

	public int xCoord;
	public int yCoord;

	public int moveCounter;
	public Color colorEatChange; //Changes color when snake eats.
	public Color appleColorChange; //changes the apple color
	public int snakeSpeed; //Snake speed changer debug buttons.
	public double gameScore; //Game score.
	public String direction;//is your first name one?
	public int totalMovement; 
	public int lastStudentIDDigit;

	public Player(Handler handler){
		this.handler = handler;
		lastStudentIDDigit = 7;
		xCoord = 0;
		yCoord = 0;
		count = 0;
		moveCounter = 0;
		direction= "Right";
		justAte = false;
		lenght= 1;

	}

	public void tick(){
		moveCounter++;
		pauseState = new PauseState(handler);
		gameOverState = new GameOverState(handler);
		if(moveCounter>=25-snakeSpeed) {
			checkCollisionAndMove();
			moveCounter=0;
		}
		if((handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)) && (direction!="Down")){
			direction="Up";
		}if((handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)) && (direction!="Up")){
			direction="Down";
		}if((handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)) && (direction!="Right")){
			direction="Left";
		}if((handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)) && (direction!="Left")){
			direction="Right";
		}

		//Menu keys
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
			Game.GameStates.AudioPlay.stopSound();
			if (!Game.GameStates.OptionsState.soundOff) {
            	Game.GameStates.AudioPlay.playSound(2);
            }
			GameState.setState(pauseState);
		}

		//Debug keys
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)){ //Add tail to snake.
			tailDebug();
		}if((handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS))||(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ADD))) { //Increases snake speed.
			snakeSpeed++;
			System.out.println("Debug Speed increased to: "+snakeSpeed);
		}if((handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)||handler.getKeyManager().keyJustPressed(KeyEvent.VK_SUBTRACT))) { //Decreases snake speed.
			snakeSpeed--;
			System.out.println("Debug Speed decreased to: "+snakeSpeed);
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_R)) { //Reset game
			handler.getMouseManager().setUimanager(null);
			handler.getGame().reStart();
			State.setState(handler.getGame().gameState);
			System.out.println("Game reseted.");
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_C)) { //Color change
			colorEatChange = SnakeColor.colorChange();
			System.out.println("Color changed.");
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_K)){ //Insta Game Over
			GameState.setState(gameOverState);
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)){ //Add score debug button
			scoreDebug();
		}
	}

	public void checkCollisionAndMove(){
		handler.getWorld().playerLocation[xCoord][yCoord]=false;
		int x = xCoord;
		int y = yCoord;
		totalMovement++;

		selfCollisionCheck();

		switch (direction){
		case "Left":
			if(xCoord==0){
				//shorten();
				xCoord = handler.getWorld().GridWidthHeightPixelCount-1;
			}else{
				xCoord--;
			}
			break;
		case "Right":
			if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				//shorten();
				xCoord = 0;
			}else{
				xCoord++;
			}
			break;
		case "Up":
			if(yCoord==0){
				//shorten();
				yCoord = handler.getWorld().GridWidthHeightPixelCount-1;
			}else{
				yCoord--;
			}
			break;
		case "Down":
			if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				//shorten();
				yCoord = 0;
			}else{
				yCoord++;
			}
			break;
		}
		handler.getWorld().playerLocation[xCoord][yCoord]=true;


		if(handler.getWorld().appleLocation[xCoord][yCoord]&&totalMovement>150 && gameScore >= 5){
			rottenEat();
		}else {
			if(handler.getWorld().appleLocation[xCoord][yCoord])
				Eat();
		}

		if(!handler.getWorld().body.isEmpty()) {
			handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
			handler.getWorld().body.removeLast();
			handler.getWorld().body.addFirst(new Tail(x, y,handler));
		}

	}

	public void selfCollisionCheck() {
		handler.getWorld().playerLocation[xCoord][yCoord]=false;
		int x = xCoord;
		int y = yCoord;
		for (Tail i: handler.getWorld().body){
			if (i.x == xCoord) {
				if (i.y == yCoord) {
					Game.GameStates.AudioPlay.stopSound();
					if (!Game.GameStates.OptionsState.soundOff) {
	                	Game.GameStates.AudioPlay.playSound(3);
	                }
					GameState.setState(gameOverState);
				}

			}
		}
	}

	public void render(Graphics g,Boolean[][] playeLocation){
		Random r = new Random();
		if(totalMovement==150&&gameScore >= 5)
			appleColorChange = SnakeColor.badAppleColorChange();
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

				if(gameScore >= 0) {
					Color GO = new Color(0,171,102); 
					g.setColor(GO);
					g.setFont(new Font("arial", Font.PLAIN, 60/ScreenRes.downscale));
					g.drawString("" +(int)gameScore, handler.getWorld().GridSize + handler.getWorld().GridPixelsize*5, ScreenRes.height/5);
				}
				if(colorEatChange != null) {
					g.setColor(colorEatChange);
				}else {
					g.setColor(new Color(80, 220, 100));
				}

				if(playeLocation[i][j]){
					g.fillRect((i*handler.getWorld().GridPixelsize),
							(j*handler.getWorld().GridPixelsize),
							handler.getWorld().GridPixelsize,
							handler.getWorld().GridPixelsize);
				}

				if(handler.getWorld().appleLocation[i][j]){
					if(appleColorChange != null) {
						g.setColor(appleColorChange);
					}else {
						g.setColor(new Color(255, 0, 0));
					}
					g.fillRect((i*handler.getWorld().GridPixelsize),
							(j*handler.getWorld().GridPixelsize),
							handler.getWorld().GridPixelsize,
							handler.getWorld().GridPixelsize);
				}

			}
		}


	}

	public void Eat(){
		lenght++;
		totalMovement = 0;
		snakeSpeed += lastStudentIDDigit + 1;
		gameScore += Math.sqrt(2 * gameScore + 1);
		System.out.println("Score: "+gameScore);
		colorEatChange = SnakeColor.colorChange();
		appleColorChange = SnakeColor.appleColorChange();
		Tail tail= null;
		handler.getWorld().appleLocation[xCoord][yCoord]=false;
		handler.getWorld().appleOnBoard=false;
		switch (direction){
		case "Left":
			if( handler.getWorld().body.isEmpty()){
				if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail = new Tail(this.xCoord+1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail = new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail =new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
					}else{
						tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

					}
				}

			}
			break;
		case "Right":
			if( handler.getWorld().body.isEmpty()){
				if(this.xCoord!=0){
					tail=new Tail(this.xCoord-1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail=new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail=new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
					}
				}

			}
			break;
		case "Up":
			if( handler.getWorld().body.isEmpty()){
				if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(this.xCoord,this.yCoord+1,handler));
				}else{
					if(this.xCoord!=0){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					}
				}
			}else{
				if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
					}
				}

			}
			break;
		case "Down":
			if( handler.getWorld().body.isEmpty()){
				if(this.yCoord!=0){
					tail=(new Tail(this.xCoord,this.yCoord-1,handler));
				}else{
					if(this.xCoord!=0){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					} System.out.println("Tu biscochito");
				}
			}else{
				if(handler.getWorld().body.getLast().y!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
					}
				}

			}
			break;
		}
		handler.getWorld().body.addLast(tail);
		handler.getWorld().playerLocation[tail.x][tail.y] = true;


	}

	public void scoreDebug() {
		gameScore += Math.sqrt(2 * gameScore + 1);
		System.out.println("Score: "+gameScore);
	}

	public void tailDebug(){
		lenght++;
		Tail tail= null;
		System.out.println("Length of snake is currently " + lenght + ".");
		handler.getWorld().appleLocation[xCoord][yCoord]=false;
		handler.getWorld().appleOnBoard=true;
		switch (direction){
		case "Left":
			if( handler.getWorld().body.isEmpty()){
				if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail = new Tail(this.xCoord+1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail = new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail =new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
					}else{
						tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

					}
				}

			}
			break;
		case "Right":
			if( handler.getWorld().body.isEmpty()){
				if(this.xCoord!=0){
					tail=new Tail(this.xCoord-1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail=new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail=new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
					}
				}

			}
			break;
		case "Up":
			if( handler.getWorld().body.isEmpty()){
				if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(this.xCoord,this.yCoord+1,handler));
				}else{
					if(this.xCoord!=0){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					}
				}
			}else{
				if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
					}
				}

			}
			break;
		case "Down":
			if( handler.getWorld().body.isEmpty()){
				if(this.yCoord!=0){
					tail=(new Tail(this.xCoord,this.yCoord-1,handler));
				}else{
					if(this.xCoord!=0){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					} System.out.println("Tu biscochito");
				}
			}else{
				if(handler.getWorld().body.getLast().y!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
					}
				}

			}
			break;
		}
		handler.getWorld().body.addLast(tail);
		handler.getWorld().playerLocation[tail.x][tail.y] = true;
	}

	public void rottenEat() {
		snakeSpeed -= (lastStudentIDDigit + 1);
		gameScore -= Math.sqrt(2 * gameScore + 1);
		Eat();
		gameScore -= Math.sqrt(2 * (gameScore) + 1);
	}

	public void shorten(){
		lenght = 0;
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

				handler.getWorld().playerLocation[i][j]=false;

			}
		}
	}

	public boolean isJustAte() {
		return justAte;
	}

	public void setJustAte(boolean justAte) {
		this.justAte = justAte;
	}
}
