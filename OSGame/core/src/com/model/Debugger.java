package com.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.renderer.Drawable;
import com.renderer.Updatable;

import java.text.DecimalFormat;

/**
 * This class helps to debug some times features plotting some data on screen
 * <p>This function will plot only in debug mode</p>
 */
public class Debugger extends Drawable implements Updatable {


    private BitmapFont font;
    private double lastElapsedTime;
    private double lastTime;
    private double updateElapsedTime;

    public static final boolean IsDebugging = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

    public Debugger() {

        //loading font from assets
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
    }

    @Override
    public void draw(SpriteBatch batch) {
        //Getting delta time and converting nano seconds and seconds
        double elapsedTime = (System.nanoTime() - lastTime) / 1000000000;
        //updating last time
        lastTime = System.nanoTime();

        //drawing data on screen
        //CPS is how often the update function  is called
        font.draw(batch, "CPS: " + new DecimalFormat("#").format(1 / updateElapsedTime), 0, Gdx.graphics.getHeight() - 10);
        //FPS is how often the draw function is called
        font.draw(batch, "FPS: " + new DecimalFormat("#").format(1 / elapsedTime), 0, Gdx.graphics.getHeight() - 25);
    }

    @Override
    public int getZIndex() {
        //this information always go on top of everything
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isExpired() {
        //can not be destroyed
        return false;
    }

    @Override
    public void update() {
        //gathering data to calc CPS
        updateElapsedTime = (System.nanoTime() - lastElapsedTime) / 1000000000;
        lastElapsedTime = System.nanoTime();
    }
}
