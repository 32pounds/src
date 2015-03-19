package com.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.comms.Command;
import com.comms.CommandHandler;
import com.map.Map;
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

    @Override
    public void draw(SpriteBatch batch) {
        //using bacth.draw instead of sprite.draw cuz I dont want apply every property of the sprite (like position)
        batch.draw(sprite, x * XDIMENSION, y * YDIMENSION, sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
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

        //this whole logic should be placed on server side

        //getting command from the queue
        //this function will be called here ultil we finish the game state,
        //after that the position will be got on game state
        Command command = CommandHandler.getInstance().remove();

        //if there no command is not needed to be updated
        if (command == null) return;

        //obvious

        switch (command) {
            case MOVE_DOWN:
                //check if the next step is a valid step
                if (map.getCode(x, y - 1) == ' ')
                    y--;

                //set a rotation toward to the step
                sprite.setRotation(180);
                break;

            case MOVE_UP:
                if (map.getCode(x, y + 1) == ' ')
                    y++;
                sprite.setRotation(0);
                break;

            case MOVE_LEFT:
                if (map.getCode(x - 1, y) == ' ')
                    x--;
                sprite.setRotation(90);
                break;

            case MOVE_RIGTH:
                if (map.getCode(x + 1, y) == ' ')
                    x++;
                sprite.setRotation(270);
                break;
        }

    }
}