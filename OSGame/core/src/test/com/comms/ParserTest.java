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
        Command[] commands = new Command[3];
        commands[0] = new DummyCmd();
        commands[1] = new DummyCmd();
        commands[2] = new DummyCmd();

        String result = new Parser(new GameState(new Map())).Parse(commands);

        assertEquals("3,com.comms.DummyCmd:test,com.comms.DummyCmd:test,com.comms.DummyCmd:test,", result);
    }

    /**
     * Method: DeParseCommands(String data)
     */
    @Test
    public void testDeParseCommands() throws Exception
    {
        String test = "3,com.comms.DummyCmd:test,com.comms.DummyCmd:test,com.comms.DummyCmd:test,";

        Command[] result = new Parser(new GameState(new Map())).DeParseCommands(test);

        assertEquals("com.comms.DummyCmd:test", new String(result[0].getData()));
        assertEquals("com.comms.DummyCmd:test", new String(result[1].getData()));
        assertEquals("com.comms.DummyCmd:test", new String(result[2].getData()));
    }

    /**
     * Method: Parse(Entity[] entities)
     */
    @Test
    public void testParseEntities() throws Exception
    {
        String resultString = "0, ,0,2,90,2,@,0,2,90,3,%,0,2,90,#,&,0,2,90,";

        Entity[] entities = new Entity[4];

        Entity entity = new Entity(new GameState(new Map()), "0");
        entity.assignID(new GameID(" "));
        entity.setXPos(0);
        entity.setYPos(2);
        entity.setRotation(90);
        entities[0] = entity;

        entity = new Entity(new GameState(new Map()), "2");
        entity.assignID(new GameID("@"));
        entity.setXPos(0);
        entity.setYPos(2);
        entity.setRotation(90);
        entities[1] = entity;

        entity = new Entity(new GameState(new Map()), "3");
        entity.assignID(new GameID("%"));
        entity.setXPos(0);
        entity.setYPos(2);
        entity.setRotation(90);
        entities[2] = entity;

        entity = new Entity(new GameState(new Map()), "#");
        entity.assignID(new GameID("&"));
        entity.setXPos(0);
        entity.setYPos(2);
        entity.setRotation(90);
        entities[3] = entity;

        String result = new Parser(new GameState(new Map())).Parse(entities);

        assertEquals(resultString, result);
    }

    /**
     * Method: DePerseEntities(String data)
     */
    @Test
    public void testDePerseEntities() throws Exception
    {
        Entity[] entities = new Parser(new GameState(new Map())).DePerseEntities("0, ,0,22,90,2,@,0,2,90,3,%,0,2,90,#,&,0,2,90,");

        assertEquals(4, entities.length);


        assertEquals(entities[0].getSpriteString(), "0");
        assertEquals(entities[0].getID().toString(), " ");
        assertEquals(entities[0].getYPos(), 22);
        assertEquals(entities[0].getXPos(), 0);
        assertEquals(entities[0].getRotation(), 90);

        assertEquals(entities[1].getSpriteString(), "2");
        assertEquals(entities[1].getID().toString(), "@");
        assertEquals(entities[1].getYPos(), 2);
        assertEquals(entities[1].getXPos(), 0);
        assertEquals(entities[1].getRotation(), 90);

        assertEquals(entities[2].getSpriteString(), "3");
        assertEquals(entities[2].getID().toString(), "%");
        assertEquals(entities[2].getYPos(), 2);
        assertEquals(entities[2].getXPos(), 0);
        assertEquals(entities[2].getRotation(), 90);

        assertEquals(entities[3].getSpriteString(), "#");
        assertEquals(entities[3].getID().toString(), "&");
        assertEquals(entities[3].getYPos(), 2);
        assertEquals(entities[3].getXPos(), 0);
        assertEquals(entities[3].getRotation(), 90);
    }

    /**
     * Method: DeParse(String data)
     */
    @Test
    public void testDeParse() throws Exception
    {
        Tuple<Entity[],Command[]> pair = new Parser(new GameState(new Map())).DeParse("4,0, ,0,22,90,2,@,0,2,90,3,%,0,2,90,#,&,0,2,90,3,com.comms.DummyCmd:test,com.comms.DummyCmd:test,com.comms.DummyCmd:test,");

        Entity[] entities = pair.getValue1();
        Command[] result = pair.getValue2();

        assertEquals(entities[0].getSpriteString(), "0");

        assertEquals(entities[0].getID().toString(), " ");
        assertEquals(entities[0].getYPos(), 22);
        assertEquals(entities[0].getXPos(), 0);
        assertEquals(entities[0].getRotation(), 90);

        assertEquals(entities[1].getSpriteString(), "2");
        assertEquals(entities[1].getID().toString(), "@");
        assertEquals(entities[1].getYPos(), 2);
        assertEquals(entities[1].getXPos(), 0);
        assertEquals(entities[1].getRotation(), 90);

        assertEquals(entities[2].getSpriteString(), "3");
        assertEquals(entities[2].getID().toString(), "%");
        assertEquals(entities[2].getYPos(), 2);
        assertEquals(entities[2].getXPos(), 0);
        assertEquals(entities[2].getRotation(), 90);

        assertEquals(entities[3].getSpriteString(), "#");
        assertEquals(entities[3].getID().toString(), "&");
        assertEquals(entities[3].getYPos(), 2);
        assertEquals(entities[3].getXPos(), 0);
        assertEquals(entities[3].getRotation(), 90);

        assertEquals("com.comms.DummyCmd:test", new String(result[0].getData()));
        assertEquals("com.comms.DummyCmd:test", new String(result[1].getData()));
        assertEquals("com.comms.DummyCmd:test", new String(result[2].getData()));

    }
}
