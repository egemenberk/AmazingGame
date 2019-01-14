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
        ServerCommunicationHelper sch = new ServerCommunicationHelper();
        long serverGeneratedTicks = 0;
        while(true) {
            try {
                JSONObject tickInformation = new JSONObject().put("tick",serverGeneratedTicks);
                sch.send_data(tickInformation, 0);
                sch.send_data(tickInformation, 1);
                sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            serverGeneratedTicks++;
        }
    }

    private class ServerCommunicationHelper{

        public ServerCommunicationHelper() {
            new ServeClient(clientsInThatGame.get(0), 0).start();
            new ServeClient(clientsInThatGame.get(1), 1).start();
        }

        public synchronized void send_data(JSONObject data, int to_id)
        {
            clientsInThatGame.get(to_id).out.println(data);
            clientsInThatGame.get(to_id).out.flush();
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

                    if(id == 0)
                        send_data(receivedInfo,1);
                    else
                        send_data(receivedInfo, 0);
                }
            }
        }
    }

}
