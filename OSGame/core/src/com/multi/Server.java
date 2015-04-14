package com.OSG;

import java.net.*;
import java.io.*;
import java.util.*;
import Commun.*;

public class Server implements MessangerListener{
    public static int portNum = 5050;
    private String hostIP;
    Scanner inputReader;
    Scanner IPReader;
    private Messenger messenger;
    private SenderThread senderThread;

    // Constructor will intialize port number 
    // and a command line scanner for debugging.
    // Then calle SetupHost() to begin networking.
    // As well setup UDP listeners.
    public Server(int Port){
        messenger = new Messenger();
        messenger.addListener(this);

        hostIP = null;
        inputReader = new Scanner(System.in);
	IPReader = new Scanner(System.in);
	portNum = Port;
        SetupHost();
    }
   
    /* SetupHost will setup a server by binding to the 
     * assigned port and begin listening on that socket.
     * There is some code like the PrintWriter that will
     * allow command line messages to be sent, this is
     * non-essential and will be removed once debugging
     * of the program is finished
     * +Jordan
     */
    private static void SetupHost() throws SocketException{
        
        // TCP setup 
        try{
            String data = "foo";
            System.out.println("Attempting to bind to port...");
	    ServerSocket servr = new ServerSocket(portNum);
	    System.out.println("Port bound! waiting for client...");
	    Socket listeningSocket = servr.accept();
	    System.out.println("Connection established!");
			
	    PrintWriter out = new PrintWriter(listeningSocket.getOutputStream(), true);
	    System.out.println("Sending data...");
	    out.print(data);
	    out.close();
	    listeningSocket.close();
	    servr.close();
        }catch(Exception e){ System.out.println("NETWORKING ERROR SERVER SIDE: " + e); }
        
        // UDP setup
        DatagramSocket datagramSocket = new DatagramSocket(portNum);

        receiverThread = new ReceiverThread( datagramSocket, messanger );
        receiverThread.start();


    }

}
