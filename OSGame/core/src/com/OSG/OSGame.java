package com.OSG;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.comms.InputHandler;
import com.comms.OSInputProcessor;
import com.gameloop.GameLoop;
import com.map.Map;
import com.model.Debugger;
import com.model.Player;
import com.model.PopupMenu;
import com.renderer.Drawable;
import com.renderer.SpriteStorage;
import com.renderer.Updatable;
import java.util.ArrayList;
import java.util.Collections;

public class OSGame extends ApplicationAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private GameLoop gameLoop;
    private ArrayList<Drawable> drawables;
    private ArrayList<Updatable> updatables;
    private Player localPlayer;

    @Override
    public void create() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);
        batch = new SpriteBatch();
        
        drawables = new ArrayList<Drawable>();

        SpriteStorage.getInstance().loadAssets();

        if (Debugger.IsDebugging) {
            Debugger debugger = new Debugger();
            drawables.add(debugger);
        }

        Map map = new Map();
        drawables.add(map);

        localPlayer = new Player(map);
        drawables.add(localPlayer);

        gameLoop = new GameLoop(updatables);
        gameLoop.setRunning(true);
        gameLoop.start();

        popupMenu = new PopupMenu();

        OSInputProcessor.getInstance().addInputPorcessor(new InputHandler(localPlayer));

    }

    private PopupMenu popupMenu;
    @Override
    public void render() {
        //sorting by zIndex before draw
        Collections.sort(drawables);
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateCameraPosition();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        for (Drawable drawable : drawables)
            drawable.draw(batch);

        batch.end();

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
