package com.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.comms.GameID;
import com.comms.GameState;
import com.map.Direction;
import com.map.Map;
import com.map.Position;
import com.renderer.Drawable;
import com.renderer.SpriteStorage;

/**
 * This function represent the player
 * <p>This class will be edited when we add the game state</p>
 */
public class Entity extends Drawable
{

    //sprite of the player
    //I am using a Sprite cuz texture doesn't have rotation
    protected Sprite sprite;

    //initial position of the player
    protected Position position;
    protected GameState gameState;

    //The GameID being used to reference this entity remotely
    private GameID id;

    public GameID getID()
    {
        return id;
    }

    public void assignID(GameID newID)
    {
        id = newID;
    }

    //It is need to parse the object
    //How this class need a img code to be instantiate
    private String imageCode;


    /**
     * Provide a way to access map data, but after game state finished this property must be ripped off from here.
     */
    public Entity(GameState state, String img)
    {
        position = new Position(1, 1);
        imageCode = img;
        //using sprite cuz Texture doesn't have rotation
        sprite = new Sprite(SpriteStorage.getInstance().getTexture(img));
        //temporarily here
        this.gameState = state;
    }

    public void changeSprite(String img)
    {
        sprite = new Sprite(SpriteStorage.getInstance().getTexture(img));
    }

    /**
     * Get Entity x position.
     * @return Entity x position.
     */
    public int getXPos()
    {
        return position.getX();
    }

    /**
     * Get Entity y position
     * @return Entity y position
     */
    public int getYPos()
    {
        return position.getY();
    }

    /**
     * Set Entity x position.
     * @param x X position to be set.
     */
    public void setXPos(int x)
    {
        position.setX(x);
    }

    /**
     * Set Entity y position.
     * @param y Y position to be set.
     */
    public void setYPos(int y)
    {
        position.setY(y);
    }

    /**
     * Get Image code.
     * @return
     */
    public String getImageCode()
    {
        return imageCode;
    }

    @Override
    public void draw(SpriteBatch batch)
    {
        int x = getXPos();
        int y = getYPos();
        //using bacth.draw instead of sprite.draw cuz I dont want apply every property of the sprite (like position)
        batch.draw(sprite, x * Map.XDIMENSION, y * Map.YDIMENSION, sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
    }

    @Override
    public int getZIndex()
    {
        //the player will be drawn on of map, chant it if necessary
        return 1;
    }

    @Override
    public boolean isExpired()
    {
        //for now this will be false
        return false;
    }

    public synchronized void move(Direction dir)
    {
        Map map = gameState.gameMap();
        int x = getXPos();
        int y = getYPos();
        switch (dir)
        {
            case SOUTH:
                //check if the next step is a valid step
                if (map.isWalkable(x, y - 1)) y--;
                //set a rotation toward to the step
                sprite.setRotation(180);
                break;

            case NORTH:
                if (map.isWalkable(x, y + 1)) y++;
                sprite.setRotation(0);
                break;

            case WEST:
                if (map.isWalkable(x - 1, y)) x--;
                sprite.setRotation(90);
                break;

            case EAST:
                if (map.isWalkable(x + 1, y)) x++;
                sprite.setRotation(270);
                break;
        }
        position.setX(x);
        position.setY(y);
    }
}
