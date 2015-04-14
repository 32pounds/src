package com.OSG;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.comms.InputHandler;
import com.comms.OSInputProcessor;
import com.comms.GameID;
import com.comms.GameState;
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

public class OSGame extends ApplicationAdapter {
    private Debugger debugger;
    private GameState gameState;
    private OrthographicCamera camera;
    private Player localPlayer;
    private PopupMenu popupMenu;
    private SpriteBatch batch;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);
        batch = new SpriteBatch();
        SpriteStorage.getInstance().loadAssets();

        if (Debugger.IsDebugging) {
            debugger = new Debugger();
        }

        gameState = new GameState(new Map());
        GameLoop gameLoop = new GameLoop(gameState);

        localPlayer = new Player(gameState,"Thomas");
        gameLoop.addUpdatable((Player)localPlayer);
        gameState.register(localPlayer);


        //spawns a monster (or 50)
        //this should be done by the server, not the client
        Sound splat = Gdx.audio.newSound(Gdx.files.internal("sounds/Squish.mp3"));
        for(int i=0; i<100; i++){
            Monster monster=new Monster(gameState,"M", localPlayer, splat);
            gameState.register(monster);
            gameLoop.addUpdatable(monster);
        }

        Gdx.input.setInputProcessor(new InputHandler(localPlayer.getID()));
        popupMenu = new PopupMenu();

        OSInputProcessor.getInstance().addInputPorcessor(new InputHandler(localPlayer.getID()));

        gameLoop.setRunning(true);
        gameLoop.start();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        synchronized(localPlayer){
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
        float x = Map.XDIMENSION * localPlayer.getXPos();
        float y = Map.XDIMENSION * localPlayer.getYPos();
        camera.position.set(x, y, 0);
        camera.update();
    }
}
