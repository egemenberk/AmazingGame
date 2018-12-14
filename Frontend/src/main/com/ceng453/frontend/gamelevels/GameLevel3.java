package main.com.ceng453.frontend.gamelevels;

import javafx.scene.canvas.GraphicsContext;
import main.com.ceng453.frontend.main.ApplicationConstants;
import main.com.ceng453.frontend.gameobjects.EasyEnemyShip;
import main.com.ceng453.frontend.gameobjects.HardEnemyShip;
import main.com.ceng453.frontend.gameobjects.MediumEnemyShip;

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

        for( int i=0; i<alienCountInRow*rowCount; i++ )
        {
            EasyEnemyShip alienShip = new EasyEnemyShip(ApplicationConstants.EasyAlienShipImage, 45,70);
            alienShip.initialize(2,1);
            alienShip.setPosition(OffsetX + StepX*(i%alienCountInRow),OffsetY+StepY*(i/alienCountInRow));

            alienShips.add(alienShip);
        }

        OffsetX = 80;
        StepX = 240;
        OffsetY = 200;
        rowCount = 1;
        alienCountInRow = 2;

        for( int i=0; i<alienCountInRow*rowCount; i++ )
        {
            MediumEnemyShip alienShip = new MediumEnemyShip(ApplicationConstants.MediumAlienShipImage, 55,85);
            alienShip.initialize(4,2);
            alienShip.setPosition(OffsetX + StepX*(i%alienCountInRow),OffsetY+StepY*(i/alienCountInRow));

            alienShips.add(alienShip);
        }

        HardEnemyShip alienShip = new HardEnemyShip(ApplicationConstants.HardAlienShipImage, 90,115);
        alienShip.initialize(16,3);
        alienShip.setPosition(ApplicationConstants.ScreenWidth/2+250,50);
        alienShips.add(alienShip);
    }

    @Override
    protected void drawBackground(GraphicsContext gc) {
        gc.drawImage(ApplicationConstants.BackGroundImage, 0,0, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
    }
}
