package com.map;

import com.renderer.Drawable;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Map implements Drawable{
	
    char grid[][];
    
        public Map()
        {
            public static final int XDIMENSION=32;
            public static final int YDIMENSION=32;
            //create the grid with the map
            Texture floor= new Texture("BeigeTile.png");
	}
        
	public void draw(SpriteBatch batch)
        {
		//for(int i=0; i<grid.getX; ++i)
                    //for(int j=0; j<grid.getY; ++j)
                    //{
                       //if(grid[x][y]=="x")
                            //batch.draw(floor, 
                    //}
	}
        
	public boolean isExpired(){
		return false;
	}
}
