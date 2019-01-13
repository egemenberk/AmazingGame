package main.com.ceng453;

import main.com.ceng453.frontend.gamelevels.GameLevel4;

import java.io.IOException;

public abstract class MultiplayerCommunicationHandler{

    protected boolean terminate = false;

    public abstract void initiate( GameLevel4 delegatorClass );

    public abstract void send_data() throws IOException;
}
