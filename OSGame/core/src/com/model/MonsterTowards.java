
package com.model;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.TimeUtils;
import com.comms.GameID;
import com.comms.GameState;
import com.map.Direction;
import com.map.Map;
import com.map.Position;
import static java.lang.Math.abs;

/**
 *
 * @author michael, elizabeth
 */
public class MonsterTowards extends Monster
{
    public Map map;
    private int xBound;
    private int yBound;
    int myX;
    int myY;
    int relativeX;
    int relativeY;
    int xStep;
    int yStep;
    Direction bestX;
    Direction bestY;
    
    public MonsterTowards(GameState state, String img, Sound splatSound) 
    {
        super(state, img, splatSound);
        super.changeDeath("!");
        UPDATE_INTERVAL=250;

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
            {   // reset update timer
                lastUpdateTime=TimeUtils.millis();
                
                // seek player to chase
                if(closestPlayer==null) 
                    return;

                // get coordinates of self and closest player
                myX = getXPos();
                myY = getYPos();
                
                setBestDirections();
                moveBest();
            }
        }
    }
    
    // find best directions to chase closest player
    private void setBestDirections () {
        int playerX = closestPlayer.getXPos();
        int playerY = closestPlayer.getYPos();
        relativeX = playerX - myX;
        relativeY = playerY - myY;

        // they are to the east - go east
        if(relativeX > 0) {
            bestX = Direction.EAST;
            xStep = 1;
        }
        // they are to the west - go west
        else {
            bestX = Direction.WEST;
            xStep = -1;
        }
                
        // they are to the north - go north
        if(relativeY > 0) {
            bestY = Direction.NORTH;
            yStep = 1;
        }
        // they are to the south - go south
        else {
            bestY = Direction.SOUTH;
            yStep = -1;
        }
    }
    
    private void moveBest() {
        Direction best;
        // they are closer horizontally
        if (abs(relativeX) > abs(relativeY)) {
            if(map.isWalkable(myX + xStep, myY))
                best = bestX;
            else
                best = bestY;
        }
        // they are closer vertically
        else {
            if(map.isWalkable(myX, myY + yStep))
                best = bestY;
            else
                best = bestX;
        }
                
        super.move(best);
    }
}
