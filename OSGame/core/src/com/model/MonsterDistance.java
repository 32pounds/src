/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.TimeUtils;
import com.comms.GameID;
import com.comms.GameState;
import com.map.Direction;
import com.map.Map;
import com.map.Position;
import static com.model.Monster.randomGen;

/**
 *
 * @author michael
 */
public class MonsterDistance extends Monster
{

    public MonsterDistance(GameState state, String img, Sound splatSound) {
        super(state, img, splatSound);
        super.changeDeath("#");
        UPDATE_INTERVAL=150;
    }

    
    
    @Override
    public void update()
    {
        Map map=gameState.gameMap();
        closestPlayer=getClosestPlayer();
        if(isDead()==true)
            return;
        else if(wasDead==true)
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
            //check if dead
            if(squished()==true)
            {
                deathTime=TimeUtils.millis();
                changeSprite(dead);
                splat.play();
                wasDead=true;
            }
            else if(TimeUtils.millis()-lastUpdateTime > UPDATE_INTERVAL)
            {
                lastUpdateTime=TimeUtils.millis();
                if(closestPlayer==null) //if no players are in the game, do nothing
                    return;
                //if player is on your right
                if(closestPlayer.getXPos()>getXPos())
                {
                    //try to move left
                    if(map.isWalkable(getXPos()-1,getYPos())==true)
                        super.move(Direction.WEST);
                    //if the player is above you, try to move down
                    else if(closestPlayer.getYPos()>getYPos()) 
                    {
                        super.move(Direction.SOUTH);
                    }
                    else //as a last resort, move up
                    {
                        super.move(Direction.NORTH);
                    }
                }
                //if player is on your left
                else if(closestPlayer.getXPos()<getXPos())
                {
                    //move right if possible
                    if(map.isWalkable(getXPos()+1,getYPos())==true)
                        super.move(Direction.EAST);
                    //if player is above you, move down
                    else if(closestPlayer.getYPos()>getYPos()) 
                    {
                        super.move(Direction.SOUTH);
                    }
                    else  
                    {
                        super.move(Direction.NORTH);
                    }
                }
                //if the player is above you
                else if(closestPlayer.getYPos()>getYPos())
                {
                    //try to move down
                    if(map.isWalkable(getXPos(),getYPos()-1)==true)
                        super.move(Direction.SOUTH);
                    //if the player is on your right run left
                    else if(closestPlayer.getXPos()>getXPos()) 
                    {
                        super.move(Direction.WEST);
                    }
                    else  
                    {
                        super.move(Direction.EAST);
                    }
                }
                else
                {
                    //the player must be below you, try to run up
                    if(map.isWalkable(getXPos(),getYPos()+1)==true)
                        super.move(Direction.NORTH);
                    //if the player is on your right, run left
                    else if(closestPlayer.getXPos()>getXPos()) 
                    {
                        super.move(Direction.WEST);
                    }
                    else //if(hunter.getYPos()<getYPos()) 
                    {
                        super.move(Direction.EAST);
                    }
                }
            }
        }
    }
}
