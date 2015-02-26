package Server;

import Commun.Messenger;
import Commun.MessengerListener;
import Commun.ReceiverThread;
import Commun.SenderThread;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by gabrielgiovaninidesouza on 2/20/15.
 */
public class GameServer implements MessengerListener {

    private final int port = 7777;
    private ReceiverThread receiverThread;
    private SenderThread senderThread;
    private Messenger messenger;

    public GameServer() {
        messenger = new Messenger();
        messenger.addListener(this);
    }

    public static void main(String args[]) throws Exception {
        GameServer gameServer = new GameServer();
        gameServer.turnOn();
    }

    public void turnOn() throws SocketException {
        DatagramSocket datagramSocket = new DatagramSocket(port);

        receiverThread = new ReceiverThread(datagramSocket, messenger);
        receiverThread.start();
    }

    @Override
    public void Message(String message) {
        System.out.println(Integer.toString(message.getBytes().length) + " - Saida: " + message);
    }
}
