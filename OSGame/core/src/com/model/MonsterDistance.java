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

    public MonsterDistance(GameState state, String img, GameID killer, Sound splatSound) {
        super(state, img, killer, splatSound);
        super.changeDeath("#");
        UPDATE_INTERVAL=150;
    }

    
    
    @Override
    public void update()
    {
        Map map=gameState.gameMap();
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
            if(getXPos()==hunter.getXPos() && getYPos()==hunter.getYPos())
            {
                deathTime=TimeUtils.millis();
                changeSprite(dead);
                splat.play();
                wasDead=true;
            }
            else if(TimeUtils.millis()-lastUpdateTime > UPDATE_INTERVAL)
            {
                lastUpdateTime=TimeUtils.millis();
                if(hunter.getXPos()>getXPos())
                {
                    if(map.isWalkable(getXPos()-1,getYPos())==true)
                        super.move(Direction.WEST);
                    else if(hunter.getYPos()>getYPos()) 
                    {
                        super.move(Direction.SOUTH);
                    }
                    else //if(hunter.getYPos()<getYPos()) 
                    {
                        super.move(Direction.NORTH);
                    }
                }
                else if(hunter.getXPos()<getXPos())
                {
                    if(map.isWalkable(getXPos()+1,getYPos())==true)
                        super.move(Direction.EAST);
                    else if(hunter.getYPos()>getYPos()) 
                    {
                        super.move(Direction.SOUTH);
                    }
                    else //if(hunter.getYPos()<getYPos()) 
                    {
                        super.move(Direction.NORTH);
                    }
                }
                else if(hunter.getYPos()>getYPos())
                {
                    if(map.isWalkable(getXPos(),getYPos()-1)==true)
                        super.move(Direction.SOUTH);
                    else if(hunter.getXPos()>getXPos()) 
                    {
                        super.move(Direction.WEST);
                    }
                    else //if(hunter.getYPos()<getYPos()) 
                    {
                        super.move(Direction.EAST);
                    }
                }
                else
                {
                    if(map.isWalkable(getXPos(),getYPos()+1)==true)
                        super.move(Direction.NORTH);
                    else if(hunter.getXPos()>getXPos()) 
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
