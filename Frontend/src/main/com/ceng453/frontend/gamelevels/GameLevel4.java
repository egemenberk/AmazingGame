package main.com.ceng453.frontend.gamelevels;

import javafx.scene.canvas.GraphicsContext;
import main.com.ceng453.ApplicationConstants;
import main.com.ceng453.MultiplayerCommunicationHandler;
import main.com.ceng453.frontend.main.StaticHelpers;
import main.com.ceng453.game_objects.AlienShipFactory;
import main.com.ceng453.game_objects.GameObject;
import main.com.ceng453.game_objects.UserShip;
import java.io.Serializable;
import java.util.ArrayList;

public class GameLevel4 extends AbstractGameLevel implements Serializable {

    private UserShip rivalShip;
    private ArrayList<GameObject> rivalBullets;
    private MultiplayerCommunicationHandler multiplayerCommunucationHander;


    public GameLevel4(MultiplayerCommunicationHandler communicationHandler) {
        rivalBullets = new ArrayList<>();
        generateAliens();
        generateRivalShip();
        multiplayerCommunucationHander = communicationHandler;
        multiplayerCommunucationHander.initiate(this);
    }

    // We will construct the user ship in this method
    private void generateRivalShip()
    {
        int userShipWidth = 100, userShipHeight = 100;
        rivalShip = new UserShip(ApplicationConstants.UserShipImage, userShipWidth,userShipHeight); // Create UserShip obj.
        rivalShip.setHitpointsAndDamage(ApplicationConstants.UserShipHealth, ApplicationConstants.UserShipDamage); // Set its health and damage
        rivalShip.setPosition(ApplicationConstants.ScreenWidth/2.0-userShipWidth/2,
                2*ApplicationConstants.ScreenHeight/3.0+userShipHeight ); // Initial positioning of the user ship

        // User ship calculates its velocity depending on the mouse position
        // For initial values, we need to set the mouse position to the current
        // position of the user ship, since there is no read mouse positions yet
        rivalShip.setFlyingPositionX(rivalShip.getPositionX());
        rivalShip.setFlyingPositionY(rivalShip.getPositionY());
    }

    public void generateAliens() {
        //Creating an image
        int OffsetX = ApplicationConstants.ScreenWidth/2 + 250, OffsetY = ApplicationConstants.ScreenHeight/2;
        int StepX = 240, StepY = 100;
        int alienCountInRow = 1;
        int rowCount = 1;

        // Spawn an Hard Enemy to the Middle
        alienShips.addAll(AlienShipFactory.populateEnemyShips(AlienShipFactory.HardEnemyShip,
                alienCountInRow, rowCount, OffsetX, StepX, OffsetY, StepY));
    }

    @Override
    public void gameLoop(GameStateInfo gameStateInfo, GraphicsContext gc) {
        super.gameLoop(gameStateInfo, gc);
        multiplayerCommunucationHander.send_data();
        setShooted(0);
    }

    @Override
    protected void drawBackground(GraphicsContext gc) {
        gc.drawImage(ApplicationConstants.BackGroundImage, 0,0, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
    }

    @Override
    protected void drawObjects(GraphicsContext gc){
        super.drawObjects(gc);
        rivalShip.render(gc);
    }

    @Override
    protected void update(GameStateInfo gameStateInfo) {
        if(rivalShip.getHitPointsLeft() <= 0) // In this case, user loses. Mark the state variable
        {
            isOver = true;
            return;
        }
        if( rivalShip.isCleared() || alienShips.size() == 0) { // In this case, user completes the current level
            levelPassed = true; // Mark the state variable accordingly
            return;
        }
        super.update(gameStateInfo);

        double elapsedTime = gameStateInfo.getElapsedTime();
        long cycleCount = gameStateInfo.getCurrentCycleCounter();

        rivalShip.update(elapsedTime, cycleCount);
    }

    @Override
    protected void collision_detection() {
        super.collision_detection();
        // Multi-player related collision checks lies here
        // Checking if any user bullet collides with any alien bullet
        for (GameObject bullet : rivalBullets) {
            if (!bullet.isCleared()) {
                for (GameObject alienShip : alienShips)
                    if (StaticHelpers.intersects(alienShip, bullet)) { // If there is an intersection
                        int b = 0; // TODO delete and resolve duplicate
                        GameObject effect = alienShip.hitBy(bullet); // Apply the hit effects
                        if (effect != null) // If an Game Effect is returned( Explosion effect etc. )
                            effects.add(effect); // Track that Effect object
                    }
            }
        }

        checkIntersectionForTwoPlayers(rivalBullets, userShip);
        checkIntersectionForTwoPlayers(userBullets, rivalShip);
    }

    private void checkIntersectionForTwoPlayers(ArrayList<GameObject> userBullets, UserShip rivalShip) {
        for (GameObject bullet : userBullets) {
            if (!bullet.isCleared() && StaticHelpers.intersects(rivalShip, bullet)) {
                int b = 0; // TODO delete and resolve duplicate
                GameObject effect = rivalShip.hitBy(bullet); // Apply the hit effects
                if (effect != null) // If an Game Effect is returned( Explosion effect etc. )
                    effects.add(effect); // Track that Effect object
            }
        }
    }

    public UserShip getRivalShip() {
        return rivalShip;
    }

    public void setRivalShip(UserShip rivalShip) {
        this.rivalShip = rivalShip;
    }

    public void interpolateReceivedVersion( GameLevel4 receivedVersion )
    {
        userShip = receivedVersion.userShip;
        alienShips = receivedVersion.alienShips;
        userBullets = receivedVersion.userBullets;
        alienBullets = receivedVersion.alienBullets;
        effects = receivedVersion.effects;
        rivalShip = receivedVersion.rivalShip;
        rivalBullets = receivedVersion.rivalBullets;
    }
}







