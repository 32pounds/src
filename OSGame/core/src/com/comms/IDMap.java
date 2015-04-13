package com.comms;
import com.comms.GameID;
import com.model.Entity;
import java.util.*;
import java.util.concurrent.*;

/**
 * Manages the mapping of GameIDs to local references
 * @author Brett Menzies
 */
public class IDMap{
    private ConcurrentHashMap<GameID,Entity> map;
    public IDMap(){
        map = new ConcurrentHashMap<GameID,Entity>();
    }
    /**
     * Returns a reference to an Entity given its GameID
     * returns null if no reference is found
     */
    public Entity getByID(GameID id){
        return map.get(id);
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
        map.put(id, obj);
        obj.assignID(id);
    }
    /**
     * Removes the Entity with given GameID
     */
    public void remove(GameID id){
        map.remove(id);
    }
    /**
     * returns a collection of all entries
     */
    public Collection<Entity> entries(){
        return map.values();
    }
}
