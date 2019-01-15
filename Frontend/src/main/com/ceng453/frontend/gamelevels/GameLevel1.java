package main.com.ceng453.frontend.gamelevels;

import main.com.ceng453.game.objects.AlienShipFactory;

public class GameLevel1 extends AbstractGameLevel {

    public GameLevel1() {
        generateAliens();
    }

    public void generateAliens() {
        //Creating an image
        int OffsetX = 50, OffsetY = 30;
        int StepX = 60, StepY = 100;
        int alienCountInRow = 9;
        int rowCount = 3;

        alienShips.addAll(AlienShipFactory.populateEnemyShips(AlienShipFactory.EasyEnemyShip,
                alienCountInRow, rowCount, OffsetX, StepX, OffsetY, StepY));
    }
}
