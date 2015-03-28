package com.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.comms.Command;
import com.comms.CommandHandler;
import com.map.Direction;
import com.map.Map;
import com.model.Entity;
import com.renderer.Drawable;
import com.renderer.SpriteStorage;
import com.renderer.Updatable;

public class Player extends Entity implements Updatable {
    public Player(Map map, String texture){
        super(map, texture);
    }

    public void update(){

    }
}
