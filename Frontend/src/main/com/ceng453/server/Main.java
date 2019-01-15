package main.com.ceng453.server;

public class Main{

    public static void main(String[] args) throws InterruptedException {
        Thread server = new NewConnectionHandler();
        server.start();
        server.join();
    }
}

