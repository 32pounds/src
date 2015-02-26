package Commun;

import sun.misc.Queue;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.function.Predicate;

public class SenderThread extends Thread {

    private InetAddress inetAddress;
    private DatagramSocket datagramSocket;
    private int serverPort;
    private Queue<String> messages;

    public SenderThread(InetAddress inetAddress, DatagramSocket datagramSocket, int serverPort) {
        this.inetAddress = inetAddress;
        this.datagramSocket = datagramSocket;
        this.serverPort = serverPort;
        this.messages = new Queue<String>();
    }

    private void Test (Predicate<String> callback)
    {

    }

    @Override
    public void run() {
        super.run();

        while (true) {
            if (messages.isEmpty())
                continue;

            byte[] data = new byte[0];
            try {
                data = messages.dequeue().getBytes();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            DatagramPacket packet = new DatagramPacket(data, data.length, inetAddress, serverPort);

            try {
                datagramSocket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Thread.yield();
        }
    }

    public void sendMessage(String message) {
        messages.enqueue(message);
    }
}
