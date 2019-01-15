package main.com.ceng453.game_server;

import main.com.ceng453.ApplicationConstants;

import java.io.*;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class NewConnectionHandler extends Thread {

    private ServerSocket connectionHandlerSocket;
    private List<GameInstance> activeGames;
    private Queue<GameClient> matchmakingQueue;

    public NewConnectionHandler()
    {
        try {
            connectionHandlerSocket = new ServerSocket(ApplicationConstants.GAME_SERVER_PORT); // TODO no binding ok?
            activeGames = new LinkedList<>();
            matchmakingQueue = new LinkedBlockingQueue<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        try {
            while (true) {
                System.out.println("System is ready to receive connection...");
                GameClient newClient = new GameClient(connectionHandlerSocket.accept());
                matchmakingQueue.add( newClient );
                System.out.println("Someone has connected... Queue size is " + matchmakingQueue.size());
                handleMatchMaking();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void handleMatchMaking() {
        while( matchmakingQueue.size() > 1 )
        {
            System.out.println("Making match xd");
            GameClient c1 = matchmakingQueue.poll();
            GameClient c2 = matchmakingQueue.poll();

            GameInstance newGame = new GameInstance(c1, c2);
            activeGames.add(newGame);
            c1.out.println("start");
            c1.out.flush();
            c2.out.println("start");
            c2.out.flush();
            newGame.start();

        }
    }
}
