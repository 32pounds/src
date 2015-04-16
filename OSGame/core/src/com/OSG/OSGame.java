package com.OSG;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.comms.InputHandler;
import com.gameloop.GameLoop;
import com.map.Map;
import com.model.Debugger;
import com.model.Entity;
import com.model.Monster;
import com.model.MonsterDistance;
import com.model.MonsterTowards;
import com.model.Player;
import com.renderer.Drawable;
import com.renderer.SpriteStorage;
import com.renderer.Updatable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class OSGame extends ApplicationAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private GameLoop gameLoop;
    private ArrayList<Drawable> drawables;
    private Entity localPlayer;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);
        batch = new SpriteBatch();

        drawables = new ArrayList<Drawable>();

        SpriteStorage.getInstance().loadAssets();

        if (Debugger.IsDebugging) {
            Debugger debugger = new Debugger();
            drawables.add(debugger);
        }

        Map map = new Map();
        drawables.add(map);

        localPlayer = new Player(map,"Thomas");
        drawables.add(localPlayer);

        gameLoop = new GameLoop();

        //spawns a monster (or 50)
        Sound splat = Gdx.audio.newSound(Gdx.files.internal("sounds/Squish.mp3"));
        int spawnCount=1000;
        Random gen=new Random();
        for(int i=0; i<spawnCount; i++)
        {
            int id=gen.nextInt(100)+1;
            if(id<=20)
            {
                Monster monster=new Monster(map,"M", localPlayer, splat);
                gameLoop.addUpdatable(monster);
                drawables.add(monster);
            }
            else if(id>20 && id<=40)//30% move towards you
            {
                MonsterTowards monster=new MonsterTowards(map, "1",localPlayer, splat);
                gameLoop.addUpdatable(monster);
                drawables.add(monster);
            }
            else 
            {
                MonsterDistance monster=new MonsterDistance(map, "3",localPlayer, splat);
                gameLoop.addUpdatable(monster);
                drawables.add(monster);
            }
        }

        gameLoop.setRunning(true);
        gameLoop.start();

        Updatable spamHello = new Updatable(){
            public void update(){
                System.out.println("Hello");
            }
        };

        gameLoop.addUpdatable((Player)localPlayer);

        Gdx.input.setInputProcessor(new InputHandler((Player)localPlayer));
    }

    @Override
    public void render() {
        //sorting by zIndex before draw
        Collections.sort(drawables);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        synchronized(localPlayer){
            //the view is controlled by the position of local player,
            //so we syncronize with that instance to prevent it changing
            //during rendering
            updateCameraPosition();
            batch.setProjectionMatrix(camera.combined);
            batch.begin();

            for (Drawable drawable : drawables)
                drawable.draw(batch);

            batch.end();
        }

    }

    private void updateCameraPosition(){
        float x = Map.XDIMENSION * localPlayer.getXPos();
        float y = Map.XDIMENSION * localPlayer.getYPos();
        camera.position.set(x, y, 0);
        camera.update();
    }
}
