package com.comms;
import com.comms.GameState;

/**
 * abstract class for abstract commands representing the users actions
 * These are intended to be constructed by the input system
 * then tranported by the comms system before being sent to the
 * main game logic loop to process
 * @author Brett Menzies
 */
public abstract class Command{
    abstract public void execute(GameState state);

    abstract public char[] getData();
    abstract protected void restore(String data);
    public char[] packetize(){
        char[] data = getData();
        Class  subclass = this.getClass();
        String subclassName = this.getClass().getName();
        String resp = subclassName+':';
        for(int i=0; i<data.length; i++){
            resp = resp+data[i];
        }
        return resp.toCharArray();
    }
    public static Command parse(char[] input){
        String inputData = String.valueOf(input);
        int split = inputData.indexOf(':');
        String className = inputData.substring(0,split);
        String data = inputData.substring(split+1,inputData.length());
        //data = data.substring( 0, data.indexOf(' ') );
        Command cmd = null;
        try{
            Class commandSubclass = Class.forName(className);
            cmd = (Command) commandSubclass.newInstance();
            cmd.restore(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cmd;
    }
}
