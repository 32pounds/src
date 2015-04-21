package com.comms;

import com.model.Entity;
import javafx.util.Pair;

/**
 * Parse Entities
 */
public class Parser
{
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

        result += entities.length + ",";
        result += commands.length + ",";

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

        result += commands.length + ",";

        for (Command item : commands)
        {
            result += item.getData().length + ",";
            for (char itemChar : item.getData())
            {
                result += itemChar + ",";
            }
        }

        return result;
    }

    /**
     * Create a collection of Commands from a formatted string.
     * @param data Formatted string.
     * @return Collection of Commands.
     */
    public char[][] DeParseCommands(String data)
    {
        Pair<String, String> value = StepString(data);
        data = value.getValue();

        char[][] result = new char[Integer.parseInt(value.getKey())][];

        for (int x = 0; x < result.length; x++)
        {
            value = StepString(data);
            data = value.getValue();

            result[x] = new char[Integer.parseInt(value.getKey())];

            for (int i = 0; i < result[x].length; i++)
            {
                value = StepString(data);
                data = value.getValue();
                result[x][i] = value.getKey().toCharArray()[0];
            }
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
            result += item.getImageCode() + ",";
            result += item.getID().toChar() + ",";
            result += item.getXPos() + ",";
            result += item.getYPos() + ",";
        }
        return result;
    }

    /**
     * Create a collection of Entities from a formatted string.
     * @param data Formatted string.
     * @return Collection of Entities.
     */
    public Entity[] DePerseEntities(String data)
    {
        Entity[] entities = new Entity[CountChar(data, ',') / 4];

        for (int x = 0; x < entities.length; x++)
        {
            Pair<String, String> value = StepString(data);
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
        }

        return entities;
    }

    private Pair<String, String> StepString(String data)
    {
        int indexOf = data.indexOf(',');

        String value = data.substring(0, indexOf);
        String remainder = data.substring(indexOf + 1, data.length());

        return new Pair<String, String>(value, remainder);
    }


    private int CountChar(String string, char iChar)
    {
        int count = 0;

        for (char item : string.toCharArray())
            if (item == iChar) count++;

        return count;

    }
}
