// ClientThread.java
// Author(s): Jordan Lynn
// TODO: void RecieveGameState() shouldn't return void. See discriptions.

package com.multi;

import com.multi.MessageHandler;
import com.comms.*;

import java.net.*;
import java.io.*;
import java.util.*;


public class ClientThread{
    private int myPort;
    private int remotePort;
    protected DatagramSocket udpSocket1 = null;
    protected static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    protected PrintWriter out = null;
    private String serverAddress = "127.0.0.1";
    private DatagramSocket udpSocket = null;
    private DatagramPacket packet = null;
    public boolean isUp;
    private InetAddress servAddress = null;
    private MessageHandler handler;

    public ClientThread(int myPort,int servPort, MessageHandler handler){
        this.handler = handler;
        this.myPort = myPort;
        this.remotePort = servPort;
        in = new BufferedReader(new InputStreamReader(System.in));

        try{
            udpSocket = new DatagramSocket(myPort);
        }catch(Exception e){e.printStackTrace();}
        isUp = false;
    }

    private class ReceiveThread extends Thread{
        public void run(){
            while(isUp){
                try{
                    RecieveGameState();
                    sleep(5);
                } catch (InterruptedException e){
                    break;
                } catch (Exception e){
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
    private ReceiveThread receiveThread;


    public GameID JoinGame(){
        return JoinGame("127.0.0.1");
    }

    /* joinGame() will initialize a connection to a server a
     *
     *
     */
    public GameID JoinGame(String address){
        isUp = false;
        serverAddress = address;
        try{
            servAddress = InetAddress.getByName(serverAddress);
        }catch(Exception e){e.printStackTrace();}

        ConnectToServer();
        byte[] buff = new byte[64];

        DatagramPacket gamePacket = new DatagramPacket(buff, buff.length, servAddress, remotePort);
        try{
            udpSocket.receive(gamePacket);
        } catch (Exception e){
            e.printStackTrace();
        }
        String temp = new String(gamePacket.getData(), 0, gamePacket.getLength());
        GameID playerID = new GameID(temp.charAt(0));
        System.out.println(temp);
        if(receiveThread != null) receiveThread.interrupt();
        receiveThread = new ReceiveThread();
        receiveThread.start();
        return playerID;
    }

    /* ConnectToServer() will setup a UDP socket and packet
     * and fire off the packet to a server that's give in the
     * arguments that are passed in.
     */
    public void ConnectToServer(){

        try{
            // Create UDP socket.
            System.out.println("CLIENT: Creating socket...");


            System.out.println("CLIENT: Socket created @ port: " + udpSocket.getLocalPort());

            // Send UDP request to server.
            byte[] buf = new byte[0];
            servAddress = InetAddress.getByName(serverAddress);
            System.out.println("CLIENT: Creating packet...");
            packet = new DatagramPacket(buf, buf.length, servAddress, remotePort);
            System.out.println("CLIENT: Packet created with payload: " + buf);

            System.out.println("CLIENT: Sending packet to: " + servAddress + ":" + packet.getPort());

            udpSocket.send(packet);
            System.out.println("CLIENT: Packet sent! ");

            isUp = true;
        } catch(Exception e){ e.printStackTrace();}
    }

    /* SendString() will take in a string, break it into bytes
     * stuff the bytes into a packet then call SendPacket() to
     * send off the UDP packet.
     */
    public void SendString( String dataToSend ){
        try{
            byte[] buff = new byte[2048];
            buff = dataToSend.getBytes();
            packet = new DatagramPacket(buff, buff.length, servAddress, remotePort);

            SendPacket(packet);
        }catch(Exception e){System.out.println("Couldn't send string " + e);}
    }

    public void SendPacket( DatagramPacket toSendPacket ) throws InterruptedException{
        try{
            udpSocket.send(toSendPacket);
        }catch(Exception e){e.printStackTrace();}
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
            byte[] buff = new byte[2048];
            DatagramPacket gamePacket = new DatagramPacket(buff, buff.length, servAddress, remotePort);
            udpSocket.receive(gamePacket);
            byte[] data = gamePacket.getData();
            handler.process(new String(data));
        }catch(Exception e){e.printStackTrace();}
    }

    public void CloseConnection(){
        udpSocket.close();
        isUp = false;
    }
}
