package main.com.ceng453.game_server;

import org.json.JSONObject;

import java.io.IOException;
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
            super.run();
            String input = "";
            String output;
            while (true) {
                try {
                    if ((input = gc.in.readLine()) == null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
                //JSONObject userShip = new JSONObject(input);
                System.out.println(input);
            }
        }
    }
}
