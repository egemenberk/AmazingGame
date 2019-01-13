package main.com.ceng453.game_server;

import main.com.ceng453.ApplicationConstants;
import main.com.ceng453.MultiplayerCommunicationHandler;
import main.com.ceng453.frontend.gamelevels.GameLevel4;
import org.json.JSONObject;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientCommunicationHandler extends MultiplayerCommunicationHandler {

    private GameLevel4 delegatorClass;
    private Socket serverSocket;
    private PrintWriter out;
    private ObjectInputStream in;
    private Thread receiveDataFromServerThread;

    @Override
    public void initiate( GameLevel4 delegatorClass ) {
        this.delegatorClass = delegatorClass;
        try {
            this.serverSocket = new Socket(ApplicationConstants.GameServerIP, ApplicationConstants.GameServerPort);
            this.out = new PrintWriter(new DataOutputStream(serverSocket.getOutputStream()));
            this.in = new ObjectInputStream(serverSocket.getInputStream());
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
        while (!terminate)
        {
            try {
                GameLevel4 receivedGameLevel = (GameLevel4) in.readObject();
                delegatorClass.interpolateReceivedVersion(receivedGameLevel);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void send_data() {
        JSONObject userShip = new JSONObject();
        userShip.put("x", delegatorClass.getUserShip().getPositionX());
        userShip.put("y", delegatorClass.getUserShip().getPositionY());
        userShip.put("shooted", delegatorClass.isShooted());
        out.println(userShip.toString());
        out.flush();
    }

}
