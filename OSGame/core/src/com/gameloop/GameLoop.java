package com.gameloop;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.comms.*;
import com.comms.GameID;
import com.comms.GameState;
import com.map.Map;
import com.model.Entity;
import com.model.Monster;
import com.model.MonsterDistance;
import com.model.MonsterTowards;
import com.model.Player;
import com.renderer.Updatable;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class GameLoop extends Thread {

    private boolean running;

    //list that will be updated every thread loop
    private final List<Updatable> updatables;

    private GameState gameState;


    public GameLoop() {
        updatables = new ArrayList<Updatable>();
        running = true;
        //this is only temporary; the server should make its own in the future
        initializeGameState();
    }

    public void initializeGameState(){
        gameState = new GameState(new Map());
        Random rand=new Random();
        int id;
        for(int i=0; i<100; i++){
            id=rand.nextInt(100)+1;
            Monster monster;
            //to add sound
            Sound splat = Gdx.audio.newSound(Gdx.files.internal("sounds/Squish.mp3"));
            if(id<=33)
                monster=new Monster(gameState,"M",splat);
            else if(id>=34 && id<=66)
            {
                monster=new MonsterTowards(gameState, "1",splat);
            }
            else
            {
                monster= new MonsterDistance(gameState, "3",splat);
            }
            gameState.register(monster);
            addUpdatable(monster);
        }
    }

    public GameID requestNewPlayer(){
        Player player = new Player(gameState,"Thomas");
        GameID playerID = gameState.addPlayer(player);
        addUpdatable(player);
        return playerID;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    private void addUpdatable(Updatable up){
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
            remote.changeSprite(local.getSpriteString());
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

