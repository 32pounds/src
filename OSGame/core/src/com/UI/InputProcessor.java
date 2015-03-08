package com.UI;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//import com.renderer.Drawable;
import com.map.Map;



public interface InputProcessor{

    boolean keyDown( int key );

    boolean touchDown( );

}

