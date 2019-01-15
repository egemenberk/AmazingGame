package main.com.ceng453.client.gamelevels;

import main.com.ceng453.game.objects.AlienShipFactory;

public class GameLevel2 extends AbstractGameLevel {

    public GameLevel2() {
        generateAliens();
    }

    public void generateAliens() {
        //Creating an image
        int OffsetX = 50, OffsetY = 200;
        int StepX = 60, StepY = 100;
        int alienCountInRow = 9;
        int rowCount = 2;

        // Adding 2 rows of Easy Enemy Ships
        alienShips.addAll(AlienShipFactory.populateEnemyShips(AlienShipFactory.EasyEnemyShip,
                alienCountInRow, rowCount, OffsetX, StepX, OffsetY, StepY));

        alienCountInRow = 5;
        rowCount = 1;
        StepX = 100;
        OffsetY = 20;
        OffsetX = -20;

        // Adding one row of MediumEnemyShips of the game level
        alienShips.addAll(AlienShipFactory.populateEnemyShips(AlienShipFactory.MediumEnemyShip,
                alienCountInRow, rowCount, OffsetX, StepX, OffsetY, StepY));
    }
}
