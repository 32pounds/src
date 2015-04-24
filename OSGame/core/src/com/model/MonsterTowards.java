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
            else if(TimeUtils.millis()-lastUpdateTime > UPDATE_INTERVAL)
            {
                lastUpdateTime=TimeUtils.millis();
                if(closestPlayer==null) 
                    return;
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
