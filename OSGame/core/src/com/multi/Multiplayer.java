package com.multi;

import java.io.*;


public class Multiplayer{

	// Will start server or client depending
	// on passed in variable.
    public Multiplayer( boolean ServerOrClient ) throws IOException{

        if(ServerOrClient){
            MessageHandler handler = new MessageHandler(){
                @Override
                public void handle(String message){
                    System.out.println("Server receive"+message);
                }
            };
        	ServerThread server = new ServerThread(5050,handler);
        }else{ // client stuff

        }
    }

}
