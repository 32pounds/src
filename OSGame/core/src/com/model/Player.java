package com.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.comms.Command;
import com.comms.CommandHandler;
import com.renderer.Drawable;
import com.renderer.SpriteStorage;
import com.renderer.Updatable;

/**
 * Created by gabrielgiovaninidesouza on 3/16/15.
 */
public class Player extends Drawable implements Updatable {

    private int x = 0;
    private int y = 0;

    private final int XDIMENSION = 32;
    private final int YDIMENSION = 32;

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(SpriteStorage.getInstance().getTexture("@"), x * XDIMENSION, y * YDIMENSION);
    }

    @Override
    public int getZIndex() {
        return 1;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public void update() {

        Command command = CommandHandler.getInstance().remove();

        if(command ==  null) return;

        switch (command) {
            case MOVE_DOWN:
                y--;
                break;
            case MOVE_UP:
                y++;
                break;

            case MOVE_LEFT:
                x--;
                break;

            case MOVE_RIGTH:
                x++;
                break;
        }

    }
}
