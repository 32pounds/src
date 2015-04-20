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
    

    public ClientThread(String address, int port){
    	if (address != "127.0.0.1") serverAddress = address;
    	if( port != 5050) portNum = port;
        in = new BufferedReader(new InputStreamReader(System.in));
        ConnectToServer();
    }
    
    public void ConnectToServer(){

        try{
            // Create UDP socket.
            System.out.println("Creating socket...");
            DatagramSocket udpSocket = new DatagramSocket(5050);
 
            System.out.println("Socket created @ port: " + udpSocket.getLocalPort());

            // Send UDP request to server.
            byte[] buf = new byte[256];
            InetAddress servAddress = InetAddress.getByName(serverAddress);
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
        
            udpSocket.close();
        } catch(Exception e){ System.out.println("Couldn't setup UDP client!" + e);}
    }
}