package test.com.comms;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.comms.GameID;
import com.comms.GameState;
import com.comms.Parser;
import com.map.Map;
import com.model.Entity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Parser Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 19, 2015</pre>
 */
public class ParserTest
{

    LwjglApplication lwjglApplication;

    @Before
    public void before() throws Exception
    {
        if (lwjglApplication == null)
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
        lwjglApplication.exit();
    }

    /**
     * Method: Parse(Entity[] entities)
     */
    @Test
    public void testParse() throws Exception
    {

        String resultString = "0, ,0,2,2,@,0,2,3,%,0,2,#,&,0,2,";

        Entity[] entities = new Entity[4];

        Entity entity = new Entity(new GameState(new Map()), "0");
        entity.assignID(new GameID(' '));
        entity.setYPos(2);
        entity.setXPos(0);
        entities[0] = entity;

        entity = new Entity(new GameState(new Map()), "2");
        entity.assignID(new GameID('@'));
        entity.setYPos(2);
        entity.setXPos(0);
        entities[1] = entity;

        entity = new Entity(new GameState(new Map()), "3");
        entity.assignID(new GameID('%'));
        entity.setYPos(2);
        entity.setXPos(0);
        entities[2] = entity;

        entity = new Entity(new GameState(new Map()), "#");
        entity.assignID(new GameID('&'));
        entity.setYPos(2);
        entity.setXPos(0);
        entities[3] = entity;

        String result = new Parser(new GameState(new Map())).Parse(entities);

        org.junit.Assert.assertEquals(resultString, result);

    }

    /**
     * Method: DePerse(String data)
     */
    @Test
    public void testDePerse() throws Exception
    {
        Entity[] entities = new Parser(new GameState(new Map())).DePerse("0, ,0,2,2,@,0,2,3,%,0,2,#,&,0,2,");

        assertEquals(4, entities.length);

        assertEquals(entities[0].getImageCode(), "0");
        assertEquals(entities[0].getID().toChar(), ' ');
        assertEquals(entities[0].getYPos(), 2);
        assertEquals(entities[0].getXPos(), 0);

        assertEquals(entities[1].getImageCode(), "2");
        assertEquals(entities[1].getID().toChar(), '@');
        assertEquals(entities[1].getYPos(), 2);
        assertEquals(entities[1].getXPos(), 0);

        assertEquals(entities[2].getImageCode(), "3");
        assertEquals(entities[2].getID().toChar(), '%');
        assertEquals(entities[2].getYPos(), 2);
        assertEquals(entities[2].getXPos(), 0);

        assertEquals(entities[3].getImageCode(), "#");
        assertEquals(entities[3].getID().toChar(), '&');
        assertEquals(entities[3].getYPos(), 2);
        assertEquals(entities[3].getXPos(), 0);

    }


    /**
     * Method: StepString(String data)
     */
    @Test
    public void testStepString() throws Exception
    {
//TODO: Test goes here... 
/* 
try { 
   Method method = Parser.getClass().getMethod("StepString", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: CountChar(String string, char iChar)
     */
    @Test
    public void testCountChar() throws Exception
    {
//TODO: Test goes here... 
/* 
try { 
   Method method = Parser.getClass().getMethod("CountChar", String.class, char.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
