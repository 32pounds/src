package com.multi;

import java.net.*;
import java.io.*;
import java.util.*;

public abstract class MessageHandler{
    public abstract void handle(String received);
    class ProcessRunner implements Runnable{
        String received;
        public ProcessRunner(String received){
            this.received = received;
        }
        public void run(){
            handle(received);
        }
    }
    public void process(String received){
        ( new Thread(new ProcessRunner(received)) ).start();
    }
}
