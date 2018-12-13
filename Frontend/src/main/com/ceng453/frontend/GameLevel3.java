package main.com.ceng453.frontend;

import javafx.scene.canvas.GraphicsContext;

public class GameLevel3 extends GameLevel{

    public GameLevel3() {
        generateAliens();
        generateUserShip();
    }

    public void generateAliens() {
        //Creating an image
        int OffsetX = 80, OffsetY = 200;
        int StepX = 60, StepY = 100;
        int alienCountInRow = 9;
        int rowCount = 2;

        for( int i=0; i<alienCountInRow*rowCount; i++ )
        {
            EasyEnemyShip alienShip = new EasyEnemyShip(ApplicationConstants.EasyAlienShipImage, 45,70);
            alienShip.initialize(1,1);
            alienShip.setPosition(OffsetX + StepX*(i%alienCountInRow),OffsetY+StepY*(i/alienCountInRow));

            alienShips.add(alienShip);
        }

        OffsetY = 100;
        OffsetX = 100;

        HardEnemyShip alienShip = new HardEnemyShip(ApplicationConstants.HardAlienShipImage, 75,95);
        alienShip.initialize(6,3);
        alienShip.setPosition(OffsetX,OffsetY);
        alienShips.add(alienShip);
    }

    public void generateUserShip() {
        int userShipWidth = 100, userShipHeight = 100;
        userShip = new UserShip(ApplicationConstants.UserShipImage, userShipWidth,userShipHeight);
        userShip.initialize(4,2);
        userShip.setPosition(200,2*ApplicationConstants.ScreenHeight/3.0 );
    }

    @Override
    protected void drawBackground(GraphicsContext gc) {
        gc.drawImage(ApplicationConstants.BackGroundImage, 0,0, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
    }
}
