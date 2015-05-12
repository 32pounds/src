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
        //if dead do nothing
        if(isDead()==true)
            return;
        //if just came to life
        else if(wasDead==true)
        {
            //set image and rand X/Y
            wasDead=false; //The monster is now alive
            changeSprite(alive); //change sprite back to which type of bug it was
            int x,y;
            //find random X,Y spot on the map
            do
            {
                x=randomGen.nextInt(map.getXBound());
                y=randomGen.nextInt(map.getYBound());
            }while(map.isWalkable(x,y)==false);
            position = new Position(x,y);
        }
        else
        {
            //is there a player on the monster
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
                //if there is no players, do nothing
                if(closestPlayer==null)
                    return;
                //move towards the nearest player
                if(closestPlayer.getXPos()>getXPos())
                    super.move(Direction.EAST);
                else if(closestPlayer.getXPos()<getXPos())
                    super.move(Direction.WEST);
                else if(closestPlayer.getYPos()>getYPos())
                    super.move(Direction.NORTH);
                else
                    super.move(Direction.SOUTH);
            }
        }
    }
}
