package main.com.ceng453.game_objects;

import main.com.ceng453.ApplicationConstants;

import java.util.LinkedList;
import java.util.Objects;

/*
 * Alienship factory, to generate aliens in different levels
 */
public class AlienShipFactory {

    // Constant alien types that can be created
    public static final int EasyEnemyShip = 0;
    public static final int MediumEnemyShip = 1;
    public static final int HardEnemyShip = 2;

    private AlienShipFactory(){} // Factory pattern

    /*
     * This method generates alien ships, with 'alienCountInRow' in each of 'rowCount'
     * First row,column will be positioned in OffsetX and OffsetY
     * There will be StepX and StepY difference in the each row and column respectively
     */
    public static LinkedList<GameObject> populateEnemyShips(int shipType, int alienCountInRow, int rowCount, int OffsetX, int StepX, int OffsetY, int StepY){
        // We will be adding each initialized ship to that list to return
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

            Objects.requireNonNull(alienShip).setPosition(OffsetX + StepX*(i%alienCountInRow),OffsetY+StepY*(i/alienCountInRow));

            createdShips.add(alienShip);
        }
        return createdShips;
    }

}
