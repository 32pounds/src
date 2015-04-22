package com.comms;
import com.comms.GameID;
import com.map.Map;
import com.model.Entity;
import com.model.Player;
import com.renderer.Drawable;
import java.util.*;
import java.util.concurrent.*;

/**
 * Manages the mapping of GameIDs to local references
 * @author Brett Menzies
 */
public class GameState
{
    private Map gameMap;
    private ConcurrentHashMap<GameID,Entity> idMap;
    private List<Drawable>     drawables;//list to it can be sorted by Z-index
    private Collection<Player> players;

    public GameState(Map map){
        idMap     = new ConcurrentHashMap<GameID,Entity>();
        drawables = new ArrayList<Drawable>();
        players   = new ArrayList<Player>();
        gameMap   = map;
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

        //attempt to remove from drawables and players, its fine if
        // they are not actually in there at the moment
        drawables.remove(removed);
        players.remove(removed);
    }

    public Collection<Entity> entities(){
        return idMap.values();
    }
    /**
     * returns a list of all drawables sorted by their Z index
     */
    public List<Drawable> drawables(){
        return drawables;
    }

    public GameID addPlayer(Player player){
        if(player.getID() == null || !idMap.containsKey(player.getID())){
            register(player);
        }
        players.add(player);
        return player.getID();
    }

    public Collection<Player> getPlayers(){
        return players;
    }

    public Map gameMap(){
        return gameMap;
    }
}
