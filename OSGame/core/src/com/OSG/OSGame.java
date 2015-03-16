package com.OSG;

import com.renderer.Drawable;
import com.map.Map;
import com.map.Direction;
import com.comms.Command;
import com.comms.MoveCmd;
import com.gameloop.GameLoop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.renderer.SpriteStorage;

public class OSGame extends ApplicationAdapter {
	SpriteBatch batch;
	Drawable map;
	Command example;
    GameLoop thread;
        
	@Override
	public void create () {
		batch = new SpriteBatch();
        SpriteStorage.getInstance().loadAssets();
		map = new Map();
		example = new MoveCmd(Direction.NORTH);
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
		
		
		
		batch.end();
	}
}
