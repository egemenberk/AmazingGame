package main.com.ceng453.frontend.main;

import main.com.ceng453.ApplicationConstants;
import main.com.ceng453.frontend.gamelevels.MultiplayerGameLevel;
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
            this.serverSocket = new Socket(ApplicationConstants.GameServerIP, ApplicationConstants.GameServerPort);
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

                if(!receivedJson.isNull("winner"))
                    delegatorClass.announceWinner(receivedJson);
                else if(!receivedJson.isNull("tick"))
                    delegatorClass.updateGameTick(receivedJson);
                else if(!receivedJson.isNull("has_shot"))
                    delegatorClass.updateRivalShip(receivedJson);
                else // Just in case, no condition will lead that else
                    is_terminated = true;

            } catch (IOException e) {
                is_terminated = true;
                e.printStackTrace();
            }
        }
    }

    public void send_data( boolean has_shot ) {
        JSONObject userShip = new JSONObject();
        userShip.put("x", delegatorClass.getUserShip().getPositionX());
        userShip.put("y", delegatorClass.getUserShip().getPositionY());
        userShip.put("hp",delegatorClass.getUserShip().getHitPointsLeft());
        userShip.put("alien_hp",delegatorClass.getBoss().getHitPointsLeft());
        userShip.put("has_shot", has_shot);
        out.println(userShip.toString());
        out.flush();
    }

}
