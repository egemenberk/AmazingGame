package com.ceng453.frontend;

import java.util.HashMap;
import java.util.Map;

class GameService {
    private final Map<String, GameInstance> gameInstances;


    public GameService() {
        this.gameInstances = new HashMap<>();
    }

    public void CreateNewInstance(String tokenOfUser) {
        gameInstances.put(tokenOfUser, new SinglePlayerGameInstance( tokenOfUser ));
        gameInstances.get(tokenOfUser).StartGame();
    }
}
