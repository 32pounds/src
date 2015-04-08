package com.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.comms.Command;
import com.comms.CommandHandler;
import com.map.*;
import com.model.Entity;
import com.renderer.Drawable;
import com.renderer.SpriteStorage;
<<<<<<< HEAD
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
=======

/**
 * This function represent the player
 * <p>This class will be edited when we add the game state</p>
 */
public class Player extends Drawable {

    //sprite of the player
    //I am using a Sprite cuz texture doesn't have rotation
    private Sprite sprite;

    //initial position of the player
    private int x = 16;
    private int y = 16;

    //this property is to provide a way to access map data,
    //after game state finished this property must be ripped off from here
    private Map map;

    /**
     * Provide a way to access map data, but after game state finished this property must be ripped off from here.
     * @param map Map
     */
    public Player(Map map) {

        //using sprite cuz Texture doesn't have rotation
        sprite = new Sprite(SpriteStorage.getInstance().getTexture("@"));

        //temporarily here
        this.map = map;
    }

    public int getXPos(){
        return x;
    }

    public int getYPos(){
        return y;
    }

    @Override
    public void draw(SpriteBatch batch) {
        //using bacth.draw instead of sprite.draw cuz I dont want apply every property of the sprite (like position)
        batch.draw(sprite, x * Map.XDIMENSION, y * Map.YDIMENSION, sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
>>>>>>> master
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
