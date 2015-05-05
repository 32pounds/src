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
import java.util.Collection;

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
        //has enough time passed since the monster died?
        if(TimeUtils.millis()-deathTime>WAIT_TIME)
            return false;
        
        return true;
    }

    public void changeDeath(String img)
    {
        //switch to the monsteres new death imaige
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
            //if a player was on your square, you should die
            if(squished()==true)
            {
                deathTime=TimeUtils.millis();//update death time to now
                changeSprite(dead);//put dead sprite image
                if(splat != null) splat.play(); //check to play a sound effect
                wasDead=true; //update death state
            }
            //move if enough time has passed
            else if(TimeUtils.millis()-lastUpdateTime > UPDATE_INTERVAL)
            {
                lastUpdateTime=TimeUtils.millis();
                //this is to chose a random direction and move in it
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
        for(Player player : gameState.getPlayers()){ //traverse the players
            x=player.getXPos();
            y=player.getYPos();
            if(getXPos()==x && getYPos()==y) //return if dead
            {
                return true;
            }
        }
        return false;
    }
    
    public Player getClosestPlayer()
    {
        Collection<Player> players = gameState.getPlayers(); //list of players
        Player closest = null; //nearest player starts as no one 
        int minDist = Integer.MAX_VALUE; //this is so that the nearest player has a closer distance
        for(Player player : players){ //traverse the players list
            int dist = (abs(player.getXPos()-getXPos()) + abs(player.getYPos()-getYPos())); //calculate distance function
            if(dist < minDist){ //update distance and player
                closest = player;
                minDist = dist;
            }
        }
        return closest; //returns the nearest player
    }
}
