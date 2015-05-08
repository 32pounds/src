package test.com.comms;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.comms.*;
import com.map.Map;
import com.model.Entity;
import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * DummyCmd Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 15, 2015</pre>
 */
public class DummyCmdTest
{
    @Before
    public void before() throws Exception
    {
    }

    @After
    public void after() throws Exception
    {
    }

    /**
     * Method: getData()
     */
    @Test
    public void testGetData() throws Exception
    {
        DummyCmd dummyCmd = new DummyCmd();
        assertEquals("com.comms.DummyCmd:test", dummyCmd.getData());
    }


} 
