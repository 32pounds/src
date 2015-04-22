// ClientThread.java
// Author(s): Jordan Lynn
// TODO: void RecieveGameState() shouldn't return void. See discriptions.

package com.multi;

import java.net.*;
import java.io.*;
import java.util.*;


public class ClientThread extends Thread{

    public static int portNum = 5050;
    protected DatagramSocket udpSocket1 = null;
    protected static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    protected PrintWriter out = null;
    private String serverAddress = "127.0.0.1";
    private DatagramSocket udpSocket = null;
    public boolean isUp;
    private InetAddress servAddress = null;

    public ClientThread(String address, int port){
    	if (address != "127.0.0.1") serverAddress = address;
    	if( port != 5050) portNum = port;
        in = new BufferedReader(new InputStreamReader(System.in));
        
        try{
            udpSocket = new DatagramSocket(portNum);
        }catch(Exception e){System.out.println("Couldn't create socket! " + e);}
        isUp = false;
    }
    
    public void run(){
        System.out.println("Hello from client thread!");
        ConnectToServer();
    }


    /* ConnectToServer() will setup a UDP socket and packet
     * and fire off the packet to a server that's give in the 
     * arguments that are passed in.
     */
    public void ConnectToServer(){

        try{
            // Create UDP socket.
            System.out.println("Creating socket...");
            
 
            System.out.println("Socket created @ port: " + udpSocket.getLocalPort());

            // Send UDP request to server.
            byte[] buf = new byte[256];
            servAddress = InetAddress.getByName(serverAddress);
            System.out.println("Creating packet...");
            DatagramPacket packet = new DatagramPacket(buf, buf.length, servAddress, 5051);
            System.out.println("Packet created with payload: " + buf);
            
            System.out.println("Sending packet to: " + servAddress + ":" + packet.getPort());
            
            udpSocket.send(packet);
            System.out.println("Packet sent! ");

            // Now recieve response.
            packet = new DatagramPacket(buf, buf.length);
            System.out.println("Waiting for response...");
            udpSocket.receive(packet);
            System.out.println("Heard back from server!");
            
            // display response
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Data: " + received);
        
            isUp = true;
            try{RecieveGameState();}catch(Exception e){System.out.println("uh oh client... " + e);}
        } catch(Exception e){ System.out.println("Couldn't setup UDP client!" + e);}
    }

    /* RecieveGameState() will continually recieve UDP packets,
     * and print out the info
     * 
     * TODO: RecieveGameState() shouldn't return "void" but rather
     * a list of entities that it gets from the udp packet, or a
     * character array list that get's taken care of by the parser
     * depending on how this is all gonna go.
     */
    public void RecieveGameState() throws InterruptedException{
        try{
            while(true){
                byte[] buff = new byte[256];
                DatagramPacket gamePacket = new DatagramPacket(buff, buff.length, servAddress, 5051);
                udpSocket.receive(gamePacket);
                String got = new String(gamePacket.getData(), 0, gamePacket.getLength());
                System.out.println("got:  " + got);
                sleep(1);
            }
        }catch(Exception e){System.out.println("client uh oh:  " +e);}
    }

    public void CloseConnection(){
        udpSocket.close();
        isUp = false;
    }
}