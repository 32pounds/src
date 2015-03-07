package com.map;

import com.renderer.Drawable;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.files.FileHandle;

public class Map implements Drawable{
	
    char grid[][];
    final int XDIMENSION=32;
    final int YDIMENSION=32;
    Texture floor,wall,person;
    
    public Map()
    {
        FileHandle mapOne=Gdx.files.internal("levelOne.map");
        //create the grid with the map
        // FILL THE GRID
        
        floor= new Texture("BeigeTile.png");
        wall=new Texture("BlackCarpet.png");//i did black carpet as a place holder
        person=new Texture("Thomas-north.png"); //person place holder
    }
	public void draw(SpriteBatch batch)
        {
		for(int i=0; i<grid.length; ++i)
                    for(int j=0; j<grid[0].length; ++j)
                    {
                       if(grid[i][j]==' ')
                            batch.draw(floor, i*XDIMENSION, j*YDIMENSION); 
                       else if(grid[i][j]=='x')
                           batch.draw(wall, i*XDIMENSION, j*YDIMENSION);
                       else if(grid[i][j]=='@')
                           batch.draw(person, i*XDIMENSION, j*YDIMENSION);
                    }
	}
        
        public boolean isWalkable(int x, int y)
        {
            if(grid[x][y]==' ')
                return true;
            return false;
        }
	public boolean isExpired(){
		return false;
	}
}
