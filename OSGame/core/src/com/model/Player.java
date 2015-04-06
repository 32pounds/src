package com.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.comms.Command;
import com.comms.CommandHandler;
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

    public Player(Map map, String texture){
        super(map, texture);
        lastUpdateTime = TimeUtils.millis();
        Position mapStartPos = map.findFirstInstance('@');
        position = mapStartPos;
    }

    public void update(){
        if(TimeUtils.millis()-lastUpdateTime > UPDATE_INTERVAL){
            if(dir != null) move(dir);
            lastUpdateTime = TimeUtils.millis();
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
