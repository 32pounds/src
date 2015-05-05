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
import static java.lang.Math.abs;

/**
 *
 * @author elizabeth
 */
public class VirusQueen extends Monster
{
    VirusManager manager;
    Map map;
    int myX;
    int myY;
    int relativeX;
    int relativeY;
    int xStep;
    int yStep;
    Direction bestX;
    Direction bestY;
    int safety;
    
    public VirusQueen(GameState state, String img, Sound splatSound, VirusManager mngr) 
    {
        super(state, img, splatSound);
        super.changeAlive("VirusQueen");
        super.changeDeath("Blank");
        manager = mngr;
        map=gameState.gameMap();
	//super.changeSplat("");
        UPDATE_INTERVAL=290+randomGen.nextInt(20);
        //position = pos;
        safety = 5;
        
    }
    
    @Override
    public void update()
    {
        if(isDead()==true)
            return;
        map=gameState.gameMap();
        closestPlayer=getClosestPlayer();

        if(wasDead==true)
        {
            //set image and rand X/Y
            wasDead=false;
            changeSprite(alive);
            int x,y;
            do
            {
                x=randomGen.nextInt(map.getXBound());
                y=randomGen.nextInt(map.getYBound());
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

            // check if it's time to update
            else if(TimeUtils.millis()-lastUpdateTime > UPDATE_INTERVAL)
            {   // reset update timer
                lastUpdateTime=TimeUtils.millis();
                
                // seek player to chase
                if(closestPlayer==null) 
                    return;

                // get coordinates of self and closest player
                myX = getXPos();
                myY = getYPos();
                relativeX = closestPlayer.getXPos() - myX;
                relativeY = closestPlayer.getYPos() - myY;

                if ( abs(relativeY) < safety || abs(relativeX) < safety){
                    // player too close - flee
                    setBestDirections();
                    moveBest();
                }
                else {
                    // spawn a virus
                    
                    // wander
                    
                }
            }
        }
    }
    
    // find best directions to flee closest player
    private void setBestDirections () {
        // they are to the east - go west
        if(relativeX > 0) {
            bestX = Direction.WEST;
            xStep = -1;
        }
        // they are to the west - go east
        else {
            bestX = Direction.EAST;
            xStep = 1;
        }
                
        // they are to the north - go south
        if(relativeY > 0) {
            bestY = Direction.SOUTH;
            yStep = -1;
        }
        // they are to the south - go north
        else {
            bestY = Direction.NORTH;
            yStep = 1;
        }
    }
    
    private void moveBest() {
        /* Remember, the map coordinates don't match our standard understanding,
           so these values might not seem to make sense.
           The directions are named by the movement observed during gameplay.
        */
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


