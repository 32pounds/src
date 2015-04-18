/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.TimeUtils;
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
    public MonsterTowards(GameState state, String img, Entity killer, Sound splatSound) 
    {
        super(state, img, killer, splatSound);
        super.changeDeath("!");
        UPDATE_INTERVAL=250;
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
                    super.move(Direction.EAST);
                else if(hunter.getXPos()<getXPos())
                    super.move(Direction.WEST);
                else if(hunter.getYPos()>getYPos())
                    super.move(Direction.NORTH);
                else
                    super.move(Direction.SOUTH);
            }
        }
    }
}
