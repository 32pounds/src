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
import com.model.MonsterDistance;
import com.model.MonsterTowards;
import com.model.Player;
import com.model.PopupMenu;
import com.renderer.Drawable;
import com.renderer.SpriteStorage;
import com.renderer.Updatable;
import java.lang.InterruptedException;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import com.multi.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameLoop extends Thread {
    private List<Updatable> updatables;
    private GameState gameState;
    private boolean running;
    private ServerThread serverThread;
    private ConcurrentLinkedQueue<Command> commands;


    public GameLoop() {
        updatables = new ArrayList<Updatable>();
        commands = new ConcurrentLinkedQueue<Command>();
        running = true;
        

        MessageHandler handler = new MessageHandler(){
            @Override
            public void handle(String message){
                //System.out.println("Server received command "+message);
                Command cmd = Command.parse(message.toCharArray());
                if(cmd != null){
                    commands.add(cmd);
                }
            }
        };
        try{
            serverThread = new ServerThread(5051,handler, this);
            serverThread.start();
        } catch (Exception e){
            e.printStackTrace();
        }

        //this is only temporary; the server should make its own in the future
        initializeGameState();

        ScheduledExecutorService executor =
                    Executors.newSingleThreadScheduledExecutor();

        Runnable sendStateTask = new Runnable() {
            public void run() {
                serverThread.SendString(getStateMessage());
            }
        };

        executor.scheduleAtFixedRate(sendStateTask, 0, 25, TimeUnit.MILLISECONDS);
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

    public String getStateMessage(){
        Parser p = new Parser(gameState);
        return p.Parse(gameState.entities());
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

            Command command = commands.poll();

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

