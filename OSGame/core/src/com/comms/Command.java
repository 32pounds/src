package com.comms;

/**
 * Created by gabrielgiovaninidesouza on 3/16/15.
 */
public enum Command {

    MOVE_UP,
    MOVE_DOWN,
    MOVE_LEFT,
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
