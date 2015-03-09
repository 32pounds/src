package com.map;

import com.renderer.Drawable;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;


public class Map implements Drawable{
	//this grid is [y][x] to make parsing _much_ easier
    char grid[][];
    final int XDIMENSION=32;
    final int YDIMENSION=32;
    Texture floor,wall,person,leftDeskEnd, middleDesk, rightEndDesk, chair;
    
    public Map()
    {
        FileHandle mapOne=Gdx.files.internal("levelOne.map");
        
        //create the grid with the map
        grid = parseGrid(mapOne);
        
        try{
            floor= new Texture("top-down/floorings/BeigeTile.png");
            //I did black carpet as a place holder
            wall=new Texture("top-down/floorings/BlackCarpet.png");
            //person place holder
            person=new Texture("top-down/humans/Thomas-north.png");
            leftDeskEnd=new Texture("top-down/objects/WhiteDesk-end.png");
            middleDesk=new Texture("top-down/objects/WhiteDesk-horizontal.png");
            rightEndDesk=new Texture("top-down/objects/WhiteDesk-horizontal.png");
            chair=new Texture("top-down/objects/ChairRed-north.png");
        } catch (GdxRuntimeException e){
            e.printStackTrace();
        }
    }
	public void draw(SpriteBatch batch)
    {
        if(floor == null || wall == null || person == null) return;
        for(int y=0; y<grid.length; ++y){
            for(int x=0; x<grid[y].length; ++x){
               if(grid[y][x]==' ')
                    batch.draw(floor, x*XDIMENSION, y*YDIMENSION); 
               else if(grid[y][x]=='X')
                   batch.draw(wall, x*XDIMENSION, y*YDIMENSION);
               else if(grid[y][x]=='@')
                   batch.draw(person, x*XDIMENSION, y*YDIMENSION);
               else if(grid[y][x]=='L')
                   batch.draw(leftDeskEnd, x*XDIMENSION, y*YDIMENSION);
               else if(grid[y][x]=='D')
                   batch.draw(middleDesk, x*XDIMENSION, y*YDIMENSION);
               else if(grid[y][x]=='R')
                   batch.draw(rightEndDesk, x*XDIMENSION, y*YDIMENSION);
               else if(grid[y][x]=='C')
                   batch.draw(chair, x*XDIMENSION, y*YDIMENSION);
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