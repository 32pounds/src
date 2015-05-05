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

/**
 *
 * @author michael
 */
public class MonsterTowards extends Monster
{
    public MonsterTowards(GameState state, String img, Sound splatSound) 
    {
        super(state, img, splatSound);
        super.changeDeath("!");
        UPDATE_INTERVAL=250;
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
            //generate a random valid X,Y spot to spawn
            do
            {
                x=randomGen.nextInt(map.getXBound());
                y=randomGen.nextInt(map.getYBound());
            }while(map.isWalkable(x,y)==false);
            position = new Position(x,y);
        }
        else
        {
            //check to see if it is dead
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
                if(closestPlayer==null) //if no players in the game, do nothing
                    return;
                if(closestPlayer.getXPos()>getXPos()) //if player on the right, move right
                    super.move(Direction.EAST);
                else if(closestPlayer.getXPos()<getXPos()) //if player on left, move left
                    super.move(Direction.WEST);
                else if(closestPlayer.getYPos()>getYPos()) //if player above you, move up
                    super.move(Direction.NORTH);
                else
                    super.move(Direction.SOUTH); //player must be below you
            }
        }
    }
}
