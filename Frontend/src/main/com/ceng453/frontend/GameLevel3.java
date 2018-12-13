package main.com.ceng453.frontend;

import javafx.scene.canvas.GraphicsContext;

public class GameLevel3 extends GameLevel{

    public GameLevel3() {
        generateAliens();
        generateUserShip();
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
        alienShip.initialize(12,3);
        alienShip.setPosition(ApplicationConstants.ScreenWidth/2+250,50);
        alienShips.add(alienShip);
    }

    public void generateUserShip() {
        int userShipWidth = 100, userShipHeight = 100;
        userShip = new UserShip(ApplicationConstants.UserShipImage, userShipWidth,userShipHeight);
        userShip.initialize(6,2);
        userShip.setPosition(200,2*ApplicationConstants.ScreenHeight/3.0 );
    }

    @Override
    protected void drawBackground(GraphicsContext gc) {
        gc.drawImage(ApplicationConstants.BackGroundImage, 0,0, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
    }
}
