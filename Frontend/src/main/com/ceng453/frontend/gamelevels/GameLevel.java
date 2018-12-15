package main.com.ceng453.frontend.gamelevels;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import main.com.ceng453.frontend.gameobjects.EasyEnemyShip;
import main.com.ceng453.frontend.main.ApplicationConstants;
import main.com.ceng453.frontend.gameobjects.GameObject;
import main.com.ceng453.frontend.main.StaticHelpers;
import main.com.ceng453.frontend.gameobjects.UserShip;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/*
 *  Abstract game level class.
 *  All of the game level functionality lies here,
 *  Only the initialization functions are defined in the child classes.
 *  This allows easy extendability to our game style by changing only the objects inside the level
 */
public abstract class GameLevel {

    // Current GameObjects in the game level
    protected ArrayList<GameObject> effects;
    protected ArrayList<GameObject> alienShips;
    protected ArrayList<GameObject> userBullets;
    protected ArrayList<GameObject> alienBullets;
    protected UserShip userShip;

    protected boolean isOver; // Indicator for GameOver state
    protected boolean levelPassed; // Indicator for successful level end state

    // Event handler classes to capture user input
    MouseMoveEventHandler customizedMouseMoveEventHandler;
    MouseClickEventHandler customizedMouseClickEventHandler;

    // Constructor of the abstract class.
    // Note that alien construction does not done in this class
    public GameLevel() {
        alienShips = new ArrayList<>();
        userBullets = new ArrayList<>();
        effects = new ArrayList<>();
        alienBullets = new ArrayList<>();

        customizedMouseClickEventHandler = new MouseClickEventHandler( this );
        customizedMouseMoveEventHandler = new MouseMoveEventHandler(this);
        generateUserShip();
    }

    // Alien construction is left to the extending class to allow customization between levels
    public abstract void generateAliens() throws FileNotFoundException;

    // We will construct the user ship in this method
    public void generateUserShip()
    {
        int userShipWidth = 100, userShipHeight = 100;
        userShip = new UserShip(ApplicationConstants.UserShipImage, userShipWidth,userShipHeight); // Create UserShip obj.
        userShip.setHitpointsAndDamage(ApplicationConstants.UserShipHealth, ApplicationConstants.UserShipDamage); // Set its health and damage
        userShip.setPosition(ApplicationConstants.ScreenWidth/2.0-userShipWidth/2,
                2*ApplicationConstants.ScreenHeight/3.0+userShipHeight ); // Initial positioning of the user ship

        // User ship calculates its velocity depending on the mouse position
        // For initial values, we need to set the mouse position to the current
        // position of the user ship, since there is no read mouse positions yet
        userShip.setFlyingPositionX(userShip.getPositionX());
        userShip.setFlyingPositionY(userShip.getPositionY());
    }

    // High level game pipeline. GameService class will be calling this method for each frame.
    public void gameLoop( GameStateInfo gameStateInfo, GraphicsContext gc ){
        // UPDATE OPERATIONS
        update(gameStateInfo); // Update the state of the game
        collision_detection(); // Collision detection and related updates( e.g EnemyShip gets damage from user bullet in case of collision )
        // Draw OPERATIONS
        drawBackground(gc);
        drawObjects(gc);
        drawTexts(gc,gameStateInfo.getCurrentGameScore());
    }

    // Score & Healt indicator drawing
    protected void drawTexts(GraphicsContext gc, int currentScore){
        double ScoreXOffset = 30;
        double HealthStatusXOffset = 300;
        gc.strokeText(String.format("Current Score : %d", currentScore),ScoreXOffset, ApplicationConstants.TextDrawRectHeight);
        gc.strokeText(String.format("Health : %d/%d", userShip.getHitPointsLeft(),ApplicationConstants.UserShipHealth),HealthStatusXOffset,ApplicationConstants.TextDrawRectHeight);
    }

    // Drawing of the Background image will be implemented by the child methods
    protected abstract void drawBackground(GraphicsContext gc);

    // Drawing game objects
    private void drawObjects(GraphicsContext gc) {
        userShip.render(gc);
        for( GameObject alien : alienShips )
            alien.render(gc);
        for( GameObject bullet : userBullets)
            bullet.render(gc);
        for( GameObject bullet : alienBullets)
            bullet.render(gc);
        for( GameObject effect : effects )
            effect.render(gc);
    }

    // Method for collision check between the objetcs
    private void collision_detection() {

        // Checking if any user bullet collides with any alien bullet
        for( GameObject userBullet: userBullets) {
            for (GameObject  alienBullet: alienBullets)
                if (StaticHelpers.intersects(userBullet, alienBullet)) { // If there is an intersection
                    GameObject effect = alienBullet.hitBy(userBullet); // Apply the effects
                    if( effect != null ) // If an Game Effect is returned( Explosion effect etc. )
                        effects.add(effect); // Track that Effect object
                }
        }

        // Checking if any user bullet collides with any alien bullet
        for( GameObject bullet: userBullets) {
            for (GameObject alienShip : alienShips)
                if ( !bullet.isCleared() &&  StaticHelpers.intersects(alienShip, bullet)) { // If there is an intersection
                    GameObject effect = alienShip.hitBy(bullet); // Apply the hit effects
                    if( effect != null ) // If an Game Effect is returned( Explosion effect etc. )
                        effects.add(effect); // Track that Effect object
                }
        }
        // Checking if user ship collides with any alien bullet
        for( GameObject bullet: alienBullets) {
            if ( !bullet.isCleared() && StaticHelpers.intersects(userShip, bullet)) { // If there is an intersection
                GameObject effect = userShip.hitBy(bullet); // Apply the hit effects
                if (effect != null) // If an Game Effect is returned( Explosion effect etc. )
                    effects.add(effect); // Track that Effect object
            }

        }
    }

    // Update method of GameLevel calls individual update methods of all GameObjects currently in the game
    private void update( GameStateInfo gameStateInfo ) {
        double elapsedTime = gameStateInfo.getElapsedTime();
        long cycleCount = gameStateInfo.getCurrentCycleCounter();

        if(userShip.getHitPointsLeft() <= 0) // In this case, user loses. Mark the state variable
        {
            isOver = true;
            return;
        }
        if( userShip.isCleared() || alienShips.size() == 0) { // In this case, user completes the current level
            levelPassed = true; // Mark the state variable accordingly
            return;
        }

        // Calling individual update methods of GameObjects
        userShip.update(elapsedTime, cycleCount);

        updateHelperForPossibleNewItems(gameStateInfo, elapsedTime, cycleCount, alienShips);
        updateHelperForPossibleNewItems(gameStateInfo, elapsedTime, cycleCount, alienBullets);

        updateHelper(elapsedTime, cycleCount, userBullets);
        updateHelper(elapsedTime, cycleCount, alienBullets);
        updateHelper(elapsedTime, cycleCount, effects);
    }

    /*
     * This method both
     *  - Calls the update method of gameObjects
     *  - Catches the newly created objects during their update
     *  ( e.g ) AlienShips might shoot and create bullets
     */
    private void updateHelperForPossibleNewItems(GameStateInfo gameStateInfo, double elapsedTime, long cycleCount, ArrayList<GameObject> objects) {
        for(int i = 0; i< objects.size(); i++ ) // For each object
        {
            GameObject object = objects.get(i);
            if( object.isCleared() ) { // If that object has destroyed
                objects.remove(i--); // Remove from list
                gameStateInfo.incrementScoreBy(object.getBounty()); // And add its score to user
            }
            else { // Call update method of the object, and catch possible newBullet
                GameObject possibleNewBullet = object.update(elapsedTime, cycleCount);
                if (possibleNewBullet != null) {
                    alienBullets.add(possibleNewBullet);
                }
            }
        }
    }

    // Calling update of each object in objects
    private void updateHelper(double elapsedTime, long cycleCount, ArrayList<GameObject> objects) {
        for(int i = 0; i< objects.size(); i++ )
        {
            GameObject object = objects.get(i);
            if( object.isCleared() ) {
                objects.remove(i--);
            }
            else
                object.update(elapsedTime,cycleCount);
        }
    }

    // Mouse handler classes
    private class MouseMoveEventHandler implements EventHandler<MouseEvent>{

        GameLevel superClass;

        public MouseMoveEventHandler( GameLevel superClass ) {
            this.superClass = superClass;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            superClass.MouseMoveEventHandle( mouseEvent );
        }
    }

    private class MouseClickEventHandler implements EventHandler<MouseEvent>{

        GameLevel superClass;

        public MouseClickEventHandler( GameLevel superClass ) {
            this.superClass = superClass;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            superClass.MouseClickedEventHandle( mouseEvent );
        }
    }

    // Mouse move handle method, it sets the flying point of the user ship
    // As well as not permitting the user ship to pass above the screen's top 2/5 part
    public void MouseMoveEventHandle( MouseEvent mouseEvent ){
        double lastMousePositionX = mouseEvent.getX();
        double lastMousePositionY = mouseEvent.getY() > 3*ApplicationConstants.ScreenHeight/5.0? mouseEvent.getY() : 3*ApplicationConstants.ScreenHeight/5.0;
        userShip.setFlyingPositionX(lastMousePositionX);
        userShip.setFlyingPositionY(lastMousePositionY);
    }

    // Mouse click handle, user ship shoots whenever mouse clicks
    public void MouseClickedEventHandle( MouseEvent mouseEvent ){
        userBullets.add(userShip.shoot());
    }

    public boolean isOver() {
        return isOver;
    }

    public boolean levelPassed() { return levelPassed; }

    public MouseMoveEventHandler getCustomizedMouseMoveEventHandler() {
        return customizedMouseMoveEventHandler;
    }

    public MouseClickEventHandler getCustomizedMouseClickEventHandler() {
        return customizedMouseClickEventHandler;
    }
}