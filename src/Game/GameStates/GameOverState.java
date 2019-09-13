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
				Resources.Soundtrack.stopSound();
				if (!Resources.Soundtrack.getSoundOff()) {
					Resources.Soundtrack.playSound(0);
				}
			}));

			uiManager.addObjects(new UIImageButton(handler.getWidth()/2-(130/ScreenRes.downscale), handler.getHeight()-(616/ScreenRes.downscale), 90/ScreenRes.downscale, 15/ScreenRes.downscale, Images.Continue, () -> {
				handler.getMouseManager().setUimanager(null);
				State.setState(handler.getGame().gameState);
				Game.Entities.Dynamic.Player.gameScore = 0;
				Game.Entities.Dynamic.PlayerTwo.gameScore2 = 0;
				Resources.Soundtrack.stopSound();
				if (!Resources.Soundtrack.getSoundOff()) {
					Resources.Soundtrack.playSound(1);
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
			g.drawImage(Images.gameOver,0,0,handler.getWidth(),handler.getHeight(),null);
			uiManager.Render(g);
		} else {
			if (!Player.playerOneLost) {
				Color GO = new Color(0,171,102);

				// -------------------------left panel---------------------- //
				
				//draw background
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, handler.getWidth(), handler.getHeight());

				//draw score header border
				g.setColor(GO);
				g.drawRect(0, 0, ScreenRes.GridPixelsize*GameState.boardLocationStartX, handler.getHeight()/15);

				//player one avatar
				g.drawImage(Images.playOne, 0, handler.getHeight()/2, ScreenRes.GridPixelsize*GameState.boardLocationStartX-2, handler.getHeight()/2,null);

				//draw panel border
				g.setColor(GO);
				g.drawRect(0, 1, ScreenRes.GridPixelsize*GameState.boardLocationStartX-1, handler.getHeight()-2);
				g.drawRect(0, 2, ScreenRes.GridPixelsize*GameState.boardLocationStartX-2, handler.getHeight()-4);
				g.drawRect(0, 3, ScreenRes.GridPixelsize*GameState.boardLocationStartX-3, handler.getHeight()-6);

				//score image
				g.drawImage(Images.score,0 + (ScreenRes.GridPixelsize*GameState.boardLocationStartX)/10,10,handler.getHeight()/3,handler.getHeight()/16,null);
				g.drawImage(Images.playerOneScore,0 + (ScreenRes.GridPixelsize*GameState.boardLocationStartX)/13,handler.getHeight()/2-handler.getHeight()/20,handler.getHeight()/3,handler.getHeight()/18,null);
				g.drawImage(Images.playerOneWin,33/ScreenRes.downscale, handler.getHeight()/2+handler.getHeight()/9, ScreenRes.GridPixelsize*GameState.boardLocationStartX-2-(50/ScreenRes.downscale), 300/ScreenRes.downscale,null);
				g.setColor(GO);
				g.setFont(new Font("arial", Font.PLAIN, 80 / ScreenRes.downscale));
				g.drawString("PLAYER ONE WINS!", handler.getWidth()/2-ScreenRes.GridPixelsize*GameState.boardLocationStartX/2, handler.getHeight()/2);
				g.setFont(new Font("arial", Font.PLAIN, 60/ScreenRes.downscale));
				g.drawString("" +(int)Player.gameScore, 0 + (ScreenRes.GridPixelsize*GameState.boardLocationStartX)/10, handler.getHeight()/5 + handler.getHeight()/20);
				g.drawImage(Images.crt,0,0,handler.getWidth(),handler.getHeight(),null);
				
			} else {
				
				// -------------------------right panel---------------------- //
				Color GO = new Color(0,171,102);
				//draw background
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
				

				//draw score header border
				g.setColor(GO);
				g.drawRect(ScreenRes.GridPixelsize*GameState.boardLocationEndX+1, 0, ScreenRes.GridPixelsize*GameState.boardLocationStartX, handler.getHeight()/15);

				//draw winning avatar
				g.drawImage(Images.playerTwoWin, ScreenRes.GridPixelsize*GameState.boardLocationEndX, handler.getHeight()/2-handler.getHeight()/32, handler.getWidth() - ScreenRes.GridPixelsize*GameState.boardLocationEndX - 2, handler.getHeight()/2+handler.getHeight()/19,null);
				
				//draw panel border
				g.drawRect(ScreenRes.GridPixelsize*GameState.boardLocationEndX+1, 1, handler.getWidth()-ScreenRes.GridPixelsize*GameState.boardLocationStartX-1, handler.getHeight()-2);
				g.drawRect(ScreenRes.GridPixelsize*GameState.boardLocationEndX+2, 2, handler.getWidth()-ScreenRes.GridPixelsize*GameState.boardLocationStartX-2, handler.getHeight()-4);
				g.drawRect(ScreenRes.GridPixelsize*GameState.boardLocationEndX+3, 3, handler.getWidth()-ScreenRes.GridPixelsize*GameState.boardLocationStartX-3, handler.getHeight()-6);
				
				g.setColor(GO);
				
				//draw winning message
				g.setFont(new Font("arial", Font.PLAIN, 80 / ScreenRes.downscale));
				g.drawString("PLAYER TWO WINS!", ScreenRes.GridPixelsize*GameState.boardLocationStartX, handler.getHeight()/2);
				g.setFont(new Font("arial", Font.PLAIN, 60 / ScreenRes.downscale));
				g.drawString("" +(int)PlayerTwo.gameScore2, GameState.boardLocationEndX*ScreenRes.GridPixelsize+(ScreenRes.GridPixelsize*GameState.boardLocationStartX)/10, handler.getHeight()/5 + handler.getHeight()/20);
				
				//draw final score
				g.drawImage(Images.score,ScreenRes.GridPixelsize*GameState.boardLocationEndX + (ScreenRes.GridPixelsize*GameState.boardLocationStartX)/10,10,handler.getHeight()/3,handler.getHeight()/16,null); //score image
				g.drawImage(Images.playerTwoScore,ScreenRes.GridPixelsize*GameState.boardLocationEndX + (ScreenRes.GridPixelsize*GameState.boardLocationStartX)/15,handler.getHeight()/2-handler.getHeight()/20,handler.getHeight()/3,handler.getHeight()/18,null); //player two header
				
				//crt effect
				g.drawImage(Images.crt,0,0,handler.getWidth(),handler.getHeight(),null); //crt effect
			}
			
			uiManager.addObjects(new UIImageButton(handler.getWidth()/2-(-22/ScreenRes.downscale), handler.getHeight()-(516/ScreenRes.downscale), 150/ScreenRes.downscale, 20/ScreenRes.downscale, Images.gTitle, () -> {
				handler.getMouseManager().setUimanager(null);
				State.setState(handler.getGame().menuState);
				Resources.Soundtrack.stopSound();
				if (!Resources.Soundtrack.getSoundOff()) {
					Resources.Soundtrack.playSound(0);
				}
			}));

			uiManager.addObjects(new UIImageButton(handler.getWidth()/2+(200/ScreenRes.downscale), handler.getHeight()-(516/ScreenRes.downscale), 150/ScreenRes.downscale, 20/ScreenRes.downscale, Images.Continue, () -> {
				handler.getMouseManager().setUimanager(null);
				State.setState(handler.getGame().gameState);
				Game.Entities.Dynamic.Player.gameScore = 0;
				Game.Entities.Dynamic.PlayerTwo.gameScore2 = 0;
				Resources.Soundtrack.stopSound();
				if (!Resources.Soundtrack.getSoundOff()) {
					Resources.Soundtrack.playSound(1);
				}
			}));

			uiManager.Render(g);
		}
	}
	

}
