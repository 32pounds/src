package com.comms;

import com.model.Entity;
import java.util.*;

/**
 * Parse Entities
 * <p>NOTES:</p>
 * <p>When you use Parse(Entity[]) to parse use DeParseEntity(string) to deparse</p>
 * <p>When you use Parse(Command[]) to parse use DeParseCommand(string)to deparse</p>
 * <p>When you use Parse(Entity[], Command[]) to parse use DeParse(string) deparse</p>
 */
public class Parser
{
    class Pair{
        String value, key;
        Pair(String k, String v){
            value = v;
            key = k;
        }
        String getValue(){
            return value;
        }
        String getKey(){
            return key;
        }
    }

    private String splitChar = ",";

    /**
     * GameState necessary to create new instances of Entities
     */
    private GameState gameState;

    /**
     * Constructor
     * @param gameState GameState necessary to create new instances of Entities
     */
    public Parser(GameState gameState)
    {
        this.gameState = gameState;
    }


    /**
     * Create a string from an array of Commands and an array of Entities
     * @param entities Entities to be parsed
     * @param commands ommands to be parsed
     * @return An parsed string from an array of Commands and an array of Entities
     */
    public String Parse(Entity[] entities, Command[] commands)
    {
        String result = "";

        result += entities.length + splitChar;

        result += Parse(entities);
        result += Parse(commands);

        return result;
    }

    /**
     * Create a string from an array of Commands
     * @param commands Commands to be parsed
     * @return String parsed from an array of Commands
     */
    public String Parse(Command[] commands)
    {
        String result = "";

        result += commands.length + splitChar;

        for (Command item : commands)
            result += new String(item.getData()) + splitChar;

        return result;
    }

    /**
     * Create a collection of Commands from a formatted string.
     * @param data Formatted string.
     * @return Collection of Commands.
     */
    public Command[] DeParseCommands(String data)
    {
        Pair value = StepString(data);
        data = value.getValue();

        Command[] result = new Command[Integer.parseInt(value.getKey())];

        for (int x = 0; x < result.length; x++)
        {
            value = StepString(data);
            data = value.getValue();

            String commandData = value.getKey();
            result[x] = Command.parse(commandData.toCharArray());
        }

        return result;
    }


    /**
     * Create string referent to a collection of Entities
     * @param entities Entities to be parsed
     * @return String referent to a collection of Entities
     */
    public String Parse(Entity[] entities)
    {
        String result = "";

        for (Entity item : entities)
        {
            result += item.getSpriteString() + splitChar;
            result += item.getID().toChar() + splitChar;
            result += item.getXPos() + splitChar;
            result += item.getYPos() + splitChar;
            result += item.getRotation() + splitChar;
        }
        return result;
    }

    public String Parse(Collection<Entity> entities)
    {
        String result = "";

        for (Entity item : entities)
        {
            result += item.getSpriteString() + splitChar;
            result += item.getID().toChar() + splitChar;
            result += item.getXPos() + splitChar;
            result += item.getYPos() + splitChar;
            result += item.getRotation() + splitChar;
        }
        return result;
    }

    /**
     * Create an array of Entities and an array of Commands from a data string
     * @param data Data
     * @return An array of Entities and an array of Commands
     */
/*    public Pair<Entity[], Command[]> DeParse(String data)
    {
        data = value.getValue();

        int numberOfEntities = Integer.parseInt(value.getKey());

        Entity[] entities = new Entity[numberOfEntities];

        for (int x = 0; x < entities.length; x++)
        {
            value = StepString(data);
            data = value.getValue();
            entities[x] = new Entity(gameState, value.getKey());

            value = StepString(data);
            data = value.getValue();
            entities[x].assignID(new GameID(value.getKey().toCharArray()[0]));

            value = StepString(data);
            data = value.getValue();
            entities[x].setXPos(Integer.parseInt(value.getKey()));

            value = StepString(data);
            data = value.getValue();
            entities[x].setYPos(Integer.parseInt(value.getKey()));

            value = StepString(data);
            data = value.getValue();
            entities[x].setRotation(Integer.parseInt(value.getKey()));
        }

        value = StepString(data);
        data = value.getValue();

        Command[] result = new Command[Integer.parseInt(value.getKey())];

        for (int x = 0; x < result.length; x++)
        {
            value = StepString(data);
            data = value.getValue();

            String commandData = value.getKey();
            result[x] = Command.parse(commandData.toCharArray());
        }

        return new Pair<Entity[], Command[]>(entities, result);
    }*/

    /**
     * Create a collection of Entities from a formatted string.
     * @param data Formatted string.
     * @return Collection of Entities.
     */
    public Entity[] DePerseEntities(String data)
    {
        Entity[] entities = new Entity[CountChar(data, ',') / 5];

        for (int x = 0; x < entities.length; x++)
        {
            Pair value = StepString(data);
            data = value.getValue();
            entities[x] = new Entity(gameState, value.getKey());

            value = StepString(data);
            data = value.getValue();
            entities[x].assignID(new GameID(value.getKey().toCharArray()[0]));

            value = StepString(data);
            data = value.getValue();
            entities[x].setXPos(Integer.parseInt(value.getKey()));

            value = StepString(data);
            data = value.getValue();
            entities[x].setYPos(Integer.parseInt(value.getKey()));

            value = StepString(data);
            data = value.getValue();
            entities[x].setRotation(Integer.parseInt(value.getKey()));
        }

        return entities;
    }

    private Pair StepString(String data)
    {
        int indexOf = data.indexOf(',');

        String value = data.substring(0, indexOf);
        String remainder = data.substring(indexOf + 1, data.length());

        return new Pair(value, remainder);
    }


    private int CountChar(String string, char iChar)
    {
        int count = 0;

        for (char item : string.toCharArray())
            if (item == iChar) count++;

        return count;

    }
}
