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
     * created the function falls back to SendPacket() to continually
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

                } catch (IOException e) {
                    e.printStackTrace();
                }
            
            
    }

    /*  SendPacket() will continually send out UDP packets,
    *   TODO: Setup function to accept gamestate entities and
    *   send them out. Currently I'm not sure how this'll work
    *   but it will be as simple as calling the function:
    *   SendPacket( EntityList list), and the render fucntion
    *   will be able to send these out as fast as possible.
    */
    public void SendPacket( DatagramPacket toSendPacket ) throws InterruptedException{
        try{
            udpSocket.send(toSendPacket);
        }catch(Exception e){System.out.println("Sorry :( " + e);}
    }

    /* SendString() will take in a string, break it into bytes
     * stuff the bytes into a packet then call SendPacket() to
     * send off the UDP packet.
     */
    public void SendString( String dataToSend ){
        try{
            byte[] buff = new byte[1024];
            buff = dataToSend.getBytes();
            System.out.println("sending...");
            packet = new DatagramPacket(buff, buff.length, address, replyPort);
    
            SendPacket(packet);
        }catch(Exception e){System.out.println("Couldn't send string " + e);}
    }

    /* SetupHost will setup a server by binding to the 
     * assigned port and begin listening on that socket.
     * There is some code like the PrintWriter that will
     * allow command line messages to be sent, this is
     * non-essential and will be removed once debugging
     * of the program is finished
     * 
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

    public void RecievePacket() throws InterruptedException{
        try{
            byte[] buff = new byte[1024];
            DatagramPacket clientPacket = new DatagramPacket(buff, buff.length, address, 5051);
            udpSocket.receive(clientPacket);

        }catch(Exception e){System.out.println("server uh oh:  " +e);}
    }

    /* CloserServer() should be called when multiplayer is over. */
    public void CloseServer(){
        // close socket and let others know the server is no longer up.
        udpSocket.close();
        isUp = false;
    }

}
