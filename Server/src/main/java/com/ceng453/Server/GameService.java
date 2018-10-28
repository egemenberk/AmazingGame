package com.ceng453.Server;

import java.util.HashMap;
import java.util.Map;

public class GameService {
    private Map<String, GameInstance> gameInstances;


    public GameService() {
        this.gameInstances = new HashMap<>();
    }

    public void CreateNewInstance(String tokenOfUser) {
        gameInstances.put(tokenOfUser, new SinglePlayerGameInstance( tokenOfUser ));
        gameInstances.get(tokenOfUser).StartGame();
    }
}
