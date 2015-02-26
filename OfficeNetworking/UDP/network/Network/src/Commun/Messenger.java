package Commun;

import java.util.ArrayList;

public class Messenger {
    private ArrayList<MessengerListener> listeners = new ArrayList<MessengerListener>();

    public void addListener(MessengerListener listener) {
        listeners.add(listener);
    }

    public void sendMessage(String message) {
        for (MessengerListener listener : listeners)
            listener.Message(message);
    }
}

