package com.comms;
import com.comms.GameID;
import com.map.Map;
import com.model.Entity;
import com.renderer.Drawable;
import java.util.*;
import java.util.concurrent.*;

/**
 * Manages the mapping of GameIDs to local references
 * @author Brett Menzies
 */
public class GameState{
    private Map gameMap;
    private ConcurrentHashMap<GameID,Entity> idMap;
    private List<Drawable> drawables;
    public GameState(Map map){
        gameMap  = map;
        idMap    = new ConcurrentHashMap<GameID,Entity>();
        drawables = new ArrayList<Drawable>();
        drawables.add(map);
    }
    /**
     * Returns a reference to an Entity given its GameID
     * returns null if no reference is found
     */
    public Entity getByID(GameID id){
        return idMap.get(id);
    }
    /**
     * Adds an Entity to the map and returns its unique GameID
     */
    public GameID register(Entity obj){
        //creates a new unique ID
        GameID id = new GameID();
        register(obj, id);
        return id;
    }
    /**
     * Registers an Entity with the given GameID as a key
     */
    public void register(Entity obj, GameID id){
        idMap.put(id, obj);
        obj.assignID(id);
        drawables.add(obj);
        Collections.sort(drawables);
    }
    /**
     * Removes the Entity with given GameID
     */
    public void remove(GameID id){
        Entity removed = idMap.remove(id);
        drawables.remove(removed);
    }
    /**
     * returns a list of all drawables sorted by their Z index
     */
    public List<Drawable> drawables(){
        return drawables;
    }

    public Map gameMap(){
        return gameMap;
    }
}
