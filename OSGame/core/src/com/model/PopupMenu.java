package com.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.renderer.Drawable;

public class PopupMenu extends Drawable implements InputProcessor {

    private Table table;
    private Stage stage;
    private boolean visible = true;

    public PopupMenu() {

        stage = new Stage();
        Gdx.input.setInputProcessor(this);

        Table rootTable = new Table();

        rootTable.setBounds(10, -10, 250, Gdx.graphics.getHeight() - 100);

        com.badlogic.gdx.scenes.scene2d.utils.Drawable draw = new TextureRegionDrawable(new TextureRegion(new Texture("menu/sky.jpg")));
        rootTable.background(draw);

        table = new Table();
        table.debug();

        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("font/font.fnt")), Color.BLUE);

        Table table = new Table();

        table.add(new Label("Name: Someone", labelStyle)).left();
        table.row();
        table.add(new Label("Health: 100", labelStyle)).left();
        table.row();
        table.add(new Label("Mana: 60", labelStyle)).left();
        table.row();
        table.add(new Label("Int: 10", labelStyle)).left();
        table.row();
        table.add(new Label("Dex: 10", labelStyle)).left();
        table.row();
        table.add(new Label("Str: 10", labelStyle)).left();

        table.setFillParent(true);
        table.top().left().pad(10);


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
