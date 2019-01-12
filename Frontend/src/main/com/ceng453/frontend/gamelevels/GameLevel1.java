package main.com.ceng453.frontend.gamelevels;

import javafx.scene.canvas.GraphicsContext;
import main.com.ceng453.game_objects.AlienShipFactory;
import main.com.ceng453.frontend.main.ApplicationConstants;

public class GameLevel1 extends GameLevel{

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

    protected void drawBackground(GraphicsContext gc) {
        gc.drawImage(ApplicationConstants.BackGroundImage, 0,0, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
    }
}
