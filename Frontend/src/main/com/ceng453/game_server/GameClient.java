package main.com.ceng453.game_server;

import java.io.*;
import java.net.Socket;

public class GameClient {
    public Socket clientSocket;
    public BufferedReader in;
    public ObjectOutputStream out;

    public GameClient(Socket socket) throws IOException {
        this.clientSocket = socket;
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
    }
}
