package com.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.comms.*;
import com.map.*;
import com.model.Entity;
import com.renderer.Drawable;
import com.renderer.SpriteStorage;
import com.renderer.Updatable;
import com.badlogic.gdx.utils.TimeUtils;

public class Player extends Entity implements Updatable {
    private static final long UPDATE_INTERVAL = 100;
    private long lastUpdateTime;
    private Direction dir;

    public Player(GameState state, String texture){
        super(state, texture);
        lastUpdateTime = TimeUtils.millis();
        Position mapStartPos = gameState.gameMap().findFirstInstance('@');
        position = mapStartPos;
    }

    public void update(){
        Map map = gameState.gameMap();
        if(TimeUtils.millis()-lastUpdateTime > UPDATE_INTERVAL){
            if(dir != null) move(dir);
            checkTeleport();
            lastUpdateTime = TimeUtils.millis();
        }

                
            /*if (map.isMove(x, y) == 1) {
                //teleportTarget = map.findPreviousInstance(x, y, 'g');
                teleportTarget = map.getPadToGround();
                position = teleportTarget;
                x = getXPos();
                position.setX(--x);
            }
            else if (map.isMove(x, y) == 2) {
                //teleportTarget = map.findNextInstance(x, y, 'o');
                teleportTarget = map.getPadToStation();
                position = teleportTarget;
                x = getXPos();
                position.setX(--x);
            }*/
        
    }
    
    public void checkTeleport(){
        int x = getXPos();
        int y = getYPos();
        Position teleportTarget = map.getPadToGround();

        int teleport = map.isMove(x, y);

        if (teleport != 0) {
            switch (teleport) {
                case 1:  // orange pad, to station
                    teleportTarget = map.getPadToGround();
                    teleportTarget.setX(teleportTarget.getX()+1);
                    break;
                case 2:  // green pad, to ground
                    teleportTarget = map.getPadToStation();
                    teleportTarget.setX(teleportTarget.getX()-1);
                    break;
                case 3:  // cyber entry, to cyber
                    teleportTarget = map.getCyberExit();
                    teleportTarget.setY(teleportTarget.getY()-1);
                    break;
                case 4:  // cyber exit, to entry
                    teleportTarget = map.getCyberEntry();
                    teleportTarget.setY(teleportTarget.getY()+1);
                    break;
            }
            
            //position = teleportTarget;
            x = teleportTarget.getX();
            y = teleportTarget.getY();
            position.setX(x);
            position.setY(y);
        }
    }

    public void setMovingDir(Direction direction){
        dir = direction;
        move(dir);
        //checkTeleport();
        lastUpdateTime = TimeUtils.millis();
    }

    public void stopMoving(Direction direction){
        if(dir==direction) dir = null;
        //checkTeleport();
    }
}
