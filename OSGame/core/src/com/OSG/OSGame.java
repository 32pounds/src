package com.OSG;

import com.renderer.Drawable;
import com.map.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OSGame extends ApplicationAdapter {
	SpriteBatch batch;

	Drawable map;

	@Override
	public void create () {
		batch = new SpriteBatch();
		map = new Map();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		map.draw(batch);
<<<<<<< HEAD

=======
		
		
		
>>>>>>> 1f3b029bee2824a5fd2b61fab49d6778cdc11dd6
		batch.end();
	}
}
