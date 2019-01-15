package main.com.ceng453.game.objects;

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
    public static final int BossEnemyShip = 3;

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
                    alienShip = new EasyEnemyShip(ApplicationConstants.EASY_ALIEN_SHIP_IMAGE, 45, 70);
                    alienShip.setHitpointsAndDamage(ApplicationConstants.EASY_ALIEN_SHIP_HP,ApplicationConstants.EASY_ALIEN_SHIP_DMG);
                    break;
                case AlienShipFactory.MediumEnemyShip:
                    alienShip = new MediumEnemyShip(ApplicationConstants.MEDIUM_ALIEN_SHIP_IMAGE, 55,85);
                    alienShip.setHitpointsAndDamage(ApplicationConstants.MEDIUM_ALIEN_SHIP_HP,ApplicationConstants.MEDIUM_ALIEN_SHIP_DMG);
                    break;
                case AlienShipFactory.HardEnemyShip:
                    alienShip = new HardEnemyShip(ApplicationConstants.HARD_ALIEN_SHIP_IMAGE, 90,115);
                    alienShip.setHitpointsAndDamage(ApplicationConstants.HARD_ALIEN_SHIP_HP,ApplicationConstants.HARD_ALIEN_SHIP_DMG);
                    break;
                case AlienShipFactory.BossEnemyShip:
                    alienShip = new BossAlien(ApplicationConstants.BOSS_ALIEN_SHIP_IMAGE, ApplicationConstants.BOSS_ALIEN_SHIP_WIDTH,ApplicationConstants.BOSS_ALIEN_SHIP_HEIGHT);
                    alienShip.setHitpointsAndDamage(ApplicationConstants.BOSS_ALIEN_SHIP_HP,ApplicationConstants.HARD_ALIEN_SHIP_DMG);

            }

            Objects.requireNonNull(alienShip).setPosition(OffsetX + StepX*(i%alienCountInRow),OffsetY+StepY*(i/alienCountInRow));

            createdShips.add(alienShip);
        }
        return createdShips;
    }

}
