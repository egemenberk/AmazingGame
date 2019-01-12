package main.com.ceng453.game_server;

import java.util.ArrayList;
import java.util.List;

public class GameInstance extends Thread{
    List<GameClient> clientsInThatGame;

    public GameInstance(GameClient c1, GameClient c2) {
        clientsInThatGame = new ArrayList<>();
        clientsInThatGame.add(c1);
        clientsInThatGame.add(c2);
    }

    @Override
    public void run() {
        super.run();
        //TODO Server Game Logic
    }
}
