package com.ceng453.frontend;

import java.util.ArrayList;

public class SinglePlayerGameInstance implements GameInstance {
    private String assignedUserToken;
    private Thread workerThread;
    private Object responseGenerationLock;
    private String responseAsJson;
    private boolean terminateSignal;
    private long currentGameCycle;

    private ArrayList<GameObject> alienShips;
    private GameObject playerShip;
    private ArrayList<GameObject> bullets;


    public SinglePlayerGameInstance(String tokenOfUser) {
        assignedUserToken = tokenOfUser;

        workerThread = new Thread( this );

        currentGameCycle = 0;
    }

    @Override
    public void run() {
        System.out.println("Thread started for user " + assignedUserToken);

        while(!terminateSignal){
            currentGameCycle++;

            ProcessUserInput(); // read user input
            UpdateGameState();// update game
            serialize();

            try {
                Thread.sleep(30); // TODO adjust to keep fps stable
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void ProcessUserInput(){

    }

    private void UpdateGameState(){
        for( GameObject alienShip : alienShips ){
            alienShip.update(currentGameCycle);
        }
    }

    private String serialize(){
        synchronized (responseGenerationLock) {
            String responseAsJson = "asd";//Json.createObjectBuilder().add("key", "value").build().toString();

            return responseAsJson;
        }
    }

    public String GetGameStateAsJson(){
        synchronized (responseGenerationLock){
            return responseAsJson;
        }
    }

    public void setTerminateSignal(boolean terminateSignal) {
        this.terminateSignal = terminateSignal;
    }

    @Override
    public void StartGame() {
        workerThread.start();
    }
}
