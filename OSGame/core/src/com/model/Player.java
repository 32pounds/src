package com.model;

import com.comms.*;
import com.map.*;
import com.renderer.Updatable;
import com.badlogic.gdx.utils.TimeUtils;

public class Player extends Entity implements Updatable {
    private static final long UPDATE_INTERVAL = 100;
    private long lastUpdateTime;
    private Direction dir;

    //sets up player texture and start position
    public Player(GameState state, String texture){
        super(state, texture);
        lastUpdateTime = TimeUtils.millis();
        Position mapStartPos = gameState.gameMap().findFirstInstance('@');
        position = mapStartPos;
    }

    //updates player location
    public void update(){
        Map map = gameState.gameMap();
        if(TimeUtils.millis()-lastUpdateTime > UPDATE_INTERVAL){
            if(dir != null) move(dir);
            lastUpdateTime = TimeUtils.millis();
        }
        int x = getXPos();
        int y = getYPos();
        Position mapStartPos;

        //if teleport
        if (map.isMove(x, y) != 0) {
            //teleport up
            if (map.isMove(x, y) == 1) {
                mapStartPos = map.findPreviousInstance(x, y, 'g');
                position = mapStartPos;
                x = getXPos();
                position.setX(--x);
            }
            //teleport down
            else if (map.isMove(x, y) == 2) {
                mapStartPos = map.findNextInstance(x, y, 'o');
                position = mapStartPos;
                x = getXPos();
                position.setX(--x);
            }
        }
    }

    //sets direction player is moving
    public void setMovingDir(Direction direction){
        dir = direction;
        move(dir);
        lastUpdateTime = TimeUtils.millis();
    }

    public void stopMoving(Direction direction){
        if(dir==direction) dir = null;
    }
}
