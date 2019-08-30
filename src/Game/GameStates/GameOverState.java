package Game.GameStates;

import Main.Handler;
import Main.ScreenRes;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

import java.awt.*;

import Game.Entities.Dynamic.Player;

public class GameOverState extends State {

	private int count = 0;
	private UIManager uiManager;
	Boolean singleplayer = false;

	public GameOverState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUimanager(uiManager);

		if (singleplayer) {
			uiManager.addObjects(new UIImageButton(handler.getWidth()/2-(-52/ScreenRes.downscale), handler.getHeight()-(548/ScreenRes.downscale), 90/ScreenRes.downscale, 15/ScreenRes.downscale, Images.gTitle, () -> {
				handler.getMouseManager().setUimanager(null);
				State.setState(handler.getGame().menuState);
				Game.GameStates.AudioPlay.stopSound();
				if (!Game.GameStates.OptionsState.soundOff) {
					Game.GameStates.AudioPlay.playSound(0);
				}
			}));

			uiManager.addObjects(new UIImageButton(handler.getWidth()/2-(130/ScreenRes.downscale), handler.getHeight()-(548/ScreenRes.downscale), 90/ScreenRes.downscale, 15/ScreenRes.downscale, Images.Continue, () -> {
				handler.getMouseManager().setUimanager(null);
				State.setState(handler.getGame().gameState);
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
		if (singleplayer) {
			g.drawImage(Images.gameOver,0,0,ScreenRes.width,ScreenRes.height,null);
			uiManager.Render(g);
		} else {
			if (!Player.playerOneLost) {
				Color GO = new Color(0,171,102);

				// -------------------------left panel---------------------- //
				
				//draw background for the score panel
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, ScreenRes.GridPixelsize*GameState.boardLocationStartX, ScreenRes.height);

				//draw score image border
				g.setColor(GO);
				g.drawRect(0, 0, ScreenRes.GridPixelsize*GameState.boardLocationStartX, ScreenRes.height/18);

				//player one picture
				g.drawImage(Images.playOne, 0, ScreenRes.height/2, ScreenRes.GridPixelsize*GameState.boardLocationStartX-2, ScreenRes.height/2,null);

				//draw panel border
				g.setColor(GO);
				g.drawRect(0, 1, ScreenRes.GridPixelsize*GameState.boardLocationStartX-1, ScreenRes.height-2);
				g.drawRect(0, 2, ScreenRes.GridPixelsize*GameState.boardLocationStartX-2, ScreenRes.height-4);
				g.drawRect(0, 3, ScreenRes.GridPixelsize*GameState.boardLocationStartX-3, ScreenRes.height-6);

				//score image
				g.drawImage(Images.score,0 + (ScreenRes.GridPixelsize*GameState.boardLocationStartX)/10,10,ScreenRes.height/3,ScreenRes.height/16,null);
				g.drawImage(Images.playerOneScore,0 + (ScreenRes.GridPixelsize*GameState.boardLocationStartX)/13,ScreenRes.height/2-ScreenRes.height/20,ScreenRes.height/3,ScreenRes.height/18,null);
				g.drawImage(Images.playerOneWin,29, ScreenRes.height/2+ScreenRes.height/9, ScreenRes.GridPixelsize*GameState.boardLocationStartX-2-50, 300,null);
				g.drawImage(Images.crt,0,0,handler.getWidth(),handler.getHeight(),null);
			} else {
				Color GO = new Color(0,171,102);
				//draw background for the score panel
				g.setColor(Color.BLACK);
				g.fillRect(ScreenRes.GridPixelsize*GameState.boardLocationEndX, 0, ScreenRes.width, ScreenRes.height);

				//draw score title image border
				g.setColor(GO);
				g.drawRect(ScreenRes.GridPixelsize*GameState.boardLocationEndX+1, 0, ScreenRes.GridPixelsize*GameState.boardLocationStartX, ScreenRes.height/18);

				//player two picture
				g.drawImage(Images.playerTwo, ScreenRes.GridPixelsize*GameState.boardLocationEndX, ScreenRes.height/2, ScreenRes.width - ScreenRes.GridPixelsize*GameState.boardLocationEndX - 2, ScreenRes.height/2,null);

				//draw game border
				g.setColor(GO);
				g.drawRect(ScreenRes.GridPixelsize*GameState.boardLocationStartX-1, 1, ScreenRes.GridPixelsize*ScreenRes.GridSize-3, ScreenRes.height-3);
				g.drawRect(ScreenRes.GridPixelsize*GameState.boardLocationStartX-1, 2, ScreenRes.GridPixelsize*ScreenRes.GridSize-3, ScreenRes.height-3);
				g.drawRect(ScreenRes.GridPixelsize*GameState.boardLocationStartX-1, 3, ScreenRes.GridPixelsize*ScreenRes.GridSize-3, ScreenRes.height-3);

				//draw panel border
				g.setColor(GO);
				g.drawRect(ScreenRes.GridPixelsize*GameState.boardLocationEndX+1, 1, ScreenRes.width-ScreenRes.GridPixelsize*GameState.boardLocationEndX-1, ScreenRes.height-2);
				g.drawRect(ScreenRes.GridPixelsize*GameState.boardLocationEndX+2, 2, ScreenRes.width-ScreenRes.GridPixelsize*GameState.boardLocationEndX-2, ScreenRes.height-4);
				g.drawRect(ScreenRes.GridPixelsize*GameState.boardLocationEndX+3, 3, ScreenRes.width-ScreenRes.GridPixelsize*GameState.boardLocationEndX-3, ScreenRes.height-6);

				//score image
				g.drawImage(Images.score,ScreenRes.GridPixelsize*GameState.boardLocationEndX + (ScreenRes.GridPixelsize*GameState.boardLocationStartX)/10,10,ScreenRes.height/3,ScreenRes.height/16,null);
				g.drawImage(Images.playerTwoScore,ScreenRes.GridPixelsize*GameState.boardLocationEndX + (ScreenRes.GridPixelsize*GameState.boardLocationStartX)/15,ScreenRes.height/2-ScreenRes.height/20,ScreenRes.height/3,ScreenRes.height/18,null);
				g.drawImage(Images.playerTwoWin, ScreenRes.GridPixelsize*GameState.boardLocationEndX, ScreenRes.height/2-ScreenRes.height/32, ScreenRes.width - ScreenRes.GridPixelsize*GameState.boardLocationEndX - 2, ScreenRes.height/2+ScreenRes.height/19,null);
				g.drawImage(Images.crt,0,0,handler.getWidth(),handler.getHeight(),null);
			}
		}

	}

}
