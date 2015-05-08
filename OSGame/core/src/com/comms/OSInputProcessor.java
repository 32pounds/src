package com.comms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import java.util.ArrayList;

/**
 * This class handle multi input processor, in case of more tha two classes need to process input.
 */
public class OSInputProcessor implements InputProcessor
{
    private ArrayList<InputProcessor> processors = new ArrayList<InputProcessor>();

    private static OSInputProcessor instance;

    private OSInputProcessor()
    {
        Gdx.input.setInputProcessor(this);
    }

    /**
     * Get singleton instance
     *
     * @return Singleton instance
     */
    public static OSInputProcessor getInstance()
    {
        if (instance == null) instance = new OSInputProcessor();
        return instance;
    }

    /**
     * Add an InputProcessor to be updated.
     * @param inputProcessor InputProcessor to be updated.
     */
    public void addInputPorcessor(InputProcessor inputProcessor)
    {
        processors.add(inputProcessor);
    }

    /**
     * Remove InputProcessor from updated events.
     * @param inputProcessor Remove from updated events.
     */
    public void removeInputPorcessor(InputProcessor inputProcessor)
    {
        processors.remove(inputProcessor);
    }

    @Override
    public boolean keyDown(int keycode)
    {
        for (InputProcessor i : processors)
        {
            if (i.keyDown(keycode)) return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        for (InputProcessor i : processors)
        {
            if (i.keyUp(keycode)) return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        for (InputProcessor i : processors)
        {
            if (i.keyTyped(character)) return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        for (InputProcessor i : processors)
        {
            if (i.touchDown(screenX, screenY, pointer, button)) return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        for (InputProcessor i : processors)
        {
            if (i.touchUp(screenX, screenY, pointer, button)) return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        for (InputProcessor i : processors)
        {
            if (i.touchDragged(screenX, screenY, pointer)) return true;
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        for (InputProcessor i : processors)
        {
            if (i.mouseMoved(screenX, screenY)) return true;
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        for (InputProcessor i : processors)
        {
            if (i.scrolled(amount)) return true;
        }
        return false;
    }
}
