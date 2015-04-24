package test.com.comms;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.comms.*;
import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Parser Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 20, 2015</pre>
 */
public class GameIDTest
{
    LwjglApplication lwjglApplication;
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception{
    }

    @Test
    public void testGameIDGeneration() throws Exception{
        ArrayList<GameID> seen = new ArrayList<GameID>();
        for (int i=0; i<200; i++) {
            GameID n = new GameID();
            assertTrue("check previous values", !seen.contains(n));
            seen.add(n);
        }
    }

    @Test
    public void testCharTranslation() throws Exception{
        GameID origin = new GameID();
        char   idChar = origin.toChar();
        GameID result = new GameID(idChar);
        assertTrue("Test GameID translation", result.equals(origin));

        origin = new GameID((Number)42);
        idChar = origin.toChar();
        result = new GameID(idChar);
        assertTrue("Test GameID translation", result.equals(origin));
    }


}
