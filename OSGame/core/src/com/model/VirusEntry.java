/* VirusEntry.java
 * by Elizabeth Hernandez
 * developed from MonsterTowards by Michael
 * 4-29-15
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
    
    public VirusEntry(GameState state, String img, Sound splatSound, int X, int Y) 
    {
        super(state, img, splatSound);
        super.changeAlive("VirusEntry");
        super.changeDeath("CyberFloor");
	//super.changeSplat("");
        UPDATE_INTERVAL=10000;
        map=gameState.gameMap();
    }
    
    @Override
    public void update()
    {
        if(isDead()==true)
            return;

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
