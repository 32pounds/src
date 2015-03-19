package com.renderer;

/**
 * This interface define a class that will be updated by the game loop
 */
public interface Updatable {
    /**
     * this functon will be called when every loop of GameLoop
     */
    void update();
}
