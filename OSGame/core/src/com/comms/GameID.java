package com.comms;
/**
 * ID class that will be used by the comms system to refer to objects
 * on every client/server connected to the game universally
 * @author Brett Menzies
 */
public class GameID{
    private static int idGen = 0;
        //I don't want this in the ascii range
        //because the parser probably will not handle that well
    /**
     * newID is here to generate a so far unused id in this JVM instance
     * It is separate from the constructor so that it may be synchronized to
     * prevent id duplication in multi threaded scenarios
     */
    private static synchronized int newID(){
        idGen++;
        return idGen;
    }

    private final int id;
    private GameID(int inputID){
        id = inputID;
    }
    public GameID(){
        id = newID();
    }
    public GameID(Number inputID){
        id = (int) inputID.shortValue();
    }
    public GameID(String inputID){
        id = Integer.valueOf(inputID);
    }

    /**
     * Used to transmit the ID over the network
     */
    public String toString(){
        return (""+id);
    }

    @Override
    public boolean equals(Object obj){
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        GameID otherID = (GameID) obj;
        return (otherID.id == this.id);
    }

    @Override
    public GameID clone(){
        return new GameID(id);
    }
    /**
     * The contract for hashCode is defined by the class's
     * "equals" function, so it is overridden to math equals'
     * use of the internally stored ID
     * see: https://docs.oracle.com/javase/7/docs/api/java/lang/Object.html#hashCode%28%29
     */
    @Override
    public int hashCode(){
        return (int) id;
    }
}
