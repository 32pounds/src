package com.map;

public class Position{
    private int X,Y;
    public Position(int x, int y){
        X = x;
        Y = y;
    }
    public int getX(){
        return X;
    }
    public int getY(){
        return Y;
    }
    public void setX(int x){
        X = x;
    }
    public void setY(int y){
        Y = y;
    }
}
