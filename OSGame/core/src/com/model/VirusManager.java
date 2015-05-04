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
    Monster virus;
    GameID id;
    GameState gameState;
    Map map;
    private List entries;
    private List queens;
    private List viruses;
    private GameLoop gameLoop;

    public VirusManager (GameState gameState, GameLoop gl) {
        // initialize virus variables
        gameLoop = gl;
        virusTarget = 50;
        virusQueenTarget = 5;
        virusCount = 0;
        virusQueenCount = 0;
        map = gameState.gameMap();
        entries = new ArrayList();
        queens = new ArrayList();
        viruses = new ArrayList();
    }
    
    private void initializeVirusEntries() {
        ArrayList Coord = map.getVirusEntries();
        ArrayList<Integer> xCoord = (ArrayList) Coord.get(0);
        ArrayList<Integer> yCoord = (ArrayList) Coord.get(1);
        int total = xCoord.size();
        for (int i = 0; i < total; i++){
            id = gameLoop.spawnVirusEntry(xCoord.get(i), yCoord.get(i));
            entries.add(id);
        }
    }

    // spawn a virus queen if count is below target
    public boolean okaySpawnQueen (int X, int Y) {
        if (virusQueenCount < virusQueenTarget) {
            id = gameLoop.spawnVirusQueen(X,Y);
            virusQueenCount++;
            return true;
        }
        return false;
    }

    // spawn a virus if count is below target
    public boolean okaySpawnVirus (int X, int Y) {
        if (virusCount < virusTarget) {
            id = gameLoop.spawnVirus(X, Y);
            virusCount++;
            return true;
        }
        return false;
    }
    
    // lower virus count by 1
    public void virusDied() {
        virusCount--;
    }

    // lower virus queen count by 1
    public void virusQueenDied() {
        virusQueenCount--;
    }



}
