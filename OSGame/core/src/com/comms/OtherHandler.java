package com.comms;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.map.Direction;
import com.comms.GameID;
import com.comms.*;
/**
 * Handle the player input
 */
public class OtherHandler implements InputProcessor {
    GameID actor;

    public OtherHandler(GameID target){
        actor = target;
    }

    @Override
    public boolean keyDown(int keycode) {
        Command result = new DummyCmd();
        boolean keyRegistered = true;
        switch (keycode) {
            case Input.Keys.PERIOD:
            case Input.Keys.W:
                result = new MoveCmd(actor, Direction.NORTH);
                break;
            case Input.Keys.E:
            case Input.Keys.S:
                result = new MoveCmd(actor, Direction.SOUTH);
                break;
            case Input.Keys.O:
            case Input.Keys.A:
                result = new MoveCmd(actor, Direction.WEST);
                break;
            case Input.Keys.U:
            case Input.Keys.D:
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
            case Input.Keys.PERIOD:
            case Input.Keys.W:
                result = new StopCmd(actor, Direction.NORTH);
                break;
            case Input.Keys.E:
            case Input.Keys.S:
                result = new StopCmd(actor, Direction.SOUTH);
                break;
            case Input.Keys.O:
            case Input.Keys.A:
                result = new StopCmd(actor, Direction.WEST);
                break;
            case Input.Keys.U:
            case Input.Keys.D:
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
