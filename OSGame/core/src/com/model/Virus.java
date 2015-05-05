/* VirusEntry.java
 * by Elizabeth Hernandez
 * developed from MonsterTowards by Michael
 * 4-29-15
 */
package com.model;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.TimeUtils;
import com.comms.GameID;
import com.comms.GameState;
import com.map.Direction;
import com.map.Map;
import com.map.Position;

/**
 *
 * @author elizabeth
 */
public class Virus extends Monster
{
    public Map map;
    private int xBound;
    private int yBound;
    
    public Virus(GameState state, String img, Sound splatSound, Position pos) 
    {
        super(state, img, splatSound);
        super.changeAlive("VirusEntry");
        super.changeDeath("CyberFloor");
	//super.changeSplat("");
        UPDATE_INTERVAL=10000;
        position = pos;
        map = gameState.gameMap();
        xBound = map.getXBound();
        yBound = map.getCyberYStart();
    }
    
    @Override
    public void update()
    {
        if(isDead()==true)
            return;

        closestPlayer=getClosestPlayer();

        if(wasDead==true)
        {
            //set image and rand X/Y
            wasDead=false;
            changeSprite(alive);
            int x,y;
            do
            {
                x=randomGen.nextInt(xBound);
                y=randomGen.nextInt(yBound);
            }while(map.isWalkable(x,y)==false);
            position = new Position(x,y);
        }
        else
        {
            if(squished()==true)
            {
                deathTime=TimeUtils.millis();
                changeSprite(dead);
                splat.play();
                wasDead=true;
            }

            // check if it's time to take a step
            else if(TimeUtils.millis()-lastUpdateTime > UPDATE_INTERVAL)
            {   // seek player to chase
                lastUpdateTime=TimeUtils.millis();
                if(closestPlayer==null) 
                    return;

                // get coordinates of self and closest player
                int myX = getXPos();
                int myY = getXPos();
                int playerX = closestPlayer.getXPos();
                int playerY = closestPlayer.getYPos();

                // chase closest player
                if(playerX > myX)
                    super.move(Direction.EAST);
                else if(playerX < myX)
                    super.move(Direction.WEST);
                else if(playerY > myY)
                    super.move(Direction.NORTH);
                else
                    super.move(Direction.SOUTH);
            }
        }
    }
}
