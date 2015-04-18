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
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author michael
 */
public class Monster extends Entity implements Updatable{

    protected long lastUpdateTime;
    protected long UPDATE_INTERVAL=100;
    protected static final long WAIT_TIME=2000;
    protected long deathTime;
    protected static Random randomGen= new Random();
    protected String alive,dead;
    protected Sound splat;
    protected Player closestPlayer;
    protected boolean wasDead;

    public Monster(GameState state, String img, Sound splatSound)
    {
        super(state, img);
        lastUpdateTime=0;
        deathTime=-WAIT_TIME;
        alive=img;
        dead="S";
        splat= splatSound;
        wasDead=true;
        closestPlayer=getClosestPlayer();
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
/*            Entity killer = state.getByID(hunter);
            if(killer == null) return;
            if(getXPos()==killer.getXPos() && getYPos()==killer.getYPos())
            {
                deathTime=TimeUtils.millis();
                changeSprite(dead);
                if(splat != null) splat.play();
                wasDead=true;
            }
            else */
            if(squished()==true)
            {
                deathTime=TimeUtils.millis();
                changeSprite(dead);
                if(splat != null) splat.play();
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
    public boolean squished()
    {
        int x,y;
        for(int i=0; i<gameState.playerList.size(); ++i)
        {
            x=gameState.playerList.get(i).getXPos();
            y=gameState.playerList.get(i).getYPos();
            if(getXPos()==x && getYPos()==y)
            {
                return true;
            }
        }
        
        return false;
    }
    public Player getClosestPlayer()
    {
        Player player=null;
        ArrayList<Integer> dist = new ArrayList<Integer>();
        if(gameState.playerList.size()>0)
        {   
            player=gameState.playerList.get(0);
            for(int i=0; i<gameState.playerList.size(); ++i)
            {
                dist.add(abs(gameState.playerList.get(i).getXPos()-getXPos()) + abs(gameState.playerList.get(i).getYPos()-getYPos()));
            }
            int num=dist.get(0);
            for(int i=0; i<dist.size(); ++i)
            {
                if(num<dist.get(i).intValue())
                {
                    player=gameState.playerList.get(i);
                    num=dist.get(i).intValue();
                }
            }
        }
        return player;
    }
}
