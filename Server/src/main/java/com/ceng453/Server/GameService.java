package com.ceng453.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameService {
    private Map<String, GameInstance> gameInstances;
    private ArrayList<Thread> gameThreads;


    public void createNewInstance(String tokenOfUser) {
        gameInstances = new HashMap<>();
        gameThreads = new ArrayList<>();
        gameInstances.put(tokenOfUser, new SinglePlayerGameInstance( tokenOfUser ));
        gameThreads.add( new Thread(gameInstances.get(tokenOfUser)) );

    }
}
