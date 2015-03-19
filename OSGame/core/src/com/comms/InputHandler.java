package com.comms;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Handle the player input
 */
public class InputHandler implements InputProcessor {

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                CommandHandler.getInstance().add(Command.MOVE_UP);
                break;
            case Input.Keys.DOWN:
                CommandHandler.getInstance().add(Command.MOVE_DOWN);
                break;
            case Input.Keys.LEFT:
                CommandHandler.getInstance().add(Command.MOVE_LEFT);
                break;
            case Input.Keys.RIGHT:
                CommandHandler.getInstance().add(Command.MOVE_RIGTH);
                break;
        }

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
