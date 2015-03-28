/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.badlogic.gdx.utils.TimeUtils;
import com.map.Direction;
import com.map.Map;
import com.renderer.Updatable;
import java.util.Random;

/**
 *
 * @author michael
 */
public class Monster extends Entity implements Updatable{

    private long lastUpdateTime;
    private static final long UPDATE_INTERVAL=100;
    private static Random randomGen= new Random();
    public Monster(Map map, String img) 
    {
        super(map, img);
        lastUpdateTime=0;
    }
    
    @Override
    public void update()
    {
        if(TimeUtils.millis()-lastUpdateTime > UPDATE_INTERVAL)
        {
            lastUpdateTime=TimeUtils.millis();
            int dir=randomGen.nextInt(4);
            if(dir==0)
                super.move(Direction.SOUTH);
            else if(dir==1)
                super.move(Direction.NORTH);
            else if(dir==2)
                super.move(Direction.EAST);
            else
                super.move(Direction.WEST);
        }
        
    }
    
}
