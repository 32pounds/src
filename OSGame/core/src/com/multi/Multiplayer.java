package com.multi;

import java.io.*;


public class Multiplayer{

	// Will start server or client depending
	// on passed in variable.
    public Multiplayer( boolean ServerOrClient ) throws IOException{
        
        if(ServerOrClient){
        	ServerThread server = new ServerThread(5050);
        }else{ // client stuff
        	
        }
    }

}
