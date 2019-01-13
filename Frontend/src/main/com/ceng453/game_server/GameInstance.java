package main.com.ceng453.game_server;

import main.com.ceng453.frontend.gamelevels.GameLevel4;
import main.com.ceng453.frontend.gamelevels.GameStateInfo;
import java.util.ArrayList;
import java.util.List;

public class GameInstance extends Thread{
    List<GameClient> clientsInThatGame;
    GameLevel4 gameLevel4;
    private final GameStateInfo gameStateInfo = new GameStateInfo(System.nanoTime()); // GameStateInfo, described in details in its class

    public GameInstance(GameClient c1, GameClient c2) {
        clientsInThatGame = new ArrayList<>();
        clientsInThatGame.add(c1);
        clientsInThatGame.add(c2);
        this.gameLevel4 = new GameLevel4(new ServerCommunicationHandler(clientsInThatGame));
    }

    @Override
    public void run() {
        super.run();
        (new ServeClient(clientsInThatGame.get(0))).start();
        (new ServeClient(clientsInThatGame.get(1))).start();
        //TODO Server Game Logic
    }

    public class ServeClient extends Thread{

        private GameClient gc;

        public ServeClient(GameClient gc) {
            this.gc = gc;
        }

        @Override
        public void run() {
        gameStateInfo.setPreviousLoopTime(System.nanoTime()); // Calibrate initial game start time
        while(true) {
            try {
                sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gameStateInfo.setElapsedTime(0.015);
            gameStateInfo.incrementCurrentCycleCount(); // increase cycle counter
            gameLevel4.gameLoop(gameStateInfo, null); // This call will generate a new frame of the game
        }

    }

}
