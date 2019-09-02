package Game.GameStates;

import Main.Handler;
import Main.ScreenRes;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

import java.awt.*;

import Game.Entities.Dynamic.Player;
import Game.Entities.Dynamic.PlayerTwo;

public class GameOverState extends State {

	private int count = 0;
	private UIManager uiManager;

	public GameOverState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUimanager(uiManager);

		if (ModeState.singlePlayerMode) {
			uiManager.addObjects(new UIImageButton(handler.getWidth()/2-(-52/ScreenRes.downscale), handler.getHeight()-(616/ScreenRes.downscale), 90/ScreenRes.downscale, 15/ScreenRes.downscale, Images.gTitle, () -> {
				handler.getMouseManager().setUimanager(null);
				State.setState(handler.getGame().menuState);
				Game.GameStates.AudioPlay.stopSound();
				if (!Game.GameStates.OptionsState.soundOff) {
					Game.GameStates.AudioPlay.playSound(0);
				}
			}));

			uiManager.addObjects(new UIImageButton(handler.getWidth()/2-(130/ScreenRes.downscale), handler.getHeight()-(616/ScreenRes.downscale), 90/ScreenRes.downscale, 15/ScreenRes.downscale, Images.Continue, () -> {
				handler.getMouseManager().setUimanager(null);
				State.setState(handler.getGame().gameState);
				Game.Entities.Dynamic.Player.gameScore = 0;
				Game.Entities.Dynamic.PlayerTwo.gameScore2 = 0;
				Game.GameStates.AudioPlay.stopSound();
				if (!Game.GameStates.OptionsState.soundOff) {
					Game.GameStates.AudioPlay.playSound(1);
				}
			}));
		}

	}

	@Override
	public void tick() {
		handler.getMouseManager().setUimanager(uiManager);
		uiManager.tick();
		count++;
		if( count>=30){
			count=30;
		}
		if(handler.getKeyManager().pbutt && count>=30){
			count=0;

			State.setState(handler.getGame().gameState);
		}


	}

	@Override
	public void render(Graphics g) {
		handler.getGame().reStart();
		if (ModeState.singlePlayerMode) {
			g.drawImage(Images.gameOver,0,0,ScreenRes.width,ScreenRes.height,null);
			uiManager.Render(g);
		} else {
			if (!Player.playerOneLost) {
				Color GO = new Color(0,171,102);

				// -------------------------left panel---------------------- //
				
				//draw background
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, ScreenRes.width, ScreenRes.height);

				//draw score header border
				g.setColor(GO);
				g.drawRect(0, 0, ScreenRes.GridPixelsize*GameState.boardLocationStartX, ScreenRes.height/15);

				//player one avatar
				g.drawImage(Images.playOne, 0, ScreenRes.height/2, ScreenRes.GridPixelsize*GameState.boardLocationStartX-2, ScreenRes.height/2,null);

				//draw panel border
				g.setColor(GO);
				g.drawRect(0, 1, ScreenRes.GridPixelsize*GameState.boardLocationStartX-1, ScreenRes.height-2);
				g.drawRect(0, 2, ScreenRes.GridPixelsize*GameState.boardLocationStartX-2, ScreenRes.height-4);
				g.drawRect(0, 3, ScreenRes.GridPixelsize*GameState.boardLocationStartX-3, ScreenRes.height-6);

				//score image
				g.drawImage(Images.score,0 + (ScreenRes.GridPixelsize*GameState.boardLocationStartX)/10,10,ScreenRes.height/3,ScreenRes.height/16,null);
				g.drawImage(Images.playerOneScore,0 + (ScreenRes.GridPixelsize*GameState.boardLocationStartX)/13,ScreenRes.height/2-ScreenRes.height/20,ScreenRes.height/3,ScreenRes.height/18,null);
				g.drawImage(Images.playerOneWin,33/ScreenRes.downscale, ScreenRes.height/2+ScreenRes.height/9, ScreenRes.GridPixelsize*GameState.boardLocationStartX-2-(50/ScreenRes.downscale), 300/ScreenRes.downscale,null);
				g.setColor(GO);
				g.setFont(new Font("arial", Font.PLAIN, 80 / ScreenRes.downscale));
				g.drawString("PLAYER ONE WINS!", ScreenRes.width/2-ScreenRes.GridPixelsize*GameState.boardLocationStartX/2, ScreenRes.height/2);
				g.setFont(new Font("arial", Font.PLAIN, 60/ScreenRes.downscale));
				g.drawString("" +(int)Player.gameScore, 0 + (ScreenRes.GridPixelsize*GameState.boardLocationStartX)/10, ScreenRes.height/5 + ScreenRes.height/20);
				g.drawImage(Images.crt,0,0,handler.getWidth(),handler.getHeight(),null);
				
			} else {
				
				// -------------------------right panel---------------------- //
				Color GO = new Color(0,171,102);
				//draw background
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, ScreenRes.width, ScreenRes.height);
				

				//draw score header border
				g.setColor(GO);
				g.drawRect(ScreenRes.GridPixelsize*GameState.boardLocationEndX+1, 0, ScreenRes.GridPixelsize*GameState.boardLocationStartX, ScreenRes.height/15);

				//draw winning avatar
				g.drawImage(Images.playerTwoWin, ScreenRes.GridPixelsize*GameState.boardLocationEndX, ScreenRes.height/2-ScreenRes.height/32, ScreenRes.width - ScreenRes.GridPixelsize*GameState.boardLocationEndX - 2, ScreenRes.height/2+ScreenRes.height/19,null);
				
				//draw panel border
				g.drawRect(ScreenRes.GridPixelsize*GameState.boardLocationEndX+1, 1, ScreenRes.width-ScreenRes.GridPixelsize*GameState.boardLocationStartX-1, ScreenRes.height-2);
				g.drawRect(ScreenRes.GridPixelsize*GameState.boardLocationEndX+2, 2, ScreenRes.width-ScreenRes.GridPixelsize*GameState.boardLocationStartX-2, ScreenRes.height-4);
				g.drawRect(ScreenRes.GridPixelsize*GameState.boardLocationEndX+3, 3, ScreenRes.width-ScreenRes.GridPixelsize*GameState.boardLocationStartX-3, ScreenRes.height-6);
				
				g.setColor(GO);
				
				//draw winning message
				g.setFont(new Font("arial", Font.PLAIN, 80 / ScreenRes.downscale));
				g.drawString("PLAYER TWO WINS!", ScreenRes.GridPixelsize*GameState.boardLocationStartX, ScreenRes.height/2);
				g.setFont(new Font("arial", Font.PLAIN, 60 / ScreenRes.downscale));
				g.drawString("" +(int)PlayerTwo.gameScore2, GameState.boardLocationEndX*ScreenRes.GridPixelsize+(ScreenRes.GridPixelsize*GameState.boardLocationStartX)/10, ScreenRes.height/5 + ScreenRes.height/20);
				
				//draw final score
				g.drawImage(Images.score,ScreenRes.GridPixelsize*GameState.boardLocationEndX + (ScreenRes.GridPixelsize*GameState.boardLocationStartX)/10,10,ScreenRes.height/3,ScreenRes.height/16,null); //score image
				g.drawImage(Images.playerTwoScore,ScreenRes.GridPixelsize*GameState.boardLocationEndX + (ScreenRes.GridPixelsize*GameState.boardLocationStartX)/15,ScreenRes.height/2-ScreenRes.height/20,ScreenRes.height/3,ScreenRes.height/18,null); //player two header
				
				//crt effect
				g.drawImage(Images.crt,0,0,handler.getWidth(),handler.getHeight(),null); //crt effect
			}
		}

	}

}
