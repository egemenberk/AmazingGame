package main.com.ceng453.game_server;

import main.com.ceng453.ApplicationConstants;
import main.com.ceng453.MultiplayerCommunicationHandler;
import main.com.ceng453.frontend.gamelevels.GameLevel4;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientCommunicationHandler extends MultiplayerCommunicationHandler {

    private GameLevel4 delegatorClass;
    private Socket clientSocket;
    private PrintWriter out;
    private ObjectOutputStream in;

    @Override
    public void initiate( GameLevel4 delegatorClass ) {
        this.delegatorClass = delegatorClass;
        try {
            this.clientSocket = new Socket(ApplicationConstants.GameServerIP, ApplicationConstants.GameServerPort);
            this.out = new PrintWriter(new DataOutputStream(clientSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
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

    @Override
    public void receive_data() {
        GameLevel4 receivedGameLevel = null;
        delegatorClass.interpolateReceivedVersion(receivedGameLevel);
    }
}
