package main.com.ceng453.game_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClient {
    Socket clientSocket;
    BufferedReader streamFromClient;
    PrintWriter streamToClient;

    public GameClient(Socket socket) throws IOException {
        this.clientSocket = socket;
        this.streamFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.streamToClient = new PrintWriter(clientSocket.getOutputStream(),true);
    }
}
