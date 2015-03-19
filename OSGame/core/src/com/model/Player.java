package com.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.comms.Command;
import com.comms.CommandHandler;
import com.renderer.Drawable;
import com.renderer.SpriteStorage;
import com.renderer.Updatable;

/**
 * This function represent the player
 * <p>This class will be edited when we add the game state</p>
 */
public class Player extends Drawable implements Updatable {

    //sprite of the player
    //I am using a Sprite cuz texture doesn't have rotation
    private Sprite sprite;

    //initial position of the player
    private int x = 1;
    private int y = 16;

    //dimension of a tile, this is used to calc the position of the player
    private final int XDIMENSION = 32;
    private final int YDIMENSION = 32;


    public Player() {

        //using sprite cuz Texture doesn't have rotation
        sprite = new Sprite(SpriteStorage.getInstance().getTexture("@"));
    }

    @Override
    public void draw(SpriteBatch batch) {
        //using bacth.draw instead of sprite.draw cuz I dont want apply every property of the sprite (like position)
        batch.draw(sprite, x*XDIMENSION, y*YDIMENSION, sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
    }

    @Override
    public int getZIndex() {
        //the player will be drawn on of map, chant it if necessary
        return 1;
    }

    @Override
    public boolean isExpired() {
        //for now this will be false
        return false;
    }

    @Override
    public void update() {

        //getting command from the queue
        //this function will be called here ultil we finish the game state,
        //after that the position will be got on game state
        Command command = CommandHandler.getInstance().remove();

        //if there no command is not needed to be updated
        if (command == null) return;

        //obvious
        switch (command) {
            case MOVE_DOWN:
                y--;
                sprite.setRotation(180);
                break;
            case MOVE_UP:
                y++;
                sprite.setRotation(0);
                break;

            case MOVE_LEFT:
                x--;
                sprite.setRotation(90);
                break;

            case MOVE_RIGTH:
                x++;
                sprite.setRotation(270);
                break;
        }

    }
}