// ServerThread.java
// Author(s): Jordan Lynn
// TODO: Have server keep track of multiple clients.

package com.multi;

import java.net.*;
import java.io.*;
import java.util.*;


public class ServerThread extends Thread{

    public static int portNum = 5050;
    protected DatagramSocket udpSocket = null;
    protected static BufferedReader in = null;
    protected PrintWriter out = null;
    private boolean isUp;
    private DatagramPacket packet = null;
    private InetAddress address = null;
    private int replyPort;

    // Constructor will intialize port number 
    // and a command line scanner for debugging.
    // Then calle SetupHost() to begin networking.
    // As well setup UDP listeners.
    public ServerThread( int port) throws IOException {
        portNum = port;
        System.out.println("Creating UDP socket @:" + portNum );
        isUp = false;

    }

    public void run(){
        System.out.println("Hello from thread!");
        setupUDP();
    }

    /* getServerStatus()
        will return true if the server is up and false if the server is down.
    */
    public boolean getServerStatus(){
        return isUp;
    }

    /* setupUDP() will get info from a newly connected client and 
     * store the info like port number and IP address into a variable
     * such as "address" or "port". Once the socket and packet are
     * created the function falls back to SendGameState() to continually
     * send out the state.
     * 
     * TODO: Have setupUDP add to list of clients.
     */
    public void setupUDP() {
            System.out.println("Creating UDP packet/socket...");
                try {
                    udpSocket = new DatagramSocket(portNum);
                    
                    byte[] buf = new byte[256];
                
                    // receive request
                    DatagramPacket recievedPacket = new DatagramPacket(buf, buf.length);
                    System.out.println("Packet created!");
                    System.out.println("Waiting to receive @: " + udpSocket.getLocalAddress() + " port " + udpSocket.getLocalPort());
                    udpSocket.receive(recievedPacket);
                    
                    System.out.println("Packet received!");
                    // figure out response
                    String dString = null;
                    if (in == null) dString = new Date().toString();
                    
                    buf = dString.getBytes();
                    
                    System.out.println("Sending response...");
                    // send the response to the client at "address" and "port"
                    address = recievedPacket.getAddress();
                    replyPort = recievedPacket.getPort();
                    System.out.println("Got packet info " + address + ":" + replyPort );
                    packet = new DatagramPacket(buf, buf.length, address, replyPort);
                    udpSocket.send(packet);
                    // set isUp to true to let others know the server is running.
                    isUp = true;

                    try{SendGameState();}catch(Exception e){System.out.println("Couldn't time " + e);}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            
            
    }

    /*  SendGameState() will continually send out UDP packets,
    *   TODO: Setup function to accept gamestate entities and
    *   send them out. Currently I'm not sure how this'll work
    *   but it will be as simple as calling the function:
    *   SendGameState( EntityList list), and the render fucntion
    *   will be able to send these out as fast as possible.
    */
    public void SendGameState() throws InterruptedException{
        while(true){
            try{
            String date = new Date().toString();
            byte[] buff = new byte[256];
            buff = date.getBytes();
            System.out.println("sending...");
            packet = new DatagramPacket(buff, buff.length, address, replyPort);
            udpSocket.send(packet);
            sleep(1000);
        }catch(Exception e){System.out.println("Uh oh.. " + e);}
        }
    }

    /* CloserServer() should be called when multiplayer is over. */
    public void CloseServer(){
        // close socket and let others know the server is no longer up.
        udpSocket.close();
        isUp = false;
    }

    /* SetupHost will setup a server by binding to the 
     * assigned port and begin listening on that socket.
     * There is some code like the PrintWriter that will
     * allow command line messages to be sent, this is
     * non-essential and will be removed once debugging
     * of the program is finished
     * +Jordan
     */
    private static void SetupTCP() throws SocketException{
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
    }

    

}
