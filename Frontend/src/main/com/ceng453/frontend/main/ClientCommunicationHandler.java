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


    public void initiate( MultiplayerGameLevel delegatorClass ) {
        this.delegatorClass = delegatorClass;
        try {
            this.serverSocket = new Socket(ApplicationConstants.GameServerIP, ApplicationConstants.GameServerPort);
            System.out.println(serverSocket.hashCode());
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
        while (true)
        {
            try {
                JSONObject receivedJson = new JSONObject(in.readLine());
                //System.out.println(receivedJson.toString());
                System.out.println(delegatorClass.rivalShip.getHitPointsLeft());
                if(receivedJson.isNull("tick"))
                    delegatorClass.updateRivalShip(receivedJson);
                else
                    delegatorClass.updateGameTick(receivedJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send_data( boolean has_shot ) {
        JSONObject userShip = new JSONObject();
        userShip.put("x", delegatorClass.getUserShip().getPositionX());
        userShip.put("y", delegatorClass.getUserShip().getPositionY());
        userShip.put("hp",delegatorClass.getUserShip().getHitPointsLeft());
        userShip.put("has_shot", has_shot);
        out.println(userShip.toString());
        out.flush();
    }

}
