package Game.Entities.Static;

import Game.Entities.Dynamic.Player;
import Game.Entities.Dynamic.PlayerTwo;
import Main.Handler;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Apple {

    private Handler handler;

    public int xCoord;
    public int yCoord;

    public Apple(Handler handler,int x, int y){
        this.handler=handler;
        this.xCoord=x;
        this.yCoord=y;
    }
    
    public static Boolean isGood(){
    	System.out.println(Player.totalMovement + "totalmove | gameScore" + Player.gameScore);
    	return (!(Player.totalMovement >= 150 && (Player.gameScore >= 5 ||PlayerTwo.gameScore2 >=5)));
    }


}
