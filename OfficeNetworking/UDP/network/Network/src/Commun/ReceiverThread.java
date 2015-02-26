package Commun;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReceiverThread extends Thread {

    private DatagramSocket datagramSocket;
    private Messenger messenger;

    public ReceiverThread(DatagramSocket datagramSocket, Messenger messenger) {
        this.datagramSocket = datagramSocket;
        this.messenger = messenger;
    }

    @Override
    public void run() {
        super.run();
        byte[] receiveData = new byte[1024];

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            try {
                //it will wait until receive a answer
                datagramSocket.receive(receivePacket);

                String answer = new String(receivePacket.getData(), 0, receivePacket.getLength());
                messenger.sendMessage(answer);

                Thread.yield();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
