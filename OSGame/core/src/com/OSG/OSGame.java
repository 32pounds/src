package com.OSG;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.comms.InputHandler;
import com.gameloop.GameLoop;
import com.map.Map;
import com.model.Debugger;
import com.model.Player;
import com.renderer.Drawable;
import com.renderer.SpriteStorage;
import com.renderer.Updatable;
import com.badlogic.gdx.utils.TimeUtils;
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

        Gdx.input.setInputProcessor(new InputHandler(localPlayer));
    }

    @Override
    public void render() {
        //sorting by zIndex before draw
        Collections.sort(drawables);
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateCameraPosition();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        double time = (double)(TimeUtils.millis()/1000d);
        float r = .75f + .5f*((float)Math.sin(time));
        float g = .75f + .5f*((float)Math.cos(time));
        float b = .75f + .5f*((float)Math.sin(2*time + 1));
        batch.setColor(r,g,b,1.0f);

        for (Drawable drawable : drawables)
            drawable.draw(batch);

        batch.end();
    }
    
    private void updateCameraPosition(){
        float x = Map.XDIMENSION * localPlayer.getXPos();
        float y = Map.XDIMENSION * localPlayer.getYPos();
        camera.position.set(x, y, 0);
        camera.rotate(.75f,0,0,1);
        camera.zoom = ((float)(1.5d-Math.sin(((double)TimeUtils.millis())/650d)));
        camera.update();
    }
}
