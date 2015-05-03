// ServerThread.java
// Author(s): Jordan Lynn
// TODO: Have server keep track of multiple clients.

package com.multi;

import com.multi.MessageHandler;

import java.net.*;
import java.io.*;
import java.util.*;
import com.comms.*;
import com.gameloop.*;

public class ServerThread extends Thread{

    public static int portNum = 5050;
    protected DatagramSocket udpSocket = null;
    protected static BufferedReader in = null;
    protected PrintWriter out = null;
    private boolean isUp;
    private DatagramPacket packet = null;
    private InetAddress address = null;
    private int replyPort;
    private MessageHandler handler;
    private clientLL clientList = null;
    private GameLoop gameLoop = null;


    // Constructor will intialize port number
    // and a command line scanner for debugging.
    // Then calle SetupHost() to begin networking.
    // As well setup UDP listeners.
    public ServerThread( int port, MessageHandler handler, GameLoop gl) throws IOException {
        this.handler = handler;
        portNum = port;
        System.out.println("Creating UDP socket @:" + portNum );
        isUp = false;
        clientList = new clientLL();
        gameLoop = gl;
    }

    public void run(){
        System.out.println("Hello from thread!");
        setupUDP();
        while(true){
            try{
                RecievePacket();
                sleep(5);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void OnConnect(InetAddress newClient, int port){
        GameID player;
        int clientIndex = clientList.GetByAddress(newClient);
        if (clientIndex != -1) {
            player = clientList.GetPlayerID(clientIndex);
        } else {
            player = gameLoop.requestNewPlayer();
            clientList.CreateClient(newClient, port, player);
        }

        String temp = String.valueOf(player.toChar());
        byte[] buff = temp.getBytes();
        DatagramPacket connectedClientPacket = new DatagramPacket(buff, buff.length, newClient, port);
        System.out.println("Sent player ID message");
        try{SendPacket(connectedClientPacket);
        }catch(Exception e){e.printStackTrace();}
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
            System.out.println("Waiting to receive @: " + udpSocket.getLocalAddress() + " port " + udpSocket.getLocalPort());
            //udpSocket.receive(recievedPacket);

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
            if(udpSocket != null){
                udpSocket.send(toSendPacket);
            }
        }catch(Exception e){e.printStackTrace();}
    }

    /* SendString() will take in a string, break it into bytes
     * stuff the bytes into a packet then call SendPacket() to
     * send off the UDP packet.
     */
    public void SendString( String dataToSend ){

        for(int i=0; i < clientList.Size(); i++ ){

            try{
                byte[] buff = new byte[1024];
                buff = dataToSend.getBytes();
                packet = new DatagramPacket(buff, buff.length, clientList.GetIP(i), clientList.GetPort(i));

                SendPacket(packet);
            }catch(Exception e){e.printStackTrace();}
        }
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
            byte[] data = clientPacket.getData();

            System.out.println("SERVER: Got data of length "+clientPacket.getLength());
            if(clientPacket.getLength() == 0){
                OnConnect(clientPacket.getAddress(), clientPacket.getPort());
            }else{
                handler.process(new String(data));
                System.out.println("Recieved from client");
            }
        }catch(Exception e){e.printStackTrace();}
    }

    /* CloserServer() should be called when multiplayer is over. */
    public void CloseServer(){
        // close socket and let others know the server is no longer up.
        udpSocket.close();
        isUp = false;
    }

}
