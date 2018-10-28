package com.ceng453.Server;

public class SinglePlayerGameInstance implements GameInstance {
    private String assignedUserToken;
    private Thread workerThread;


    public SinglePlayerGameInstance(String tokenOfUser) {
        assignedUserToken = tokenOfUser;
        workerThread = new Thread( this );
    }

    @Override
    public void StartGame() {
        workerThread.start();
    }

    @Override
    public void run() {
        System.out.println("Thread started for user " + assignedUserToken);
    }
}
