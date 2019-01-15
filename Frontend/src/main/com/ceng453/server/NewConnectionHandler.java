package main.com.ceng453.server;

import main.com.ceng453.ApplicationConstants;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
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
            activeGames = new ArrayList<>();
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
                cleanUnactiveGames();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void cleanUnactiveGames() { // Just remove end instances time to time
        for( int i=0; i<activeGames.size(); i++ )
            if( activeGames.get(i).has_end() )
                activeGames.remove(i--);
    }

    private void handleMatchMaking() {
        while( matchmakingQueue.size() > 1 )
        {
            System.out.println("Making match xd");
            GameClient c1 = matchmakingQueue.poll();
            GameClient c2 = matchmakingQueue.poll();

            GameInstance newGame = new GameInstance(c1, c2);
            activeGames.add(newGame);
            newGame.start();
        }
    }
}
