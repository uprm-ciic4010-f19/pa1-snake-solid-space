package Game.Entities.Dynamic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

import Game.Entities.Static.Apple;
import Game.GameStates.GameOverState;
import Game.GameStates.GameState;
import Game.GameStates.ModeState;
import Game.GameStates.PauseState;
import Game.GameStates.State;
import Main.Handler;
import Main.ScreenRes;

public class PlayerTwo {
	public int length2;
	public boolean justAte2;
	public State pauseState;
	public State gameOverState;
	private Handler handler;

	public int xCoord2;
	public int yCoord2;

	public int moveCounter2;
	public Color colorEatChange2; // Changes color when snake eats.
	public Color appleColorChange2; // changes the apple color
	public int snakeSpeedModifier2; // Snake speed changer debug buttons.
	public static double gameScore2; // Game score.
	public String direction2;// is your first name one?
	public static int totalMovement2;
	public int lastStudentIDDigit2;
	public int maxSpeed2;
	public int additionalSpeed2;

	public PlayerTwo(Handler handler) {
		this.handler = handler;
		lastStudentIDDigit2 = 7;
		xCoord2 = GameState.boardLocationStartX;
		yCoord2 = 0;
		moveCounter2 = 0;
		direction2 = "Down";
		justAte2 = false;
		length2 = 1;
		maxSpeed2 = 8;
		additionalSpeed2 = 0;

	}

	public void tick() {
		moveCounter2++;
		pauseState = new PauseState(handler);
		gameOverState = new GameOverState(handler);
		if (moveCounter2 >= (maxSpeed2-snakeSpeedModifier2)+1) {
			checkCollisionAndMove();
			if(additionalSpeed2 >=64) {
				checkCollisionAndMove();
			}
			if(additionalSpeed2 >=128) {
				checkCollisionAndMove();
			}
			if(additionalSpeed2 >=256) {
				checkCollisionAndMove();
			}
			if(additionalSpeed2 >=512) {
				checkCollisionAndMove();
			}
		}
		if((handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) && (direction2!="Down") && (direction2!="Up")){
			direction2="Up";
			checkCollisionAndMove();
		}if((handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) && (direction2!="Up") && (direction2!="Down")){
			direction2="Down";
			checkCollisionAndMove();
		}if((handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) && (direction2!="Right") && (direction2!="Left")){
			direction2="Left";
			checkCollisionAndMove();
		}if((handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) && (direction2!="Left") && (direction2!="Right")){
			direction2="Right";
			checkCollisionAndMove();
		}
		// Debug keys
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)) { // Add tail to snake.
			tailDebug();
		}
		if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS))
				|| (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ADD))) { // Increases snake speed.
			snakeSpeedModifier2+=lastStudentIDDigit2+1;
			System.out.println("Debug Speed increased to: " + snakeSpeedModifier2);
		}
		if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)
				|| handler.getKeyManager().keyJustPressed(KeyEvent.VK_SUBTRACT))) { // Decreases snake speed.
			snakeSpeedModifier2-=lastStudentIDDigit2+1;
			System.out.println("Debug Speed decreased to: " + snakeSpeedModifier2);
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_R)) { // Reset game
			handler.getMouseManager().setUimanager(null);
			handler.getGame().reStart();
			State.setState(handler.getGame().gameState);
			System.out.println("Game reseted.");
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_C)) { // Color change
			colorEatChange2 = EntityColor.colorChange();
			System.out.println("Color changed.");
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_K)) { // Insta Game Over
			GameState.setState(gameOverState);
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)) { // Add score debug button
			scoreDebug();
		}
	}

	public void checkCollisionAndMove() {
		moveCounter2=0;
		handler.getWorld().playerLocation2[xCoord2][yCoord2] = false;
		int x = xCoord2;
		int y = yCoord2;
		totalMovement2++;

		if(!Game.GameStates.ModeState.singlePlayerMode) {
			playerCollisionCheck();
			selfCollisionCheck();
		}
		switch (direction2) {
		case "Left":
			if (xCoord2 == GameState.boardLocationStartX) {
				xCoord2 = GameState.boardLocationEndX - 1;
			} else {
				xCoord2--;
			}
			break;
		case "Right":
			if (xCoord2 == GameState.boardLocationEndX - 1) {
				xCoord2 = GameState.boardLocationStartX;
			} else {
				xCoord2++;
			}
			break;
		case "Up":
			if (yCoord2 == 0) {
				yCoord2 = handler.getWorld().GridWidthHeightPixelCount - 1;
			} else {
				yCoord2--;
			}
			break;
		case "Down":
			if (yCoord2 == handler.getWorld().GridWidthHeightPixelCount - 1) {
				yCoord2 = 0;
			} else {
				yCoord2++;
			}
			break;
		}
		handler.getWorld().playerLocation2[xCoord2][yCoord2] = true;


		if((handler.getWorld().appleLocation[xCoord2][yCoord2]||handler.getWorld().appleLocation2[xCoord2][yCoord2]) && !Game.GameStates.ModeState.singlePlayerMode){
			if (Apple.isGood()) {
				gameScore2 += Math.sqrt(Math.abs(2 * (gameScore2) + 1));
				eat();
			} else {
				if(!Game.GameStates.ModeState.singlePlayerMode) {
					gameScore2 -= Math.sqrt(Math.abs(2 * (gameScore2) + 1));
					rottenEat();
				}
			}
		}

		if (!handler.getWorld().body2.isEmpty()) {
			handler.getWorld().playerLocation2[handler.getWorld().body2.getLast().x][handler.getWorld().body2.getLast().y] = false;
			handler.getWorld().body2.removeLast();
			handler.getWorld().body2.addFirst(new TailTwo(x, y, handler));
		}

	}

	public void selfCollisionCheck() {
		for (TailTwo i : handler.getWorld().body2) {
			if (i.x == xCoord2 && i.y == yCoord2) {
				Resources.Soundtrack.stopSound();
				Player.playerOneLost = false;
				if (!Resources.Soundtrack.getSoundOff() && ModeState.singlePlayerMode) {
					Resources.Soundtrack.playSound(3);
				} else {
					Resources.Soundtrack.playSound(4);
				}
				GameState.setState(gameOverState);
			}

		}
	}

	public void playerCollisionCheck() {
		for (Tail i: handler.getWorld().body){
			if (i.x == xCoord2 && i.y == yCoord2) {
				Resources.Soundtrack.stopSound();
				Player.playerOneLost = false;
				if (!Resources.Soundtrack.getSoundOff() && ModeState.singlePlayerMode) {
					Resources.Soundtrack.playSound(3);
				} else {
					Resources.Soundtrack.playSound(4);
				}
				GameState.setState(gameOverState);
			}

		}
	}

	public void render(Graphics g, Boolean[][] playeLocation) {
		Random r = new Random();
		for (int i = GameState.boardLocationStartX; i < GameState.boardLocationEndX; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

				if (!Game.GameStates.ModeState.singlePlayerMode) {
					Color GO = new Color(0, 171, 102);
					g.setColor(GO);
					g.setFont(new Font("arial", Font.PLAIN, 60 / ScreenRes.downscale));
					g.drawString("" +(int)gameScore2, GameState.boardLocationEndX*ScreenRes.GridPixelsize+(ScreenRes.GridPixelsize*GameState.boardLocationStartX)/10, ScreenRes.height/5 + ScreenRes.height/20);
					g.drawString("PTS", ScreenRes.width -(ScreenRes.GridPixelsize*GameState.boardLocationStartX)/3, ScreenRes.height/5 + ScreenRes.height/20);

				}
				if (colorEatChange2 != null) {
					g.setColor(colorEatChange2);
				} else {
					g.setColor(new Color(80, 220, 100));
				}

				if(playeLocation[i][j] && !Game.GameStates.ModeState.singlePlayerMode){
					g.setColor(Color.BLACK);
					g.fillOval((i * handler.getWorld().GridPixelsize), (j * handler.getWorld().GridPixelsize),handler.getWorld().GridPixelsize+1, handler.getWorld().GridPixelsize+1);
					if (colorEatChange2 != null) {
						g.setColor(colorEatChange2);
						g.fillOval((i * handler.getWorld().GridPixelsize), (j * handler.getWorld().GridPixelsize),handler.getWorld().GridPixelsize, handler.getWorld().GridPixelsize);
					} else {
						g.setColor(new Color(80, 220, 100));
						g.fillOval((i * handler.getWorld().GridPixelsize), (j * handler.getWorld().GridPixelsize),handler.getWorld().GridPixelsize, handler.getWorld().GridPixelsize);
					}

				}

			}
		}

	}

	public void eat() {
		length2++;
		totalMovement2 = 0;
		Player.totalMovement = 0;
		if (snakeSpeedModifier2 < maxSpeed2) {
			snakeSpeedModifier2 += lastStudentIDDigit2 + 1;
		}else {
			additionalSpeed2 += lastStudentIDDigit2 + 1;
		}

		if (gameScore2 < 0) {
			gameScore2 = 0;
		}
		
		System.out.println("Score: " + gameScore2);
		colorEatChange2 = EntityColor.colorChange();
		TailTwo tail = null;
		handler.getWorld().appleLocation2[xCoord2][yCoord2] = false;
		handler.getWorld().appleLocation[xCoord2][yCoord2] = false;
		handler.getWorld().appleOnBoard = false;

		switch (direction2) {
		case "Left":
			if (handler.getWorld().body2.isEmpty()) {
				if (this.xCoord2 != GameState.boardLocationEndX - 1) {
					tail = new TailTwo(this.xCoord2 + 1, this.yCoord2, handler);
				} else {
					if (this.yCoord2 != 0) {
						tail = new TailTwo(this.xCoord2, this.yCoord2 - 1, handler);
					} else {
						tail = new TailTwo(this.xCoord2, this.yCoord2 + 1, handler);
					}
				}
			} else {
				if (handler.getWorld().body2.getLast().x != GameState.boardLocationEndX - 1) {
					tail = new TailTwo(handler.getWorld().body2.getLast().x + 1, this.yCoord2, handler);
				} else {
					if (handler.getWorld().body2.getLast().y != 0) {
						tail = new TailTwo(handler.getWorld().body2.getLast().x, this.yCoord2 - 1, handler);
					} else {
						tail = new TailTwo(handler.getWorld().body2.getLast().x, this.yCoord2 + 1, handler);

					}
				}

			}
			break;
		case "Right":
			if (handler.getWorld().body2.isEmpty()) {
				if (this.xCoord2 != GameState.boardLocationStartX) {
					tail = new TailTwo(this.xCoord2 - 1, this.yCoord2, handler);
				} else {
					if (this.yCoord2 != 0) {
						tail = new TailTwo(this.xCoord2, this.yCoord2 - 1, handler);
					} else {
						tail = new TailTwo(this.xCoord2, this.yCoord2 + 1, handler);
					}
				}
			} else {
				if (handler.getWorld().body2.getLast().x != GameState.boardLocationStartX) {
					tail = (new TailTwo(handler.getWorld().body2.getLast().x - 1, this.yCoord2, handler));
				} else {
					if (handler.getWorld().body2.getLast().y != 0) {
						tail = (new TailTwo(handler.getWorld().body2.getLast().x, this.yCoord2 - 1, handler));
					} else {
						tail = (new TailTwo(handler.getWorld().body2.getLast().x, this.yCoord2 + 1, handler));
					}
				}

			}
			break;
		case "Up":
			if (handler.getWorld().body2.isEmpty()) {
				if (this.yCoord2 != handler.getWorld().GridWidthHeightPixelCount - 1) {
					tail = (new TailTwo(this.xCoord2, this.yCoord2 + 1, handler));
				} else {
					if (this.xCoord2 != GameState.boardLocationStartX) {
						tail = (new TailTwo(this.xCoord2 - 1, this.yCoord2, handler));
					} else {
						tail = (new TailTwo(this.xCoord2 + 1, this.yCoord2, handler));
					}
				}
			} else {
				if (handler.getWorld().body2.getLast().y != handler.getWorld().GridWidthHeightPixelCount - 1) {
					tail = (new TailTwo(handler.getWorld().body2.getLast().x, this.yCoord2 + 1, handler));
				} else {
					if (handler.getWorld().body2.getLast().x != GameState.boardLocationStartX) {
						tail = (new TailTwo(handler.getWorld().body2.getLast().x - 1, this.yCoord2, handler));
					} else {
						tail = (new TailTwo(handler.getWorld().body2.getLast().x + 1, this.yCoord2, handler));
					}
				}

			}
			break;
		case "Down":
			if (handler.getWorld().body2.isEmpty()) {
				if (this.yCoord2 != 0) {
					tail = (new TailTwo(this.xCoord2, this.yCoord2 - 1, handler));
				} else {
					if (this.xCoord2 != GameState.boardLocationStartX) {
						tail = (new TailTwo(this.xCoord2 - 1, this.yCoord2, handler));
					} else {
						tail = (new TailTwo(this.xCoord2 + 1, this.yCoord2, handler));
					}
					System.out.println("Tu biscochito");
				}
			} else {
				if (handler.getWorld().body2.getLast().y != 0) {
					tail = (new TailTwo(handler.getWorld().body2.getLast().x, this.yCoord2 - 1, handler));
				} else {
					if (handler.getWorld().body2.getLast().x != GameState.boardLocationStartX) {
						tail = (new TailTwo(handler.getWorld().body2.getLast().x - 1, this.yCoord2, handler));
					} else {
						tail = (new TailTwo(handler.getWorld().body2.getLast().x + 1, this.yCoord2, handler));
					}
				}

			}
			break;
		}
		handler.getWorld().body2.addLast(tail);
		handler.getWorld().playerLocation2[tail.x][tail.y] = true;

	}

	public void scoreDebug() {
		gameScore2 += Math.sqrt(Math.abs(2 * (gameScore2) + 1));
		System.out.println("Score: " + gameScore2);
	}

	public void tailDebug() {
		length2++;
		TailTwo tail = null;
		System.out.println("Length of snake is currently " + length2 + ".");
		handler.getWorld().appleLocation2[xCoord2][yCoord2] = false;
		handler.getWorld().appleOnBoard = true;
		switch (direction2) {
		case "Left":
			if (handler.getWorld().body2.isEmpty()) {
				if (this.xCoord2 != GameState.boardLocationEndX - 1) {
					tail = new TailTwo(this.xCoord2 + 1, this.yCoord2, handler);
				} else {
					if (this.yCoord2 != 0) {
						tail = new TailTwo(this.xCoord2, this.yCoord2 - 1, handler);
					} else {
						tail = new TailTwo(this.xCoord2, this.yCoord2 + 1, handler);
					}
				}
			} else {
				if (handler.getWorld().body2.getLast().x != GameState.boardLocationEndX - 1) {
					tail = new TailTwo(handler.getWorld().body2.getLast().x + 1, this.yCoord2, handler);
				} else {
					if (handler.getWorld().body2.getLast().y != 0) {
						tail = new TailTwo(handler.getWorld().body2.getLast().x, this.yCoord2 - 1, handler);
					} else {
						tail = new TailTwo(handler.getWorld().body2.getLast().x, this.yCoord2 + 1, handler);

					}
				}

			}
			break;
		case "Right":
			if (handler.getWorld().body2.isEmpty()) {
				if (this.xCoord2 != GameState.boardLocationStartX) {
					tail = new TailTwo(this.xCoord2 - 1, this.yCoord2, handler);
				} else {
					if (this.yCoord2 != 0) {
						tail = new TailTwo(this.xCoord2, this.yCoord2 - 1, handler);
					} else {
						tail = new TailTwo(this.xCoord2, this.yCoord2 + 1, handler);
					}
				}
			} else {
				if (handler.getWorld().body2.getLast().x != GameState.boardLocationStartX) {
					tail = (new TailTwo(handler.getWorld().body2.getLast().x - 1, this.yCoord2, handler));
				} else {
					if (handler.getWorld().body2.getLast().y != 0) {
						tail = (new TailTwo(handler.getWorld().body2.getLast().x, this.yCoord2 - 1, handler));
					} else {
						tail = (new TailTwo(handler.getWorld().body2.getLast().x, this.yCoord2 + 1, handler));
					}
				}

			}
			break;
		case "Up":
			if (handler.getWorld().body2.isEmpty()) {
				if (this.yCoord2 != handler.getWorld().GridWidthHeightPixelCount - 1) {
					tail = (new TailTwo(this.xCoord2, this.yCoord2 + 1, handler));
				} else {
					if (this.xCoord2 != GameState.boardLocationStartX) {
						tail = (new TailTwo(this.xCoord2 - 1, this.yCoord2, handler));
					} else {
						tail = (new TailTwo(this.xCoord2 + 1, this.yCoord2, handler));
					}
				}
			} else {
				if (handler.getWorld().body2.getLast().y != handler.getWorld().GridWidthHeightPixelCount - 1) {
					tail = (new TailTwo(handler.getWorld().body2.getLast().x, this.yCoord2 + 1, handler));
				} else {
					if (handler.getWorld().body2.getLast().x != GameState.boardLocationStartX) {
						tail = (new TailTwo(handler.getWorld().body2.getLast().x - 1, this.yCoord2, handler));
					} else {
						tail = (new TailTwo(handler.getWorld().body2.getLast().x + 1, this.yCoord2, handler));
					}
				}

			}
			break;
		case "Down":
			if (handler.getWorld().body2.isEmpty()) {
				if (this.yCoord2 != 0) {
					tail = (new TailTwo(this.xCoord2, this.yCoord2 - 1, handler));
				} else {
					if (this.xCoord2 != GameState.boardLocationStartX) {
						tail = (new TailTwo(this.xCoord2 - 1, this.yCoord2, handler));
					} else {
						tail = (new TailTwo(this.xCoord2 + 1, this.yCoord2, handler));
					}
					System.out.println("Tu biscochito");
				}
			} else {
				if (handler.getWorld().body2.getLast().y != 0) {
					tail = (new TailTwo(handler.getWorld().body2.getLast().x, this.yCoord2 - 1, handler));
				} else {
					if (handler.getWorld().body2.getLast().x != GameState.boardLocationStartX) {
						tail = (new TailTwo(handler.getWorld().body2.getLast().x - 1, this.yCoord2, handler));
					} else {
						tail = (new TailTwo(handler.getWorld().body2.getLast().x + 1, this.yCoord2, handler));
					}
				}

			}
			break;
		}
		handler.getWorld().body2.addLast(tail);
		handler.getWorld().playerLocation2[tail.x][tail.y] = true;
	}

	public void rottenEat() {
		if (snakeSpeedModifier2 > 0) {
			snakeSpeedModifier2 -= (lastStudentIDDigit2 + 1);
		}
		if (snakeSpeedModifier2 < 0 ) {
			snakeSpeedModifier2 = 0;
		}
		gameScore2 -= Math.sqrt(Math.abs(2 * (gameScore2) + 1));
		eat();
		snakeSpeedModifier2 = 0;
	}

	public void shorten() {
		length2 = 0;
		for (int i = GameState.boardLocationStartX; i < GameState.boardLocationEndX; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

				handler.getWorld().playerLocation2[i][j] = false;

			}
		}
	}

	public boolean isJustAte() {
		return justAte2;
	}

	public void setJustAte(boolean justAte) {
		this.justAte2 = justAte;
	}
}
