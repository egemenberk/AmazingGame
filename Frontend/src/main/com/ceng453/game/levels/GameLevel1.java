package main.com.ceng453.game.levels;

import main.com.ceng453.game.objects.AlienShipFactory;

class GameLevel1 extends AbstractGameLevel {

    GameLevel1() {
        generateAliens();
    }

    private void generateAliens() {
        //Creating an image
        int OffsetX = 50, OffsetY = 30;
        int StepX = 60, StepY = 100;
        int alienCountInRow = 9;
        int rowCount = 3;

        alienShips.addAll(AlienShipFactory.populateEnemyShips(AlienShipFactory.EasyEnemyShip,
                alienCountInRow, rowCount, OffsetX, StepX, OffsetY, StepY));
    }
}
