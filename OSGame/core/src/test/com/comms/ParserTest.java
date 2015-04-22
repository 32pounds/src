package test.com.comms;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.comms.GameID;
import com.comms.GameState;
import com.comms.Parser;
import com.map.Map;
import com.model.Entity;
import static org.junit.Assert.assertEquals;

import javafx.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Parser Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 20, 2015</pre>
 */
public class ParserTest
{
    LwjglApplication lwjglApplication;

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
    }

    /**
     * Method: Parse(Entity[] entities, Command[] commands)
     */
    @Test
    public void testParseForEntitiesCommands() throws Exception
    {

    }

    /**
     * Method: Parse(Command[] commands)
     */
    @Test
    public void testParseCommands() throws Exception
    {
//TODO: Test goes here...
    }

    /**
     * Method: DeParseCommands(String data)
     */
    @Test
    public void testDeParseCommands() throws Exception
    {
/*        String test = "3,3,a,b,c,2,a,c,5,a,e,r,t,3,";
        char[][] result = new Parser(new GameState(new Map())).DeParseCommands(test);

        assertEquals('a', result[0][0]);
        assertEquals('b', result[0][1]);
        assertEquals('c', result[0][2]);

        assertEquals('a', result[1][0]);
        assertEquals('c', result[1][1]);

        assertEquals('a', result[2][0]);
        assertEquals('e', result[2][1]);
        assertEquals('r', result[2][2]);
        assertEquals('t', result[2][3]);
        assertEquals('3', result[2][4]);
*/
    }

    /**
     * Method: Parse(Entity[] entities)
     */
    @Test
    public void testParseEntities() throws Exception
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
     * Method: DePerseEntities(String data)
     */
    @Test
    public void testDePerseEntities() throws Exception
    {
        Entity[] entities = new Parser(new GameState(new Map())).DePerseEntities("0, ,0,22,2,@,0,2,3,%,0,2,#,&,0,2,");

        assertEquals(4, entities.length);

        assertEquals(entities[0].getSpriteString(), "0");
        assertEquals(entities[0].getID().toChar(), ' ');
        assertEquals(entities[0].getYPos(), 22);
        assertEquals(entities[0].getXPos(), 0);

        assertEquals(entities[1].getSpriteString(), "2");
        assertEquals(entities[1].getID().toChar(), '@');
        assertEquals(entities[1].getYPos(), 2);
        assertEquals(entities[1].getXPos(), 0);

        assertEquals(entities[2].getSpriteString(), "3");
        assertEquals(entities[2].getID().toChar(), '%');
        assertEquals(entities[2].getYPos(), 2);
        assertEquals(entities[2].getXPos(), 0);

        assertEquals(entities[3].getSpriteString(), "#");
        assertEquals(entities[3].getID().toChar(), '&');
        assertEquals(entities[3].getYPos(), 2);
        assertEquals(entities[3].getXPos(), 0);
    }

    /**
     * Method: DeParse(String data)
     */
    @Test
    public void testDeParse() throws Exception
    {
 /*       Pair<Entity[],char[][]> pair = new Parser(new GameState(new Map())).DeParse("4,0, ,0,22,2,@,0,2,3,%,0,2,#,&,0,2,3,3,a,b,c,2,a,c,5,a,e,r,t,3,");

        Entity[] entities = pair.getKey();
        char[][] result = pair.getValue();

        assertEquals(entities[0].getSpriteString(), "0");
        assertEquals(entities[0].getID().toChar(), ' ');
        assertEquals(entities[0].getYPos(), 22);
        assertEquals(entities[0].getXPos(), 0);

        assertEquals(entities[1].getSpriteString(), "2");
        assertEquals(entities[1].getID().toChar(), '@');
        assertEquals(entities[1].getYPos(), 2);
        assertEquals(entities[1].getXPos(), 0);

        assertEquals(entities[2].getSpriteString(), "3");
        assertEquals(entities[2].getID().toChar(), '%');
        assertEquals(entities[2].getYPos(), 2);
        assertEquals(entities[2].getXPos(), 0);

        assertEquals(entities[3].getSpriteString(), "#");
        assertEquals(entities[3].getID().toChar(), '&');
        assertEquals(entities[3].getYPos(), 2);
        assertEquals(entities[3].getXPos(), 0);

        assertEquals('a', result[0][0]);
        assertEquals('b', result[0][1]);
        assertEquals('c', result[0][2]);

        assertEquals('a', result[1][0]);
        assertEquals('c', result[1][1]);

        assertEquals('a', result[2][0]);
        assertEquals('e', result[2][1]);
        assertEquals('r', result[2][2]);
        assertEquals('t', result[2][3]);
        assertEquals('3', result[2][4]);*/
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
