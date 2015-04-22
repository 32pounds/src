package com.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.renderer.Drawable;
import com.renderer.SpriteStorage;
import com.map.Position;

public class Map extends Drawable {
    //this grid is [y][x] to make parsing _much_ easier
    char grid[][];
    public static final int XDIMENSION = 32;
    public static final int YDIMENSION = 32;

    public Map() {
        FileHandle mapOne = Gdx.files.internal("Level_1_Big_Map.map");

        //create the grid with the map
        grid = parseGrid(mapOne);
    }

    public void draw(SpriteBatch batch) {
        for (int y = 0; y < grid.length; ++y) {
            for (int x = 0; x < grid[y].length; ++x) {
                if (grid[y][x] == ' ')
                    batch.draw(SpriteStorage.getInstance().getTexture(" "), x * XDIMENSION, y * YDIMENSION);
                else if (grid[y][x] == 'X')
                    batch.draw(SpriteStorage.getInstance().getTexture("X"), x * XDIMENSION, y * YDIMENSION);
                else if (grid[y][x] == '^')
                    batch.draw(SpriteStorage.getInstance().getTexture("^"), x * XDIMENSION, y * YDIMENSION);
                else if (grid[y][x] == 'v')
                    batch.draw(SpriteStorage.getInstance().getTexture("v"), x * XDIMENSION, y * YDIMENSION);
                else if (grid[y][x] == '@')
                    batch.draw(SpriteStorage.getInstance().getTexture("@"), x * XDIMENSION, y * YDIMENSION);
                else if (grid[y][x] == 'L')
                    batch.draw(SpriteStorage.getInstance().getTexture("L"), x * XDIMENSION, y * YDIMENSION);
                else if (grid[y][x] == 'D')
                    batch.draw(SpriteStorage.getInstance().getTexture("D"), x * XDIMENSION, y * YDIMENSION);
                else if (grid[y][x] == 'R')
                    batch.draw(SpriteStorage.getInstance().getTexture("R"), x * XDIMENSION, y * YDIMENSION);
                else if (grid[y][x] == 'C')
                    batch.draw(SpriteStorage.getInstance().getTexture("C"), x * XDIMENSION, y * YDIMENSION);
            }
        }
    }

    public int getXBound()
    {
                return grid[0].length;
    }

    public int getYBound()
    {
                return grid.length;
    }

    @Override
    public int getZIndex() {
        return 0;
    }

    public char getCode(int xIndex, int yIndex) {
        return grid[yIndex][xIndex];
    }

    public char getCode(Position pos){
        return grid[pos.getY()][pos.getX()];
    }

    public boolean isWalkable(int x, int y) {
        if (grid[y][x] == ' ' || grid[y][x] == '^' || grid[y][x] == 'v')
            return true;
        return false;
    }

    public boolean isWalkable(Position pos){
        return (grid[pos.getY()][pos.getX()] == ' ');
    }

    public boolean isExpired() {
        return false;
    }


   public int isMove(int x, int y) {
        if (grid[y][x] == '^')
            return 1;
        else if (grid[y][x] == 'v')
            return 2;
        return 0;
    }

    public boolean isMove(Position pos){
        return (grid[pos.getY()][pos.getX()] == ' ');
    }

    public Position findFirstInstance(char item){
        for (int y = 0; y < grid.length; ++y) {
            for (int x = 0; x < grid[y].length; ++x) {
                if(grid[y][x]==item) return new Position(x,y);
            }
        }
        return new Position(0,0);
    }

    public Position findNextInstance(int X, int Y, char item){
        for (int y = Y; y < grid.length; ++y) {
            for (int x = X; x < grid[y].length; ++x) {
                if(grid[y][x]==item) return new Position(x,y);
            }
        }
        return new Position(0,0);
    }

    public Position findPreviousInstance(int X, int Y, char item){
        for (int y = Y; y > 0; --y) {
            for (int x = X; x > 0; --x) {
                if(grid[y][x]==item) return new Position(x,y);
            }
        }
        return new Position(0,0);
    }

    private char[][] parseGrid(FileHandle map) {
        String[] lines = (map.readString()).split("\n");
        int height = lines.length;
        char[][] tmp = new char[height][];
        for (int y = 0; y < height; y++) {
            tmp[y] = lines[height - y - 1].toCharArray();
            System.err.println(lines[height - y - 1]);
        }
        return tmp;
    }
}
