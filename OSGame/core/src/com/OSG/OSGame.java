package com.OSG;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.comms.*;
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
import java.util.*;
import com.multi.*;
import java.io.*;

public class OSGame extends ApplicationAdapter implements CommandHandler {
    private Debugger debugger;
    private GameState gameState;
    private OrthographicCamera camera;
    private GameID localPlayer;
    private PopupMenu popupMenu;
    private SpriteBatch batch;
    private GameLoop gameLoop;
    private String servAddress = null;
    private String cliAddress = null;
    public ClientThread clientThread;
    private InputHandler input;

    @Override
    public void create() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        servAddress = "127.0.0.1";
        cliAddress = "127.0.0.1";

        MessageHandler handler = new MessageHandler(){
            @Override
            public void handle(String message){
                syncWithState(message);
/*                synchronized(gameState){
                    gameLoop.syncWith(gameState);
                }*/
            }
        };
        clientThread = new ClientThread(5050,5051,handler);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);
        batch = new SpriteBatch();
        SpriteStorage.getInstance().loadAssets();

        if (Debugger.IsDebugging) {
            debugger = new Debugger();
        }

        gameState = new GameState(new Map());
        gameLoop = new GameLoop();

        gameLoop.setRunning(true);
        gameLoop.start();

        //This will be a call to comms in the future
        localPlayer = clientThread.JoinGame(); //blocking call

        input = new InputHandler(localPlayer,this);

        Gdx.input.setInputProcessor(input);
        popupMenu = new PopupMenu(this);

        OSInputProcessor.getInstance().addInputPorcessor(input);

    }

    public void connectToIP(String ip){
        final String ipAddr = ip;
        Runnable connect = new Runnable(){
            public void run(){
                localPlayer = clientThread.JoinGame(ipAddr);
                gameState = new GameState(new Map());
                input.setLocalPlayer(localPlayer);
            }
        };
        (new Thread(connect)).start();
    }

    public void handleCommand(Command cmd){
        clientThread.SendString(String.valueOf(cmd.packetize()));
    }

    public void syncWithState(String state){
        Parser p = new Parser(gameState);
        Entity[] entities = p.DePerseEntities(state);
       // System.out.println(entities.length);
        synchronized(gameState){
            for(Entity remote : entities){
                Entity local = gameState.getByID(remote.getID());
                if(local == null){
                    local = new Entity(gameState,remote.getSpriteString());
                    local.assignID(remote.getID());
                    gameState.register(local, local.getID());
                }
                local.changeSprite(remote.getSpriteString());
                local.setXPos(remote.getXPos());
                local.setYPos(remote.getYPos());
                local.setRotation(remote.getRotation());
            }
        }
    }

    @Override
    public void render() {
        //gameLoop.syncWith(gameState);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


/*        Object player = gameState.getByID(localPlayer);
        if(player == null) player = new Object();*/
        synchronized(gameState){
            //the view is controlled by the position of local player,
            //so we syncronize with that instance to prevent it changing
            //during rendering
            updateCameraPosition();
            batch.setProjectionMatrix(camera.combined);
            batch.begin();

            for (Drawable drawable : gameState.drawables())
                drawable.draw(batch);

            if (Debugger.IsDebugging) {
                debugger.draw(batch);
            }

            batch.end();
        }
        //this is here, because when it is on batch.begin() weird things happens
        popupMenu.draw(batch);
    }

    private void updateCameraPosition(){
        Entity player = gameState.getByID(localPlayer);
        if(player == null) return;
        float x = Map.XDIMENSION * player.getXPos();
        float y = Map.XDIMENSION * player.getYPos();
        camera.position.set(x, y, 0);
        camera.update();
    }
}
