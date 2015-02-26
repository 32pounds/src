package Client;

import Commun.Messenger;
import Commun.MessengerListener;
import Commun.ReceiverThread;
import Commun.SenderThread;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class GameClient implements MessengerListener {
    private final int port = 7777;
    private final String serverHost = "localhost";
    private ReceiverThread receiverThread;
    private SenderThread senderThread;
    private Messenger messenger;


    public GameClient() {
        messenger = new Messenger();
        messenger.addListener(this);
    }

    public static void main(String args[]) throws Exception {
        GameClient gameClient = new GameClient();
        gameClient.connect();
        double count = 0;
        while (true) {
            //char[] chars = new char[1024];
            //Arrays.fill(chars, 'f');
            gameClient.sendMessage(Double.toString(count++));
        }
    }

    public void connect() throws SocketException, UnknownHostException {
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress inetAddress = InetAddress.getByName(serverHost);
        datagramSocket.connect(inetAddress, port);

        receiverThread = new ReceiverThread(datagramSocket, messenger);
        receiverThread.start();

        senderThread = new SenderThread(inetAddress, datagramSocket, port);
        senderThread.start();
    }

    @Override
    public void Message(String message) {
        System.out.println("Saida: " + message);
    }

    public void sendMessage(String message) {
        senderThread.sendMessage(message);
    }
}
