/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.TimeUtils;
import com.map.*;
import com.comms.*;
import com.renderer.Updatable;
import java.util.Random;

/**
 *
 * @author michael
 */
public class Monster extends Entity implements Updatable{

    protected long lastUpdateTime;
    protected long UPDATE_INTERVAL=100;
    protected long WAIT_TIME=2000;
    protected long deathTime;
    protected static Random randomGen= new Random();
    protected Entity hunter;
    protected String alive,dead;
    protected Sound splat;
    protected boolean wasDead;

    public Monster(GameState state, String img, Entity killer, Sound splatSound)
    {
        super(state, img);
        lastUpdateTime=0;
        hunter=killer;
        deathTime=-WAIT_TIME;
        alive=img;
        dead="S";
        splat= splatSound;
        wasDead=true;
    }

    public boolean isDead()
    {
        if(TimeUtils.millis()-deathTime>WAIT_TIME)
            return false;
        return true;
    }
    
    public void changeDeath(String img)
    {
        dead=img;
    }

    @Override
    public void update()
    {
        Map map = gameState.gameMap();
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
                int dir=randomGen.nextInt(4);
                if(dir==0)
                    super.move(Direction.SOUTH);
                else if(dir==1)
                    super.move(Direction.NORTH);
                else if(dir==2)
                    super.move(Direction.EAST);
                else
                    super.move(Direction.WEST);
            }
        }
    }
}
