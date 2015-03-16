package com.map;

import com.renderer.Drawable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.files.FileHandle;
import com.renderer.SpriteStorage;

public class Map implements Drawable{
	//this grid is [y][x] to make parsing _much_ easier
    char grid[][];
    final int XDIMENSION=32;
    final int YDIMENSION=32;
    
    public Map()
    {
        FileHandle mapOne=Gdx.files.internal("levelOne.map");
        
        //create the grid with the map
        grid = parseGrid(mapOne);

    }
	public void draw(SpriteBatch batch)
    {
        for(int y=0; y<grid.length; ++y){
            for(int x=0; x<grid[y].length; ++x){
               if(grid[y][x]==' ')
                    batch.draw(SpriteStorage.getInstance().getTexture(" "), x*XDIMENSION, y*YDIMENSION);
               else if(grid[y][x]=='X')
                   batch.draw(SpriteStorage.getInstance().getTexture("X"), x*XDIMENSION, y*YDIMENSION);
               else if(grid[y][x]=='@')
                   batch.draw(SpriteStorage.getInstance().getTexture("@"), x*XDIMENSION, y*YDIMENSION);
               else if(grid[y][x]=='L')
                   batch.draw(SpriteStorage.getInstance().getTexture("L"), x*XDIMENSION, y*YDIMENSION);
               else if(grid[y][x]=='D')
                   batch.draw(SpriteStorage.getInstance().getTexture("D"), x*XDIMENSION, y*YDIMENSION);
               else if(grid[y][x]=='R')
                   batch.draw(SpriteStorage.getInstance().getTexture("R"), x*XDIMENSION, y*YDIMENSION);
               else if(grid[y][x]=='C')
                   batch.draw(SpriteStorage.getInstance().getTexture("C"), x*XDIMENSION, y*YDIMENSION);
            }
        }
    }
    public boolean isWalkable(int x, int y)
    {
        if(grid[y][x]==' ')
            return true;
        return false;
    }
	public boolean isExpired(){
		return false;
	}
    private char[][] parseGrid(FileHandle map){
        String[] lines = (map.readString()).split("\n");
        int height     = lines.length;
        char[][] tmp   = new char[height][];
        for(int y=0; y<height; y++){
            tmp[y] = lines[height-y-1].toCharArray();
            System.err.println(lines[height-y-1]);
        }
        return tmp;
    }
}
