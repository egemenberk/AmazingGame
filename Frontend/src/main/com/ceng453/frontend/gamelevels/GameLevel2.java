package main.com.ceng453.frontend.gamelevels;

import javafx.scene.canvas.GraphicsContext;
import main.com.ceng453.game_objects.AlienShipFactory;
import main.com.ceng453.ApplicationConstants;

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

    @Override
    protected void drawBackground(GraphicsContext gc) {
        gc.drawImage(ApplicationConstants.BackGroundImage, 0,0, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
    }
}
