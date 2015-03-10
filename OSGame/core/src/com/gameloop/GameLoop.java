package com.gameloop;

public class GameLoop extends Thread {
    private boolean running;
    public void setRunning(boolean running) {
	this.running = running;
    }
	
    @Override
    public void run() {
	if (running) {
            System.out.println("I'm here!");
        }
    }
}

