package test.com.comms;

import com.badlogic.gdx.InputProcessor;
import com.comms.OSInputProcessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * OSInputProcessor Tester.
 */
public class OSInputProcessorTest implements InputProcessor
{
    int count = 0;

    @Before
    public void before() throws Exception
    {
        count = 0;
    }

    /**
     * Method: getInstance()
     */
    @Test
    public void testGetInstance() throws Exception
    {
        assertNotNull(OSInputProcessor.getInstance());
    }

    /**
     * Method: addInputPorcessor(InputProcessor inputProcessor)
     */
    @Test
    public void testAddInputPorcessor() throws Exception
    {
        OSInputProcessor.getInstance().addInputPorcessor(this);
        OSInputProcessor.getInstance().keyDown(0);
        OSInputProcessor.getInstance().keyUp(0);
        OSInputProcessor.getInstance().keyTyped(' ');
        OSInputProcessor.getInstance().touchDown(0, 0, 0, 0);
        OSInputProcessor.getInstance().touchUp(0, 0, 0, 0);
        OSInputProcessor.getInstance().touchDragged(0, 0, 0);
        OSInputProcessor.getInstance().mouseMoved(0, 0);
        OSInputProcessor.getInstance().scrolled(0);

        assertEquals(8, count);
    }

    /**
     * Method: removeInputPorcessor(InputProcessor inputProcessor)
     */
    @Test
    public void testRemoveInputPorcessor() throws Exception
    {
        OSInputProcessor.getInstance().addInputPorcessor(this);
        OSInputProcessor.getInstance().removeInputPorcessor(this);
        OSInputProcessor.getInstance().keyDown(0);
        OSInputProcessor.getInstance().keyUp(0);
        OSInputProcessor.getInstance().keyTyped(' ');
        OSInputProcessor.getInstance().touchDown(0, 0, 0, 0);
        OSInputProcessor.getInstance().touchUp(0, 0, 0, 0);
        OSInputProcessor.getInstance().touchDragged(0, 0, 0);
        OSInputProcessor.getInstance().mouseMoved(0, 0);
        OSInputProcessor.getInstance().scrolled(0);

        assertEquals(0, count);
    }

    @Override
    public boolean keyDown(int keycode)
    {
        count++;
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        count++;
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        count++;
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        count++;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        count++;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        count++;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        count++;
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        count++;
        return false;
    }
}
