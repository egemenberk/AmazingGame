package com.ceng453.Server;

public class SinglePlayerGameInstance implements GameInstance {
    private String assignedUserToken;

    public SinglePlayerGameInstance(String tokenOfUser) {
        assignedUserToken = tokenOfUser;
    }

    @Override
    public void run() { // Game's main loop

    }
}
