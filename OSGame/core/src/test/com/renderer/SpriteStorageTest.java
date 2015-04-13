package test.com.renderer;

import com.OSG.OSGame;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.renderer.SpriteStorage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * SpriteStorage Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 12, 2015</pre>
 */
public class SpriteStorageTest
{

    LwjglApplication lwjglApplication;

    @Before
    public void before() throws Exception
    {
        if(lwjglApplication == null)
        {
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            config.title = "Test";
            config.width = 960;
            config.height = 576;
            lwjglApplication = new LwjglApplication(new ApplicationAdapter()
            {
                @Override
                public void create()
                {
                    super.create();
                }
            }, config);
        }
    }

    @After
    public void after() throws Exception
    {

    }

    /**
     * Method: getInstance()
     */
    @Test
    public void testGetInstance() throws Exception
    {
        assertNotNull(SpriteStorage.getInstance());
    }

    /**
     * Method: loadAssets()
     */
    @Test
    public void testLoadAssets() throws Exception
    {
        try
        {
            SpriteStorage.getInstance().loadAssets();
            assertTrue("SpriteStorage loaded assets without error", true);
        } catch (Exception e)
        {
            assertTrue(e.getMessage(), false);
        }
    }

    /**
     * Method: getTexture(String code)
     */
    @Test
    public void testGetTexture() throws Exception
    {
        TextureIO
        assertNotNull(SpriteStorage.getInstance().getTexture(" "));
        assertNotNull(SpriteStorage.getInstance().getTexture("X"));
        assertNotNull(SpriteStorage.getInstance().getTexture("^"));
        assertNotNull(SpriteStorage.getInstance().getTexture("v"));
        assertNotNull(SpriteStorage.getInstance().getTexture("@"));
        assertNotNull(SpriteStorage.getInstance().getTexture("Thomas"));
        assertNotNull(SpriteStorage.getInstance().getTexture("L"));
        assertNotNull(SpriteStorage.getInstance().getTexture("D"));
        assertNotNull(SpriteStorage.getInstance().getTexture("R"));
        assertNotNull(SpriteStorage.getInstance().getTexture("C"));
        assertNotNull(SpriteStorage.getInstance().getTexture("M"));
        assertNotNull(SpriteStorage.getInstance().getTexture("S"));
        assertNotNull(SpriteStorage.getInstance().getTexture("."));
        assertNotNull(SpriteStorage.getInstance().getTexture("|"));
        assertNotNull(SpriteStorage.getInstance().getTexture("l"));
        assertNotNull(SpriteStorage.getInstance().getTexture("d"));
        assertNotNull(SpriteStorage.getInstance().getTexture("r"));
        assertNotNull(SpriteStorage.getInstance().getTexture("c"));
        assertNotNull(SpriteStorage.getInstance().getTexture("s"));
        assertNotNull(SpriteStorage.getInstance().getTexture("T"));
        assertNotNull(SpriteStorage.getInstance().getTexture("/"));
    }
}
