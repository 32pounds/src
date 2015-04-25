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
                switch(grid[y][x]) {
                    case ' ':
                        batch.draw(SpriteStorage.getInstance().getTexture("BeigeTile"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'B':
                        batch.draw(SpriteStorage.getInstance().getTexture("BlackCarpet"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'o':
                        batch.draw(SpriteStorage.getInstance().getTexture("TransportUp"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'g':
                        batch.draw(SpriteStorage.getInstance().getTexture("TransportDown"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case '@':
                        batch.draw(SpriteStorage.getInstance().getTexture("StartBeige"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'L':
                        batch.draw(SpriteStorage.getInstance().getTexture("BeigeTile"), x * XDIMENSION, y * YDIMENSION);
                        batch.draw(SpriteStorage.getInstance().getTexture("WhiteDeskL"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'D':
                        batch.draw(SpriteStorage.getInstance().getTexture("BeigeTile"), x * XDIMENSION, y * YDIMENSION);
                        batch.draw(SpriteStorage.getInstance().getTexture("WhiteDeskM"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'R':
                        batch.draw(SpriteStorage.getInstance().getTexture("BeigeTile"), x * XDIMENSION, y * YDIMENSION);
                        batch.draw(SpriteStorage.getInstance().getTexture("WhiteDeskR"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'C':
                        batch.draw(SpriteStorage.getInstance().getTexture("BeigeTile"), x * XDIMENSION, y * YDIMENSION);
                        batch.draw(SpriteStorage.getInstance().getTexture("RedChairN"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'P':
                        batch.draw(SpriteStorage.getInstance().getTexture("BeigeTile"), x * XDIMENSION, y * YDIMENSION);
                        batch.draw(SpriteStorage.getInstance().getTexture("LargePlant"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'W':
                        batch.draw(SpriteStorage.getInstance().getTexture("BeigeTile"), x * XDIMENSION, y * YDIMENSION);
                        batch.draw(SpriteStorage.getInstance().getTexture("WaterCooler"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'F':
                        batch.draw(SpriteStorage.getInstance().getTexture("BeigeTile"), x * XDIMENSION, y * YDIMENSION);
                        batch.draw(SpriteStorage.getInstance().getTexture("WhiteDeskM"), x * XDIMENSION, y * YDIMENSION);
                        batch.draw(SpriteStorage.getInstance().getTexture("FilledForm"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case '.':
                        batch.draw(SpriteStorage.getInstance().getTexture("WhiteFloor"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case '|':
                        batch.draw(SpriteStorage.getInstance().getTexture("SheetMetal"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'l':
                        batch.draw(SpriteStorage.getInstance().getTexture("WhiteFloor"), x * XDIMENSION, y * YDIMENSION);
                        batch.draw(SpriteStorage.getInstance().getTexture("BeigeDeskL"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'd':
                        batch.draw(SpriteStorage.getInstance().getTexture("WhiteFloor"), x * XDIMENSION, y * YDIMENSION);
                        batch.draw(SpriteStorage.getInstance().getTexture("BeigeDeskM"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'r':
                        batch.draw(SpriteStorage.getInstance().getTexture("WhiteFloor"), x * XDIMENSION, y * YDIMENSION);
                        batch.draw(SpriteStorage.getInstance().getTexture("BeigeDeskR"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'c':
                        batch.draw(SpriteStorage.getInstance().getTexture("WhiteFloor"), x * XDIMENSION, y * YDIMENSION);
                        batch.draw(SpriteStorage.getInstance().getTexture("BlueChair"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 's':
                        batch.draw(SpriteStorage.getInstance().getTexture("Stars"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'T':
                        batch.draw(SpriteStorage.getInstance().getTexture("Terminal"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case '/':
                        batch.draw(SpriteStorage.getInstance().getTexture("CautionStripe"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case ',':
                        batch.draw(SpriteStorage.getInstance().getTexture("CyberFloor"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'H':
                        batch.draw(SpriteStorage.getInstance().getTexture("CyberWall"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'h':
                        batch.draw(SpriteStorage.getInstance().getTexture("CorruptedWall"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case '#':
                        batch.draw(SpriteStorage.getInstance().getTexture("CyberFloor"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'x':
                        batch.draw(SpriteStorage.getInstance().getTexture("Xbox"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'X':
                        batch.draw(SpriteStorage.getInstance().getTexture("Xboxes"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case '^':
                        batch.draw(SpriteStorage.getInstance().getTexture("CyberUp"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case 'v':
                        batch.draw(SpriteStorage.getInstance().getTexture("CyberDown"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case '<':
                        batch.draw(SpriteStorage.getInstance().getTexture("CyberLeft"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case '>':
                        batch.draw(SpriteStorage.getInstance().getTexture("CyberRight"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case '*':
                        batch.draw(SpriteStorage.getInstance().getTexture("Asterisk"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case '!':
                        batch.draw(SpriteStorage.getInstance().getTexture("Exclamation"), x * XDIMENSION, y * YDIMENSION);
                        break;
                    case '$':
                        batch.draw(SpriteStorage.getInstance().getTexture("Funds"), x * XDIMENSION, y * YDIMENSION);
                        break;
                }
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
        if (grid[y][x] == ' ' || grid[x][y] == 'o' || grid[x][y] == 'g' || grid[y][x] == '^' || grid[y][x] == 'v' || grid[y][x] == '.' || grid[y][x] == '@' || grid[x][y] == ',' || grid[x][y] == '#')
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
