package com.comms;

/**
 * All action that a player can do.
 */
public enum Command {
    /**
     * Pressed to go a tile up
     */
    MOVE_UP,
    /**
     * Pressed to go a tile down
     */
    MOVE_DOWN,
    /**
     * Pressed to go a tile left
     */
    MOVE_LEFT,
    /**
     * Pressed to go a tile right
     */
    MOVE_RIGTH

    /*
    These are the initial command
    We can also add other like:
    SHOOT,
    TAKE_ITEM,
    TALK,

    The server decides if this commands are valid or not, and then apply them in the game state

    And we can add char bind if necessary
    MOVE_UP 'a',
    MOVE_DOWN 'b',
    MOVE_LEFT 'c',
    MOVE_RIGTH 'd'
     */

}
