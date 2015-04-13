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
            lastUpdateTime = TimeUtils.millis();
        }
        int x = getXPos();
        int y = getYPos();
        Position mapStartPos;

        if (map.isMove(x, y) != 0) {
            if (map.isMove(x, y) == 1) {
                mapStartPos = map.findPreviousInstance(x, y, 'v');
                position = mapStartPos;
                x = getXPos();
                position.setX(--x);
            }
            else if (map.isMove(x, y) == 2) {
                mapStartPos = map.findNextInstance(x, y, '^');
                position = mapStartPos;
                x = getXPos();
                position.setX(--x);
            }
        }
    }

    public void setMovingDir(Direction direction){
        dir = direction;
        move(dir);
        lastUpdateTime = TimeUtils.millis();
    }

    public void stopMoving(Direction direction){
        if(dir==direction) dir = null;
    }
}
