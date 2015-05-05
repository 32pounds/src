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
public class Virus extends Monster
{
    public Map map;
    VirusManager manager;
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
    Direction best;
    int aggro;
    int rotation;
    
    public Virus(GameState state, String img, Sound splatSound, VirusManager mngr) 
    {
        super(state, img, splatSound);
        super.changeAlive("Virus");
        super.changeDeath("Blank");
	//super.changeSplat("");
        UPDATE_INTERVAL=240+randomGen.nextInt(20);
        //position = pos;
        map = gameState.gameMap();
        manager = mngr;
        xBound = map.getXBound();
        yBound = map.getCyberYStart();
        aggro = 8;
        rotation = super.getRotation();
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
                myX = getXPos();
                myY = getXPos();
                int playerX = closestPlayer.getXPos();
                int playerY = closestPlayer.getYPos();

                if ( (abs(relativeY) < aggro) || (abs(relativeX) < aggro)){
                    // player too close - attack
                    setBestDirections();
                    moveBest();
                }
                else {
                    // wander freely
                    wander();
                }
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
        /* Remember, the map coordinates don't match our standard understanding,
           so these values might not seem to make sense.
           The directions are named by the movement observed during gameplay.
        */

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
    
    private void wander() {
        rotation = super.getRotation();
        int dir=randomGen.nextInt(4);
        switch (dir) {
            case 0:
                // try North
                if (rotation == 180) {
                    // is facing South - go straight
                    best = Direction.SOUTH;
                }
                else best = Direction.NORTH;
                break;
            case 1:
                // try East
                if (rotation == 90) {
                    // is facing West - go straight
                    best = Direction.WEST;
                }
                else best = Direction.EAST;
                break;
            case 2:
                // try South
                if (rotation == 0) {
                    // is facing North - go straight
                    best = Direction.NORTH;
                }
                else best = Direction.SOUTH;                
                break;
            default:
                // try West
                if (rotation == 270) {
                    // is facing East - go straight
                    best = Direction.EAST;
                }
                else best = Direction.WEST;                
                break;
        }
        super.move(best);
    }
}
