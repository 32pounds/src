package com.gameloop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.comms.*;
import com.comms.GameID;
import com.comms.GameState;
import com.comms.InputHandler;
import com.comms.OSInputProcessor;
import com.gameloop.GameLoop;
import com.map.Map;
import com.model.Debugger;
import com.model.Entity;
import com.model.Monster;
import com.model.Player;
import com.model.PopupMenu;
import com.renderer.Drawable;
import com.renderer.SpriteStorage;
import com.renderer.Updatable;
import java.lang.InterruptedException;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class GameLoop extends Thread {

    private boolean running;

    //list that will be updated every thread loop
    private List<Updatable> updatables;

    private GameState gameState;

    public GameLoop() {
        updatables = new ArrayList<Updatable>();
        running = true;
        //this is only temporary; the server should make its own in the future
        initializeGameState();
    }

    public void initializeGameState(){
        gameState = new GameState(new Map());
        for(int i=0; i<100; i++){
            Monster monster=new Monster(gameState,"M", null, null);
            gameState.register(monster);
            addUpdatable(monster);
        }
    }

    public GameID requestNewPlayer(){
        Player player = new Player(gameState,"Thomas");
        gameState.register(player);
        addUpdatable(player);
        return player.getID();
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void addUpdatable(Updatable up){
        synchronized(updatables){
            updatables.add(up);
        }
    }

    public void syncWith(GameState remoteState){
        Collection<Entity> localEntities = gameState.entities();
        for(Entity local : localEntities){
            Entity remote = remoteState.getByID(local.getID());
            if(remote == null){
                remote = new Entity(remoteState,local.getSpriteString());
                remote.assignID(local.getID());
                remoteState.register(remote, remote.getID());
            }
            remote.setXPos(local.getXPos());
            remote.setYPos(local.getYPos());
            remote.setRotation(local.getRotation());
        }
    }

    @Override
    public void run() {
        //this is acting as our server for now
        while (running) {
            synchronized(updatables){
                for (Updatable updatable : updatables)
                    updatable.update();
            }

            Command command = CommandHandler.getInstance().remove();
            //if there no command is not needed to be updated
            if (command != null) {
                command.execute(gameState);
            } else {
                try{
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    break; //break the loop if the thread is interrupted
                }
            }
        }
    }
}

