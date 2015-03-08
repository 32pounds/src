package com.OSG;

import com.renderer.Drawable;
import com.map.Map;
import com.gameloop.GameLoop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OSGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
        GameLoop thread;

	Drawable map;
        
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		map = new Map();
                thread = new GameLoop();
                thread.setRunning(true);
                thread.start();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
                
                batch.begin();
                thread.run();

		map.draw(batch);
		//batch.draw(img, 50, 50);

		batch.end();
	}
}
