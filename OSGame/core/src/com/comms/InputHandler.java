package com.comms;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.map.Direction;
import com.model.Player;
import com.comms.*;
/**
 * Handle the player input
 */
public class InputHandler implements InputProcessor {
    Player actor;

    public InputHandler(Player target){
        actor = target;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        Command result = new DummyCmd();
        switch (keycode) {
            case Input.Keys.UP:
                result = new MoveCmd(actor, Direction.NORTH);
                break;
            case Input.Keys.DOWN:
                result = new MoveCmd(actor, Direction.SOUTH);
                break;
            case Input.Keys.LEFT:
                result = new MoveCmd(actor, Direction.WEST);
                break;
            case Input.Keys.RIGHT:
                result = new MoveCmd(actor, Direction.EAST);
                break;
        }
        CommandHandler.getInstance().add(result);

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
