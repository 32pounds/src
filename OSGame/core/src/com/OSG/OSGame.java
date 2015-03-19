package com.OSG;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.comms.InputHandler;
import com.gameloop.GameLoop;
import com.map.Map;
import com.model.Debugger;
import com.model.Player;
import com.renderer.Drawable;
import com.renderer.SpriteStorage;
import com.renderer.Updatable;
import java.util.ArrayList;
import java.util.Collections;

public class OSGame extends ApplicationAdapter {

    private SpriteBatch batch;
    private GameLoop thread;
    private ArrayList<Drawable> drawables;
    private ArrayList<Updatable> updatables;

    @Override
    public void create() {

        drawables = new ArrayList<Drawable>();
        updatables = new ArrayList<Updatable>();

        batch = new SpriteBatch();

        SpriteStorage.getInstance().loadAssets();

        if (Debugger.IsDebugging) {
            Debugger debugger = new Debugger();
            drawables.add(debugger);
            updatables.add(debugger);
        }

        Map map = new Map();
        drawables.add(map);

        Player player = new Player(map);
        drawables.add(player);
        updatables.add(player);

        thread = new GameLoop(updatables);
        thread.setRunning(true);
        thread.start();

        Gdx.input.setInputProcessor(new InputHandler());
    }

    @Override
    public void render() {
        //sorting by zIndex before draw
        Collections.sort(drawables);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        thread.run();

        for (Drawable drawable : drawables)
            drawable.draw(batch);

        batch.end();
    }
}
