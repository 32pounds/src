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
        Command result = new DummyCmd();
        boolean keyRegistered = true;
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
            default:
                keyRegistered = false;
        }
        if(keyRegistered) CommandHandler.getInstance().add(result);

        return keyRegistered;
    }

    @Override
    public boolean keyUp(int keycode) {
        Command result = new DummyCmd();
        boolean keyRegistered = true;
        switch (keycode) {
            case Input.Keys.UP:
                result = new StopCmd(actor, Direction.NORTH);
                break;
            case Input.Keys.DOWN:
                result = new StopCmd(actor, Direction.SOUTH);
                break;
            case Input.Keys.LEFT:
                result = new StopCmd(actor, Direction.WEST);
                break;
            case Input.Keys.RIGHT:
                result = new StopCmd(actor, Direction.EAST);
                break;
            default:
                keyRegistered = false;
        }
        if(keyRegistered) CommandHandler.getInstance().add(result);

        return keyRegistered;
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
