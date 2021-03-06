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
    protected static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private String serverAddress = "127.0.0.1";
    private DatagramSocket udpSocket = null;
    private DatagramPacket packet = null;
    public boolean isUp;
    private InetAddress servAddress = null;
    private MessageHandler handler;
    private GameID localPlayer;

    public ClientThread(int myPort,int servPort, MessageHandler handler){
        this.handler = handler;
        this.myPort = myPort;
        this.remotePort = servPort;

        in = new BufferedReader(new InputStreamReader(System.in));

        try{
            udpSocket = new DatagramSocket(myPort);
            udpSocket.setSoTimeout(500);
        }catch(Exception e){e.printStackTrace();}
        isUp = false;
    }

    private class ReceiveThread extends Thread{
        public void run(){
            while(true){
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

    public void AddPlayer(){

    }

    public GameID JoinGame(){
        return JoinGame("127.0.0.1");
    }

    /* JoinGame() will initialize a connection to a server
     *
     *
     */
    public synchronized GameID JoinGame(String address){
        serverAddress = address;

        if(localPlayer == null) localPlayer = new GameID((Number)0);

        if(receiveThread == null) {
            receiveThread = new ReceiveThread();
            receiveThread.start();
        }

        ConnectToServer();

        return localPlayer;
    }

    /* ConnectToServer() will setup a UDP socket and packet
     * and fire off the packet to a server whos info is stored
     * in above variables.
     */
    public void ConnectToServer(){
        try{
            // Send UDP request to server.
            byte[] buf = new byte[0];
            servAddress = InetAddress.getByName(serverAddress);
            System.out.println("CLIENT: Creating packet...");
            packet = new DatagramPacket(buf, buf.length, servAddress, remotePort);
            System.out.println("CLIENT: Packet created");

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
     */
    public void RecieveGameState() throws InterruptedException{
        try{
            byte[] buff = new byte[2048];
            DatagramPacket gamePacket = new DatagramPacket(buff, buff.length);
            udpSocket.receive(gamePacket);

            if ( !(gamePacket.getAddress()).equals(servAddress) ) return;

            byte[] data = gamePacket.getData();
            String temp = new String(gamePacket.getData(), 0, gamePacket.getLength());


            if(!GameID.isValidIDString(temp)){
                handler.process(temp);
                return;
            }
            System.out.println( "GameID Connect Packet: " + new String(gamePacket.getData(), 0, gamePacket.getLength()) );
            int nullIndex = temp.indexOf('\0');
            if(nullIndex > 0) temp = temp.substring(0, nullIndex);
            GameID playerID = new GameID(temp);

            localPlayer.copy(playerID);

        }catch(SocketTimeoutException e){
        }catch(Exception e){e.printStackTrace();}
    }

    public void CloseConnection(){
        udpSocket.close();
        isUp = false;
    }
}
