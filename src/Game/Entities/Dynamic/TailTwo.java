package Game.Entities.Dynamic;

import Main.Handler;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class TailTwo {
    public int x,y;
    public TailTwo(int x, int y,Handler handler){
        this.x=x;
        this.y=y;
        handler.getWorld().playerLocation2[x][y]=true;

    }

}