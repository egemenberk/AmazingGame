package main.com.ceng453;

import main.com.ceng453.frontend.gamelevels.GameLevel4;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class MultiplayerCommunicationHandler extends Thread{

    public abstract void initiate( GameLevel4 delegatorClass );

    public abstract void send_data();

    public abstract void receive_data();

    @Override
    public void run() {
        super.run();

    }
}
