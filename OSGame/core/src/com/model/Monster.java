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

    protected long lastUpdateTime;//when they last moved
    protected long UPDATE_INTERVAL=100;//how often a monster gets to move
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
        dead="BlueSplat";
        splat= splatSound;
        wasDead=true;
        closestPlayer=getClosestPlayer();
    }

    //check if the monster is dead or not
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
        //cant move if the monster is dead
        if(isDead()==true) 
            return;
        //not dead but just respawning
        else if(wasDead==true)
        {
            //set image and rand X/Y
            wasDead=false;
            changeSprite(alive);
            int x,y;
            //loop to get new respawn coordinates anywhere in the map at random
            do
            {
                x=randomGen.nextInt(map.getXBound());
                y=randomGen.nextInt(map.getYBound());
            }while(map.isWalkable(x,y)==false);
            position = new Position(x,y);
        }
        else //been alive, time to move
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
            if(squished()==true) //is there a player on the bug
            {
                deathTime=TimeUtils.millis();
                changeSprite(dead);
                if(splat != null) splat.play(); //if already playing the sound, dont do it again
                wasDead=true;
            }
            else if(TimeUtils.millis()-lastUpdateTime > UPDATE_INTERVAL)
            {
                lastUpdateTime=TimeUtils.millis();
                //move in a random direction
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
    
    //return true if a player is on the same X,Y spot
    public boolean squished()
    {
        int x,y;
        for(Player player : gameState.getPlayers()){
            x=player.getXPos();
            y=player.getYPos();
            if(getXPos()==x && getYPos()==y)
            {
                return true;
            }
        }
        return false;
    }
    
    public Player getClosestPlayer()
    {
        //traverse a Collection of players to find the nearest one 
        Collection<Player> players = gameState.getPlayers();
        Player closest = null;
        int minDist = Integer.MAX_VALUE;
        for(Player player : players){
            int dist = (abs(player.getXPos()-getXPos()) + abs(player.getYPos()-getYPos()));
            if(dist < minDist){
                closest = player;
                minDist = dist;
            }
        }
        return closest;
    }
}
