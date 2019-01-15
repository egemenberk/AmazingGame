package main.com.ceng453.client.multiplayer;

import main.com.ceng453.ApplicationConstants;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class ClientCommunicationHandler{

    private MultiplayerGameLevel delegatorClass;
    private Socket serverSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Thread receiveDataFromServerThread;

    private boolean is_terminated = false;


    public void initiate( MultiplayerGameLevel delegatorClass ) {
        this.delegatorClass = delegatorClass;
        try {
            this.serverSocket = new Socket(ApplicationConstants.GAME_SERVER_IP, ApplicationConstants.GAME_SERVER_PORT);
            this.out = new PrintWriter(serverSocket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        receiveDataFromServerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                receiveDataFromServer();
            }
        });
        receiveDataFromServerThread.start();
    }

    public void receiveDataFromServer() {
        while (!is_terminated)
        {
            try {
                JSONObject receivedJson = new JSONObject(in.readLine());
                System.out.println(receivedJson);
                if(!receivedJson.isNull(ApplicationConstants.JSON_KEY_WINNER_FLAG))
                    delegatorClass.announceWinner(receivedJson);
                else if(!receivedJson.isNull(ApplicationConstants.JSON_KEY_TICK))
                    delegatorClass.updateGameTick(receivedJson);
                else if(!receivedJson.isNull(ApplicationConstants.JSON_KEY_HAS_SHOT_THIS_TURN))
                    delegatorClass.updateRivalShip(receivedJson);
                else // Just in case, no condition will lead that else
                    is_terminated = true;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                is_terminated = true;
            }
        }
    }

    public void send_data( boolean has_shot ) {
        JSONObject report = new JSONObject();
        report.put(ApplicationConstants.JSON_KEY_X, delegatorClass.getUserShip().getPositionX());
        report.put(ApplicationConstants.JSON_KEY_Y, delegatorClass.getUserShip().getPositionY());
        report.put(ApplicationConstants.JSON_KEY_USER_HP, delegatorClass.getUserShip().getHitPointsLeft());
        try {
            report.put(ApplicationConstants.JSON_KEY_ALIEN_HP, delegatorClass.getBoss().getHitPointsLeft());
        } catch (IndexOutOfBoundsException e){
            report.put(ApplicationConstants.JSON_KEY_ALIEN_HP, 0);
        }
        report.put(ApplicationConstants.JSON_KEY_HAS_RIVAL_WON, delegatorClass.has_rival_destroyed_boss);
        report.put(ApplicationConstants.JSON_KEY_HAS_SHOT_THIS_TURN, has_shot);
        out.println(report.toString());
        out.flush();
    }

}
