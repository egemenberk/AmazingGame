package main.com.ceng453.game.server;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameInstance extends Thread{
    private List<GameClient> clientsInThatGame;
    private boolean is_terminated = false;

    GameInstance(GameClient c1, GameClient c2) {
        clientsInThatGame = new ArrayList<>();
        clientsInThatGame.add(c1);
        clientsInThatGame.add(c2);
    }

    @Override
    public void run() {
        super.run();

        new ServeClient(clientsInThatGame.get(0), 0).start();
        new ServeClient(clientsInThatGame.get(1), 1).start();

        long serverGeneratedTicks = 0;
        while(!is_terminated) {
            try {
                JSONObject tickInformation = new JSONObject().put("tick",serverGeneratedTicks);
                send_data(tickInformation, 0);
                send_data(tickInformation, 1);
                sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            serverGeneratedTicks++;
        }
        for( GameClient gameClient : clientsInThatGame )
            gameClient.terminate();
    }

    private synchronized void send_data(JSONObject data, int to_id)
    {
        clientsInThatGame.get(to_id).out.println(data);
        clientsInThatGame.get(to_id).out.flush();
    }

    private class ServeClient extends Thread {

        private GameClient gc;
        private int id;

        ServeClient(GameClient gc, int id) {
            this.gc = gc;
            this.id = id;
        }

        @Override
        public void run() {
            super.run();
            String input = "";

            while (!is_terminated) {
                try {
                    if((input = gc.in.readLine()) == null) break;
                } catch (IOException e) {
                    is_terminated = true;
                    e.printStackTrace();
                }
                JSONObject receivedInfo = new JSONObject(input);

                if( receivedInfo.getInt("alien_hp") <= 0 ){ // We have a winner here
                    send_data(new JSONObject().put("winner", 1), id);
                    send_data(new JSONObject().put("winner", 0), id==0?1:0);
                    is_terminated = true; // Here, we guarantee that there will be only one winner
                }
                else {
                    if (id == 0) // Notify other client with taken info
                        send_data(receivedInfo, 1);
                    else
                        send_data(receivedInfo, 0);
                }
            }
        }
    }

}
