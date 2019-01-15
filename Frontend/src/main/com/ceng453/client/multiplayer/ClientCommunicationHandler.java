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
                if(!receivedJson.isNull("winner"))
                    delegatorClass.announceWinner(receivedJson);
                else if(!receivedJson.isNull("tick"))
                    delegatorClass.updateGameTick(receivedJson);
                else if(!receivedJson.isNull("has_shot"))
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
        report.put("x", delegatorClass.getUserShip().getPositionX());
        report.put("y", delegatorClass.getUserShip().getPositionY());
        report.put("hp", delegatorClass.getUserShip().getHitPointsLeft());
        try {
            report.put("alien_hp", delegatorClass.getBoss().getHitPointsLeft());
        } catch (IndexOutOfBoundsException e){
            report.put("alien_hp", 0);
        }
        report.put("has_rival_destroyed_boss", delegatorClass.has_rival_destroyed_boss);
        report.put("has_shot", has_shot);
        out.println(report.toString());
        out.flush();
    }

}
