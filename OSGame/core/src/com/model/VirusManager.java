/* VirusManager.java
 * by Elizabeth Hernandez
 * 5-4-15
 *
 * manages the virus population and spawning
 */

package com.model;

import com.gameloop.GameLoop;
import com.map.Map;//
import com.model.Debugger;
import com.model.Entity;//
import com.model.Monster;//
import com.renderer.Drawable;//
import com.renderer.SpriteStorage;
import com.renderer.Updatable;//
import java.lang.InterruptedException;
import java.util.*;//
import java.util.ArrayList;
import java.util.List;

import com.comms.GameID;
import com.map.Map;
import com.map.Position;
import com.model.Entity;
import com.model.Player;
import com.model.VirusEntry;
import com.model.VirusQueen;
import com.model.Virus;
import com.gameloop.GameLoop;
import com.renderer.Drawable;
import java.util.*;
import java.util.concurrent.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.TimeUtils;
import com.map.*;
import com.comms.*;
import com.renderer.Updatable;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collection;


public class VirusManager {

    private int virusTarget;
    private int virusQueenTarget;
    private int virusCount;
    private int virusQueenCount;
    private int virusEntryCount;
    private int spawnAtEntry;
    private int spawnAtQueen;
    Monster virus;
    GameID id;
    GameState gameState;
    Map map;
    private List entries;
    private List queens;
    private List viruses;
    private GameLoop gameLoop;
    private Position nullPosition;

    // manages virus population
    public VirusManager (GameState gameState, GameLoop loop) {
        // initialize virus variables
        gameLoop = loop;
        virusTarget = 50;
        virusQueenTarget = 5;
        virusCount = 0;
        virusQueenCount = 0;
        map = gameState.gameMap();
        entries = new ArrayList();
        queens = new ArrayList();
        viruses = new ArrayList();
        spawnAtEntry = 0;
        spawnAtQueen = 0;
        nullPosition = new Position(0,0);
        
        // spawn viruses
        //initializeVirusEntries()
        //initializeVirusQueens()
        //initializeViruses()
    }
    
    private void initializeVirusEntries() {
        ArrayList<Position> EntryPositions = map.getVirusEntries();
        //ArrayList<Integer> xCoord = (ArrayList) Coord.get(0);
        //ArrayList<Integer> yCoord = (ArrayList) Coord.get(1);
        virusEntryCount = EntryPositions.size();
        for (int i = 0; i < virusEntryCount; i++){
            id = gameLoop.spawnVirusEntry(EntryPositions.get(i));
            entries.add(id);
            virusEntryCount++;
        }
    }

    // respawn a virus queen if live entry found
    public Position respawnQueen (GameID asker) {
        Position position = nullPosition;
        if (virusEntryCount > 0) {
            position = findEntry();
            if (position != nullPosition) {
                // make queen live
                virusQueenCount++;
            }
        }
        return position;
    }

    // respawn a virus if live queen found
    public boolean respawnVirus (GameID asker) {
        if (virusCount < virusTarget) {
            virusCount++;
            
            return true;
        }
        return false;
    }
    
    // finds the position of the next live virus entry
    public Position findEntry() {
        int i = spawnAtEntry;
        Position position = nullPosition;
        do {
            // check if virus entry is live
            
            // move index to next virus entry
            i++;
            if (i == virusEntryCount){
                i = 0;
            }
        } while (i != spawnAtEntry);
        return position;
    }
    
    // mark virus entry as dead
    public void virusEntryDied(GameID sender) {
        // mark entry as dead
        virusEntryCount--;
    }

    // mark virus queen as dead
    public void virusQueenDied(GameID sender) {
        virusQueenCount--;
    }
    
    // mark virus entry as alive
    public void virusEntryRespawned() {
        // mark entry as alive
        virusEntryCount++;
    }



}
