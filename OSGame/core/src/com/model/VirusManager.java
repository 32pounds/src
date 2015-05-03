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

    public VirusManager (GameState gameState) {
        // initialize virus variables
        private int virusTarget = 50;
        private int virusQueenTarget = 5;
        private int virusCount = 0;
        private int virusQueenCount = 0;
        Monster virus;

    }

    // spawn a virus entry
    public void spawnVirusEntry (int X, int Y) {
        virus=new Monster(gameState, "VirusEntry", splat, X, Y);
        gameState.register(virus);
        addUpdatable(virus);
    }

    // spawn a virus if count is below target
    public boolean spawnVirus (int X, int Y) {
        if (virusCount < virusTarget) {
            virus=new Monster(gameState, "Virus", splat, X, Y);
            virusCount++;
            gameState.register(virus);
            addUpdatable(virus);
            return true;
        }
        return false;
    }

    // spawn a virus queen if count is below target
    public boolean spawnVirusQueen (int X, int Y) {
        if (virusQueenCount < virusQueenTarget) {
            virus=new VirusQueen(gameState, "VirusQueen", splat, X, Y);
            virusQueenCount++;
            gameState.register(virus);
            addUpdatable(virus);
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
