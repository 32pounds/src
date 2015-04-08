package com.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.comms.OSInputProcessor;

public class PopupMenu extends com.renderer.Drawable implements InputProcessor {

    private Stage stage;
    private boolean visible = false;

    public PopupMenu() {
        stage = new Stage();

        OSInputProcessor.getInstance().addInputPorcessor(stage);
        OSInputProcessor.getInstance().addInputPorcessor(this);

        Table rootTable = new Table();
        Table table = new Table();

        if(Debugger.IsDebugging) {
            rootTable.debug();
            table.debug();
        }

        Drawable draw = new TextureRegionDrawable(new TextureRegion(new Texture("menu/white.png")));
        Drawable drawButton = new TextureRegionDrawable(new TextureRegion(new Texture("menu/button.png")));
        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("font/font.fnt")), Color.BLUE);

        rootTable.setBounds(0, 0, 250, Gdx.graphics.getHeight());

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = drawButton;
        textButtonStyle.down = drawButton;
        textButtonStyle.checked = drawButton;
        textButtonStyle.over = drawButton;
        textButtonStyle.font = new BitmapFont(Gdx.files.internal("menu/fonts/font.fnt"));


        table.background(draw);
        table.setFillParent(true);
        table.add(new Label("Name:", labelStyle)).left();
        table.add(new Label("Player 1", labelStyle)).left();
        table.row();
        table.add(new Label("Health:", labelStyle)).left();
        table.add(new Label("100", labelStyle)).left();
        table.row();
        table.add(new Label("Mana:", labelStyle)).left();
        table.add(new Label("30", labelStyle)).left();
        table.row();
        table.add(new TextButton("Save",textButtonStyle)).padTop(10).colspan(2);
        table.row();
        table.add(new TextButton("Load",textButtonStyle)).padTop(10).colspan(2);
        table.row();
        TextButton textButton = new TextButton("Return",textButtonStyle);
        TextButton exitButton = new TextButton("Exit Game",textButtonStyle);

        textButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                visible = !visible;
                
            }
        });

        // Click listener for exit button, will exit whole game.
        // So initialize click listener and get it ready to exit.
        exitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
               Gdx.app.exit();
                
            }
        });
        table.add(textButton).padTop(10).colspan(2);
        table.row(); // Start new row on in-game menu table for exit button
        table.add(exitButton).padTop(10).colspan(2);

        table.top().padTop(100);

        rootTable.addActor(table);
        stage.addActor(rootTable);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (visible) {
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
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
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE)
            visible = !visible;
        return false;
    }



    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
