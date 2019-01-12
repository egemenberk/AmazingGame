package main.com.ceng453.frontend.gamelevels;

import javafx.scene.canvas.GraphicsContext;
import main.com.ceng453.game_objects.AlienShipFactory;
import main.com.ceng453.ApplicationConstants;

public class GameLevel3 extends GameLevel{

    public GameLevel3() {
        generateAliens();
    }

    public void generateAliens() {
        //Creating an image
        int OffsetX = 50, OffsetY = 200;
        int StepX = 240, StepY = 100;
        int alienCountInRow = 3;
        int rowCount = 2;

        // Adding easy enemies with large step value
        alienShips.addAll(AlienShipFactory.populateEnemyShips(AlienShipFactory.EasyEnemyShip,
                alienCountInRow, rowCount, OffsetX, StepX, OffsetY, StepY));

        OffsetX = 80;
        StepX = 240;
        OffsetY = 200;
        rowCount = 1;
        alienCountInRow = 2;

        // Then, filling the spaces with Medium Enemies
        alienShips.addAll(AlienShipFactory.populateEnemyShips(AlienShipFactory.MediumEnemyShip,
                alienCountInRow, rowCount, OffsetX, StepX, OffsetY, StepY));


        OffsetX = ApplicationConstants.ScreenWidth/2 + 250;
        OffsetY = 50;
        rowCount = 1;
        alienCountInRow = 1;

        // Finally, spawn a Hard Enemy to the top
        alienShips.addAll(AlienShipFactory.populateEnemyShips(AlienShipFactory.HardEnemyShip,
                alienCountInRow, rowCount, OffsetX, StepX, OffsetY, StepY));
    }

    @Override
    protected void drawBackground(GraphicsContext gc) {
        gc.drawImage(ApplicationConstants.BackGroundImage, 0,0, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
    }
}
