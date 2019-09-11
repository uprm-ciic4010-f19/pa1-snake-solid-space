package Game.Entities.Dynamic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

import Game.Entities.Static.Apple;
import Game.GameStates.GameOverState;
import Game.GameStates.GameState;
import Game.GameStates.PauseState;
import Game.GameStates.State;
import Main.Handler;
import Main.ScreenRes;
import Resources.Images;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {

	public int lenght;
	public boolean justAte;
	public State pauseState;
	public State gameOverState;
	private Handler handler;
	public static Boolean playerOneLost;

	public int xCoord;
	public int yCoord;

	public int moveCounter;
	public Color colorEatChange; //Changes color when snake eats.
	public int snakeSpeedModifier; //Snake speed changer debug buttons.
	public static double gameScore; //Game score.
	public String direction;//is your first name one?
	public static int totalMovement; 
	public int lastStudentIDDigit;
	public int maxSpeed;
	public int additionalSpeed;

	public Player(Handler handler){
		this.handler = handler;
		lastStudentIDDigit = 7;
		xCoord = GameState.boardLocationStartX;
		yCoord = 0;
		moveCounter = 0;
		direction= "Right";
		justAte = false;
		lenght= 1;
		maxSpeed = 8;
		additionalSpeed = 0;

	}

	public void tick(){
		moveCounter++;
		pauseState = new PauseState(handler);
		gameOverState = new GameOverState(handler);
		if(moveCounter>=maxSpeed-snakeSpeedModifier) {
			checkCollisionAndMove();
			if(additionalSpeed >=64) {
				checkCollisionAndMove();
			}
			if(additionalSpeed >=128) {
				checkCollisionAndMove();
			}
			if(additionalSpeed >=256) {
				checkCollisionAndMove();
			}
			if(additionalSpeed >=512) {
				checkCollisionAndMove();
			}

		}
		if((handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)) && (direction!="Down") && (direction!="Up")){
			direction="Up";
			checkCollisionAndMove();
		}if((handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)) && (direction!="Up") && (direction!="Down")){
			direction="Down";
			checkCollisionAndMove();
		}if((handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)) && (direction!="Right") && (direction!="Left")){
			direction="Left";
			checkCollisionAndMove();
		}if((handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)) && (direction!="Left") && (direction!="Right")){
			direction="Right";
			checkCollisionAndMove();
		}

		//Menu keys
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)){
			Game.GameStates.PlayAudio.stopSound();
			if (!Game.GameStates.OptionsState.soundOff) {
				Game.GameStates.PlayAudio.playSound(2);
			}
			GameState.setState(pauseState);
		}

		//Debug keys
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)){ //Add tail to snake.
			tailDebug();
		}if((handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS))||(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ADD))) { //Increases snake speed.
			snakeSpeedModifier+=lastStudentIDDigit+1;
			System.out.println("Debug Speed increased to: "+snakeSpeedModifier);
		}if((handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)||handler.getKeyManager().keyJustPressed(KeyEvent.VK_SUBTRACT))) { //Decreases snake speed.
			snakeSpeedModifier-=lastStudentIDDigit+1;
			System.out.println("Debug Speed decreased to: "+snakeSpeedModifier);
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_R)) { //Reset game
			handler.getMouseManager().setUimanager(null);
			handler.getGame().reStart();
			State.setState(handler.getGame().gameState);
			System.out.println("Game reseted.");
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_C)) { //Color change
			colorEatChange = EntityColor.colorChange();
			System.out.println("Color changed.");
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_K)){ //Insta Game Over
			GameState.setState(gameOverState);
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)){ //Add score debug button
			scoreDebug();
		}
	}

	public void checkCollisionAndMove(){
		moveCounter=0;
		handler.getWorld().playerLocation[xCoord][yCoord]=false;
		int x = xCoord;
		int y = yCoord;
		totalMovement++;

		selfCollisionCheck();
		if(!Game.GameStates.ModeState.singlePlayerMode) {
			playerCollisionCheck();
		}
		switch (direction){
		case "Left":
			if(xCoord==GameState.boardLocationStartX){
				xCoord = GameState.boardLocationEndX-1;
			}else{
				xCoord--;
			}
			break;
		case "Right":
			if(xCoord==GameState.boardLocationEndX-1){
				xCoord = GameState.boardLocationStartX;
			}else{
				xCoord++;
			}
			break;
		case "Up":
			if(yCoord==0){
				yCoord = handler.getWorld().GridWidthHeightPixelCount-1;
			}else{
				yCoord--;
			}
			break;
		case "Down":
			if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				yCoord = 0;
			}else{
				yCoord++;
			}
			break;
		}
		handler.getWorld().playerLocation[xCoord][yCoord]=true;


		if(handler.getWorld().appleLocation[xCoord][yCoord]||handler.getWorld().appleLocation2[xCoord][yCoord]){
			if (Apple.isGood()) {
				eat();
			} else {
				rottenEat();
			}
		}

		if(!handler.getWorld().body.isEmpty()) {
			handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
			handler.getWorld().body.removeLast();
			handler.getWorld().body.addFirst(new Tail(x, y,handler));
		}

	}

	public void selfCollisionCheck() {
		for (Tail i: handler.getWorld().body){
			if (i.x == xCoord && i.y == yCoord) {
				playerOneLost = true;
				Game.GameStates.PlayAudio.stopSound();
				if (!Game.GameStates.OptionsState.soundOff) {
					Game.GameStates.PlayAudio.playSound(3);
				}
				GameState.setState(gameOverState);
			}

		}
	}


	public void playerCollisionCheck() {
		for (TailTwo i: handler.getWorld().body2){
			if (i.x == xCoord && i.y == yCoord) {
				playerOneLost = true;
				Game.GameStates.PlayAudio.stopSound();
				if (!Game.GameStates.OptionsState.soundOff) {
					Game.GameStates.PlayAudio.playSound(3);
				}
				GameState.setState(gameOverState);
			}

		}
	}

	public void render(Graphics g,Boolean[][] playeLocation){
		Random r = new Random();
		for (int i = GameState.boardLocationStartX; i < GameState.boardLocationEndX; i++) {//handler.getWorld().GridWidthHeightPixelCount
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

				if(gameScore >= 0) {
					Color GO = new Color(0,171,102); 
					g.setColor(GO);
					g.setFont(new Font("arial", Font.PLAIN, 60/ScreenRes.downscale));
					g.drawString("" +(int)gameScore, 0 + (ScreenRes.GridPixelsize*GameState.boardLocationStartX)/10, ScreenRes.height/5 + ScreenRes.height/20);
					g.drawString("PTS", (ScreenRes.GridPixelsize*GameState.boardLocationStartX)-(ScreenRes.GridPixelsize*GameState.boardLocationStartX)/3, ScreenRes.height/5 + ScreenRes.height/20);

				}
				if(colorEatChange != null) {
					g.setColor(colorEatChange);
				}else {
					g.setColor(new Color(80, 220, 100));
				}

				if(playeLocation[i][j]){
					g.setColor(Color.BLACK);
					g.drawRoundRect((i * handler.getWorld().GridPixelsize), (j * handler.getWorld().GridPixelsize),handler.getWorld().GridPixelsize, handler.getWorld().GridPixelsize, handler.getWorld().GridPixelsize,handler.getWorld().GridPixelsize);
					if (colorEatChange != null) {
						g.setColor(colorEatChange);
						g.drawRoundRect((i * handler.getWorld().GridPixelsize), (j * handler.getWorld().GridPixelsize),handler.getWorld().GridPixelsize-1, handler.getWorld().GridPixelsize-1, handler.getWorld().GridPixelsize,handler.getWorld().GridPixelsize);
					} else {
						g.setColor(new Color(80, 220, 100));
						g.drawRoundRect((i * handler.getWorld().GridPixelsize), (j * handler.getWorld().GridPixelsize),handler.getWorld().GridPixelsize-1, handler.getWorld().GridPixelsize-1, handler.getWorld().GridPixelsize,handler.getWorld().GridPixelsize);
					}
				}

				if(handler.getWorld().appleLocation[i][j]){
					Apple.isGood();
					if (Apple.isGood()) {
						g.drawImage(Images.apple, (i * handler.getWorld().GridPixelsize)-handler.getWorld().GridPixelsize/2, (j * handler.getWorld().GridPixelsize)-handler.getWorld().GridPixelsize/2,handler.getWorld().GridPixelsize+6, handler.getWorld().GridPixelsize+6, null);
					} else {
						g.drawImage(Images.rottenApple, (i * handler.getWorld().GridPixelsize), (j * handler.getWorld().GridPixelsize),handler.getWorld().GridPixelsize+6, handler.getWorld().GridPixelsize+6, null);
					}

				}

			}
		}


	}

	public void eat(){
		lenght++;
		totalMovement = 0;
		PlayerTwo.totalMovement2 = 0;
		if (snakeSpeedModifier < maxSpeed) {
			snakeSpeedModifier += lastStudentIDDigit + 1;
		}else {
			additionalSpeed += lastStudentIDDigit + 1;
		}


		gameScore += Math.sqrt(2 * gameScore + 1);
		System.out.println("Score: "+gameScore);
		colorEatChange = EntityColor.colorChange();
		Tail tail= null;
		handler.getWorld().appleLocation[xCoord][yCoord]=false;
		handler.getWorld().appleLocation2[xCoord][yCoord] = false;
		handler.getWorld().appleOnBoard = false;

		switch (direction){
		case "Left":
			if( handler.getWorld().body.isEmpty()){
				if(this.xCoord!=GameState.boardLocationEndX-1){
					tail = new Tail(this.xCoord+1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail = new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail =new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=GameState.boardLocationEndX-1){
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
				if(this.xCoord!=GameState.boardLocationStartX){
					tail=new Tail(this.xCoord-1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail=new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail=new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=GameState.boardLocationStartX){
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
					if(this.xCoord!=GameState.boardLocationStartX){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					}
				}
			}else{
				if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=GameState.boardLocationStartX){
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
					if(this.xCoord!=GameState.boardLocationStartX){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					} System.out.println("Tu biscochito");
				}
			}else{
				if(handler.getWorld().body.getLast().y!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=GameState.boardLocationStartX){
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
				if(this.xCoord!=GameState.boardLocationEndX-1){
					tail = new Tail(this.xCoord+1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail = new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail =new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=GameState.boardLocationEndX-1){
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
				if(this.xCoord!=GameState.boardLocationStartX){
					tail=new Tail(this.xCoord-1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail=new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail=new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=GameState.boardLocationStartX){
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
					if(this.xCoord!=GameState.boardLocationStartX){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					}
				}
			}else{
				if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=GameState.boardLocationStartX){
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
					if(this.xCoord!=GameState.boardLocationStartX){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					} System.out.println("Tu biscochito");
				}
			}else{
				if(handler.getWorld().body.getLast().y!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=GameState.boardLocationStartX){
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
		snakeSpeedModifier -= (lastStudentIDDigit + 1);
		gameScore -= Math.sqrt(2 * gameScore + 1);
		eat();
		gameScore -= Math.sqrt(2 * (gameScore) + 1);
	}

	public void shorten(){
		lenght = 0;
		for (int i = GameState.boardLocationStartX; i < GameState.boardLocationEndX; i++) { //handler.getWorld().GridWidthHeightPixelCount
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
