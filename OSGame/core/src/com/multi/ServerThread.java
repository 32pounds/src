package com.multi;

import java.net.*;
import java.io.*;
import java.util.*;


public class ServerThread extends Thread{

    public static int portNum = 5050;
    protected DatagramSocket udpSocket = null;
    protected static BufferedReader in = null;
    protected PrintWriter out = null;

    // Constructor will intialize port number 
    // and a command line scanner for debugging.
    // Then calle SetupHost() to begin networking.
    // As well setup UDP listeners.
    public ServerThread( int port) throws IOException {
        portNum = port;
        System.out.println("Creating UDP socket @:" + portNum );
    }
   

    public void runUDP() {
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
                    InetAddress address = recievedPacket.getAddress();
                    int replyPort = recievedPacket.getPort();
                    System.out.println("Got packet info " + address + ":" + replyPort );
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, address, replyPort);
                    udpSocket.send(packet);
            
                } catch (IOException e) {
                    e.printStackTrace();
            
                }
            
            udpSocket.close();
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
