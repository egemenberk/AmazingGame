package main.com.ceng453.frontend.gameobjects;

import main.com.ceng453.frontend.main.ApplicationConstants;

import java.util.LinkedList;

public class AlienShipFactory {

    public static final int EasyEnemyShip = 0;
    public static final int MediumEnemyShip = 1;
    public static final int HardEnemyShip = 2;

    public static LinkedList<GameObject> populateEnemyShips(int shipType, int alienCountInRow, int rowCount, int OffsetX, int StepX, int OffsetY, int StepY){
        LinkedList<GameObject> createdShips = new LinkedList<>();
        for( int i=0; i<alienCountInRow*rowCount; i++ )
        {
            GameObject alienShip = null;
            switch(shipType) {
                case AlienShipFactory.EasyEnemyShip:
                    alienShip = new EasyEnemyShip(ApplicationConstants.EasyAlienShipImage, 45, 70);
                    alienShip.setHitpointsAndDamage(ApplicationConstants.EasyAlienShipHP,ApplicationConstants.EasyAlienShipDMG);
                    break;
                case AlienShipFactory.MediumEnemyShip:
                    alienShip = new MediumEnemyShip(ApplicationConstants.MediumAlienShipImage, 55,85);
                    alienShip.setHitpointsAndDamage(ApplicationConstants.MediumAlienShipHP,ApplicationConstants.MediumAlienShipDMG);
                    break;
                case AlienShipFactory.HardEnemyShip:
                    alienShip = new HardEnemyShip(ApplicationConstants.HardAlienShipImage, 90,115);
                    alienShip.setHitpointsAndDamage(ApplicationConstants.HardAlienShipHP,ApplicationConstants.HardAlienShipDMG);
                    break;
            }

            alienShip.setPosition(OffsetX + StepX*(i%alienCountInRow),OffsetY+StepY*(i/alienCountInRow));

            createdShips.add(alienShip);
        }
        return createdShips;
    }

}
