package com.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.renderer.Drawable;
import com.renderer.Updatable;

import java.text.DecimalFormat;

public class Debugger extends Drawable implements Updatable {


    private BitmapFont font;
    private double lastElapsedTime;
    private double lastTime;
    private double updateElapsedTime;

    public static final boolean IsDebugging = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

    public Debugger() {

        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        font.scale(0.05f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        double elapsedTime = (System.nanoTime() - lastTime) / 1000000000;
        lastTime = System.nanoTime();

        font.draw(batch, "CPS: " + new DecimalFormat("#").format(1 / updateElapsedTime), 0, Gdx.graphics.getHeight() - 10);
        font.draw(batch, "FPS: " + new DecimalFormat("#").format(1 / elapsedTime), 0, Gdx.graphics.getHeight() - 25);
    }

    @Override
    public int getZIndex() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public void update() {
        updateElapsedTime = (System.nanoTime() - lastElapsedTime) / 1000000000;
        lastElapsedTime = System.nanoTime();
    }
}
