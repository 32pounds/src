/* VirusEntry.java
 * by Elizabeth Hernandez
 * developed from MonsterTowards by Michael
 * 5-6-15
 */
package com.model;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.TimeUtils;
import com.comms.GameID;
import com.comms.GameState;
import com.map.Direction;
import com.map.Map;
import com.map.Position;

/**
 *
 * @author elizabeth
 */
public class VirusEntry extends Monster
{
    public Map map;
    VirusManager manager;
    
    public VirusEntry(GameState state, String img, Sound splatSound, VirusManager mngr) 
    {
        super(state, img, splatSound);
        super.changeAlive("VirusEntry");
        super.changeDeath("Blank");
	//super.changeSplat("");
        UPDATE_INTERVAL=10000;
        map=gameState.gameMap();
        manager = mngr;
        position = manager.placeVirusEntry();
    }
    
    @Override
    public void update()
    {
        if(isDead()==true)
            return;
        map=gameState.gameMap();
        if(wasDead==true)
        {
            //come alive
            wasDead=false;
            changeSprite(alive);
        }
        else
        {
            if(squished()==true)
            {
                deathTime=TimeUtils.millis();
                changeSprite(dead);
                splat.play();
                wasDead=true;
            }

            // time to try to spawn a virus queen
            else if(TimeUtils.millis()-lastUpdateTime > UPDATE_INTERVAL)
            {   
                // reset update timer
                lastUpdateTime=TimeUtils.millis();
                
                // ask to spawn queen
                
            }
        }
    }
}
