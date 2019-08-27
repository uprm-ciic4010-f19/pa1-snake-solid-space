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

public class PlayerTwo {
	public int length2;
	public boolean justAte2;
	public State pauseState;
	public State gameOverState;
	private Handler handler;
	private int count;

	public int xCoord2;
	public int yCoord2;

	public int moveCounter2;
	public Color colorEatChange2; // Changes color when snake eats.
	public Color appleColorChange2; // changes the apple color
	public int snakeSpeed2; // Snake speed changer debug buttons.
	public double gameScore2; // Game score.
	public String direction2;// is your first name one?
	public int totalMovement2;
	public int lastStudentIDDigit2;

	public PlayerTwo(Handler handler) {
		this.handler = handler;
		lastStudentIDDigit2 = 7;
		xCoord2 = 0;
		yCoord2 = 0;
		count = 0;
		moveCounter2 = 0;
		direction2 = "Down";
		justAte2 = false;
		length2 = 1;

	}

	public void tick() {
		moveCounter2++;
		pauseState = new PauseState(handler);
		gameOverState = new GameOverState(handler);
		if (moveCounter2 >= 25 - snakeSpeed2) {
			checkCollisionAndMove2();
			moveCounter2 = 0;
		}
		if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) && (direction2 != "Down")) {
			direction2 = "Up";
		}
		if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) && (direction2 != "Up")) {
			direction2 = "Down";
		}
		if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) && (direction2 != "Right")) {
			direction2 = "Left";
		}
		if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) && (direction2 != "Left")) {
			direction2 = "Right";
		}

		// Debug keys
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)) { // Add tail to snake.
			tailDebug();
		}
		if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS))
				|| (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ADD))) { // Increases snake speed.
			snakeSpeed2++;
			System.out.println("Debug Speed increased to: " + snakeSpeed2);
		}
		if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)
				|| handler.getKeyManager().keyJustPressed(KeyEvent.VK_SUBTRACT))) { // Decreases snake speed.
			snakeSpeed2--;
			System.out.println("Debug Speed decreased to: " + snakeSpeed2);
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_R)) { // Reset game
			handler.getMouseManager().setUimanager(null);
			handler.getGame().reStart();
			State.setState(handler.getGame().gameState);
			System.out.println("Game reseted.");
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_C)) { // Color change
			colorEatChange2 = SnakeColor.colorChange();
			System.out.println("Color changed.");
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_K)) { // Insta Game Over
			GameState.setState(gameOverState);
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)) { // Add score debug button
			scoreDebug();
		}
	}

	public void checkCollisionAndMove2() {
		handler.getWorld().playerLocation2[xCoord2][yCoord2] = false;
		int x = xCoord2;
		int y = yCoord2;
		totalMovement2++;

		selfCollisionCheck2();

		switch (direction2) {
		case "Left":
			if (xCoord2 == 0) {
				// shorten();
				xCoord2 = handler.getWorld().GridWidthHeightPixelCount - 1;
			} else {
				xCoord2--;
			}
			break;
		case "Right":
			if (xCoord2 == handler.getWorld().GridWidthHeightPixelCount - 1) {
				// shorten();
				xCoord2 = 0;
			} else {
				xCoord2++;
			}
			break;
		case "Up":
			if (yCoord2 == 0) {
				// shorten();
				yCoord2 = handler.getWorld().GridWidthHeightPixelCount - 1;
			} else {
				yCoord2--;
			}
			break;
		case "Down":
			if (yCoord2 == handler.getWorld().GridWidthHeightPixelCount - 1) {
				// shorten();
				yCoord2 = 0;
			} else {
				yCoord2++;
			}
			break;
		}
		handler.getWorld().playerLocation2[xCoord2][yCoord2] = true;

		if (handler.getWorld().appleLocation2[xCoord2][yCoord2] && totalMovement2 > 150 && gameScore2 >= 5) {
			rottenEat();
		} else {
			if (handler.getWorld().appleLocation2[xCoord2][yCoord2])
				Eat();
		}

		if (!handler.getWorld().body2.isEmpty()) {
			handler.getWorld().playerLocation2[handler.getWorld().body2.getLast().x][handler.getWorld().body2
					.getLast().y] = false;
			handler.getWorld().body2.removeLast();
			handler.getWorld().body2.addFirst(new TailTwo(x, y, handler));
		}

	}

	public void selfCollisionCheck2() {
		handler.getWorld().playerLocation2[xCoord2][yCoord2] = false;
		int x = xCoord2;
		int y = yCoord2;
		System.out.println("x" + x + "y" + y);
		for (TailTwo i : handler.getWorld().body2) {
			if (i.x == xCoord2) {
				if (i.y == yCoord2) {
					Game.GameStates.AudioPlay.stopSound();
					if (!Game.GameStates.OptionsState.soundOff) {
						Game.GameStates.AudioPlay.playSound(3);
					}
					GameState.setState(gameOverState);
				}

			}
		}
	}

	public void render(Graphics g, Boolean[][] playeLocation) {
		Random r = new Random();
		if (totalMovement2 == 150 && gameScore2 >= 5)
			appleColorChange2 = SnakeColor.badAppleColorChange();
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

				if (gameScore2 >= 0) {
					Color GO = new Color(0, 171, 102);
					g.setColor(GO);
					g.setFont(new Font("arial", Font.PLAIN, 60 / ScreenRes.downscale));
					g.drawString("PLAYER 2", handler.getWorld().GridSize + handler.getWorld().GridPixelsize * 5, ScreenRes.height / 2 - ScreenRes.height/20);
					g.drawString("" +(int)gameScore2, handler.getWorld().GridSize + handler.getWorld().GridPixelsize*5, ScreenRes.height/5 + ScreenRes.height/3);
				}
				if (colorEatChange2 != null) {
					g.setColor(colorEatChange2);
				} else {
					g.setColor(new Color(80, 220, 100));
				}

				if (playeLocation[i][j]) {
					g.fillRect((i * handler.getWorld().GridPixelsize), (j * handler.getWorld().GridPixelsize),
							handler.getWorld().GridPixelsize, handler.getWorld().GridPixelsize);
				}

				if (handler.getWorld().appleLocation2[i][j]) {
					if (appleColorChange2 != null) {
						g.setColor(appleColorChange2);
					} else {
						g.setColor(new Color(255, 0, 0));
					}
					g.fillRect((i * handler.getWorld().GridPixelsize), (j * handler.getWorld().GridPixelsize),
							handler.getWorld().GridPixelsize, handler.getWorld().GridPixelsize);
				}

			}
		}

	}

	public void Eat() {
		length2++;
		totalMovement2 = 0;
		snakeSpeed2 += lastStudentIDDigit2 + 1;
		gameScore2 += Math.sqrt(2 * gameScore2 + 1);
		System.out.println("Score: " + gameScore2);
		colorEatChange2 = SnakeColor.colorChange();
		appleColorChange2 = SnakeColor.appleColorChange();
		TailTwo tail = null;
		handler.getWorld().appleLocation2[xCoord2][yCoord2] = false;
		handler.getWorld().appleOnBoard = false;
		switch (direction2) {
		case "Left":
			if (handler.getWorld().body2.isEmpty()) {
				if (this.xCoord2 != handler.getWorld().GridWidthHeightPixelCount - 1) {
					tail = new TailTwo(this.xCoord2 + 1, this.yCoord2, handler);
				} else {
					if (this.yCoord2 != 0) {
						tail = new TailTwo(this.xCoord2, this.yCoord2 - 1, handler);
					} else {
						tail = new TailTwo(this.xCoord2, this.yCoord2 + 1, handler);
					}
				}
			} else {
				if (handler.getWorld().body2.getLast().x != handler.getWorld().GridWidthHeightPixelCount - 1) {
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
				if (this.xCoord2 != 0) {
					tail = new TailTwo(this.xCoord2 - 1, this.yCoord2, handler);
				} else {
					if (this.yCoord2 != 0) {
						tail = new TailTwo(this.xCoord2, this.yCoord2 - 1, handler);
					} else {
						tail = new TailTwo(this.xCoord2, this.yCoord2 + 1, handler);
					}
				}
			} else {
				if (handler.getWorld().body2.getLast().x != 0) {
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
					if (this.xCoord2 != 0) {
						tail = (new TailTwo(this.xCoord2 - 1, this.yCoord2, handler));
					} else {
						tail = (new TailTwo(this.xCoord2 + 1, this.yCoord2, handler));
					}
				}
			} else {
				if (handler.getWorld().body2.getLast().y != handler.getWorld().GridWidthHeightPixelCount - 1) {
					tail = (new TailTwo(handler.getWorld().body2.getLast().x, this.yCoord2 + 1, handler));
				} else {
					if (handler.getWorld().body2.getLast().x != 0) {
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
					if (this.xCoord2 != 0) {
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
					if (handler.getWorld().body2.getLast().x != 0) {
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
		gameScore2 += Math.sqrt(2 * gameScore2 + 1);
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
				if (this.xCoord2 != handler.getWorld().GridWidthHeightPixelCount - 1) {
					tail = new TailTwo(this.xCoord2 + 1, this.yCoord2, handler);
				} else {
					if (this.yCoord2 != 0) {
						tail = new TailTwo(this.xCoord2, this.yCoord2 - 1, handler);
					} else {
						tail = new TailTwo(this.xCoord2, this.yCoord2 + 1, handler);
					}
				}
			} else {
				if (handler.getWorld().body2.getLast().x != handler.getWorld().GridWidthHeightPixelCount - 1) {
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
				if (this.xCoord2 != 0) {
					tail = new TailTwo(this.xCoord2 - 1, this.yCoord2, handler);
				} else {
					if (this.yCoord2 != 0) {
						tail = new TailTwo(this.xCoord2, this.yCoord2 - 1, handler);
					} else {
						tail = new TailTwo(this.xCoord2, this.yCoord2 + 1, handler);
					}
				}
			} else {
				if (handler.getWorld().body2.getLast().x != 0) {
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
					if (this.xCoord2 != 0) {
						tail = (new TailTwo(this.xCoord2 - 1, this.yCoord2, handler));
					} else {
						tail = (new TailTwo(this.xCoord2 + 1, this.yCoord2, handler));
					}
				}
			} else {
				if (handler.getWorld().body2.getLast().y != handler.getWorld().GridWidthHeightPixelCount - 1) {
					tail = (new TailTwo(handler.getWorld().body2.getLast().x, this.yCoord2 + 1, handler));
				} else {
					if (handler.getWorld().body2.getLast().x != 0) {
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
					if (this.xCoord2 != 0) {
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
					if (handler.getWorld().body2.getLast().x != 0) {
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
		snakeSpeed2 -= (lastStudentIDDigit2 + 1);
		gameScore2 -= Math.sqrt(2 * gameScore2 + 1);
		Eat();
		gameScore2 -= Math.sqrt(2 * (gameScore2) + 1);
	}

	public void shorten() {
		length2 = 0;
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
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
