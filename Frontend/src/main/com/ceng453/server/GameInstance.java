package main.com.ceng453.server;

import main.com.ceng453.ApplicationConstants;
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
                JSONObject tickInformation = new JSONObject().put(ApplicationConstants.JSON_KEY_TICK,serverGeneratedTicks);
                send_data(tickInformation, 0);
                send_data(tickInformation, 1);
                sleep(ApplicationConstants.TICK_MS);
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

                System.out.println("From "+id+" -> "+receivedInfo.toString());

                if( receivedInfo.getBoolean(ApplicationConstants.JSON_KEY_HAS_RIVAL_WON) ) // We have a winner here
                    announceWinner( negeteId(id) );
                else if( receivedInfo.getInt(ApplicationConstants.JSON_KEY_USER_HP) <= 0 ) // This user has died
                    announceWinner( negeteId(id) );
                else {
                    if (id == 0) // Notify other main with taken info
                        send_data(receivedInfo, 1);
                    else
                        send_data(receivedInfo, 0);
                }
            }
        }

        private int negeteId( int id ) { return id == 0 ? 1 : 0; }
    }

    private synchronized void announceWinner(int id) {
        if( !is_terminated ){
            is_terminated = true;
            send_data(new JSONObject().put("winner", 1), id);
            send_data(new JSONObject().put("winner", 0), id==0?1:0);
        }
    }

    public boolean has_end() {
        return is_terminated;
    }

}
