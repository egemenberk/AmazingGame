package main.com.ceng453.game_server;

import main.com.ceng453.MultiplayerCommunicationHandler;
import main.com.ceng453.frontend.gamelevels.GameLevel4;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class ServerCommunicationHandler extends MultiplayerCommunicationHandler {

    protected GameLevel4 delegatorClass;
    private List<GameClient>  clientsInThatGame;


    public ServerCommunicationHandler(List<GameClient> clientsInThatGame) {
        this.clientsInThatGame = clientsInThatGame;
        (new ServeClient(clientsInThatGame.get(0), 0)).start();
        (new ServeClient(clientsInThatGame.get(1), 1)).start();
    }

    @Override
    public void initiate(GameLevel4 delegatorClass) {
        this.delegatorClass = delegatorClass;
    }

    @Override
    public void send_data() {
        clientsInThatGame.get(0).out.println(delegatorClass);
        clientsInThatGame.get(0).out.flush();
        clientsInThatGame.get(1).out.println(delegatorClass);
        clientsInThatGame.get(1).out.flush();
    }

    public class ServeClient extends Thread {

        private GameClient gc;
        private int id;

        public ServeClient(GameClient gc, int id) {
            this.gc = gc;
            this.id = id;
        }

        @Override
        public void run() {
            super.run();
            String input = "";

            while (true) {
                try {
                    if((input = gc.in.readLine()) == null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONObject receivedInfo = new JSONObject(input);

                if(id == 0) {
                    delegatorClass.getUserShip().setPosition((double)receivedInfo.get("x"), (double) receivedInfo.get("y"));
                    if(receivedInfo.get("shooted") == "1")
                        delegatorClass.getUserShip().shoot();
                }
                else {
                    delegatorClass.getRivalShip().setPosition((double)receivedInfo.get("x"), (double) receivedInfo.get("y"));
                    if(receivedInfo.get("shooted") == "1")
                        delegatorClass.getRivalShip().shoot();

                }
            }
        }
    }
}
