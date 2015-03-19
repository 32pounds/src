package com.renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Drawable implements Comparable<Drawable> {

    public abstract void draw(SpriteBatch batch);

    public abstract int getZIndex();

    public abstract boolean isExpired();

    @Override
    public int compareTo(Drawable o) {
        
        return this.getZIndex() - o.getZIndex();
    }
}
