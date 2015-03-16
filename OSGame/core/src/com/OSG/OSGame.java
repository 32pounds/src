package com.OSG;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gameloop.GameLoop;
import com.map.Map;
import com.renderer.Drawable;
import com.renderer.SpriteStorage;
import java.util.ArrayList;
import java.util.Collections;

public class OSGame extends ApplicationAdapter {

    private SpriteBatch batch;
    private GameLoop thread;
    private ArrayList<Drawable> drawables;

    @Override
    public void create() {
        drawables = new ArrayList<Drawable>();

        batch = new SpriteBatch();

        SpriteStorage.getInstance().loadAssets();

        drawables.add(new Map());

        thread = new GameLoop();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void render() {
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
