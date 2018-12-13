package main.com.ceng453.frontend;

import javafx.scene.canvas.GraphicsContext;

public class GameLevel1 extends GameLevel{


    public GameLevel1() {
        generateAliens();
        generateUserShip();
    }

    public void generateAliens() {
        //Creating an image
        int OffsetX = 50, OffsetY = 30;
        int StepX = 60, StepY = 100;
        int alienCountInRow = 9;
        int rowCount = 3;

        for( int i=0; i<alienCountInRow*rowCount; i++ )
        {
            EasyEnemyShip alienShip = new EasyEnemyShip(ApplicationConstants.EasyAlienShipImage, 45,70);
            alienShip.initialize(2,1);
            alienShip.setPosition(OffsetX + StepX*(i%alienCountInRow),OffsetY+StepY*(i/alienCountInRow));

            alienShips.add(alienShip);
        }
    }

    public void generateUserShip() {
        int userShipWidth = 100, userShipHeight = 100;
        userShip = new UserShip(ApplicationConstants.UserShipImage, userShipWidth,userShipHeight);
        userShip.initialize(ApplicationConstants.UserShipHealth,2);
        userShip.setPosition(200,2*ApplicationConstants.ScreenHeight/3.0 );
    }

    @Override
    protected void drawBackground(GraphicsContext gc) {
        gc.drawImage(ApplicationConstants.BackGroundImage, 0,0, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
    }
}
