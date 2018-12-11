package com.ceng453.frontend;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.util.ArrayList;

abstract class GameLevel {

    protected ArrayList<GameObject> effects;
    protected ArrayList<GameObject> alienShips;
    protected ArrayList<GameObject> userBullets;
    protected ArrayList<GameObject> alienBullets;
    protected UserShip userShip;
    protected boolean isOver;

    MouseMoveEventHandler customizedMouseMoveEventHandler;
    MouseClickEventHandler customizedMouseClickEventHandler;

    public GameLevel() {
        alienShips = new ArrayList<>();
        userBullets = new ArrayList<>();
        effects = new ArrayList<>();
        alienBullets = new ArrayList<>();

        customizedMouseClickEventHandler = new MouseClickEventHandler( this );
        customizedMouseMoveEventHandler = new MouseMoveEventHandler(this);
    }

    public abstract void generateAliens() throws FileNotFoundException;

    public abstract void generateUserShip() throws FileNotFoundException;

    public void gameLoop( GameStateInfo gameStateInfo, GraphicsContext gc ){
        update(gameStateInfo);

        collision_detection();

        drawBackground(gc);
        
        drawObjects(gc);

        drawTexts(gc,gameStateInfo.getCurrentGameScore());
    }

    protected void drawTexts(GraphicsContext gc, int currentScore){
        gc.strokeText(String.format("Current Score : %d", currentScore),30,22);
        gc.strokeText(String.format("Health : %d/%d", userShip.getHitPointsLeft(),ApplicationConstants.UserShipHealth),300,22);
    }

    protected abstract void drawBackground(GraphicsContext gc);

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

    private void collision_detection() {

        for( GameObject userBullet: userBullets) {
            for (GameObject  alienBullet: alienBullets)
                if (StaticHelpers.intersects(userBullet, alienBullet)) {
                    GameObject effect = alienBullet.hitBy(userBullet);
                    if( effect != null )
                        effects.add(effect);
                }
        }

        for( GameObject bullet: userBullets) {
            for (GameObject alienShip : alienShips)
                if ( !bullet.isCleared() &&  StaticHelpers.intersects(alienShip, bullet)) {
                    GameObject effect = alienShip.hitBy(bullet);
                    if( effect != null )
                        effects.add(effect);
                }
        }
        for( GameObject bullet: alienBullets) {
            if ( !bullet.isCleared() && StaticHelpers.intersects(userShip, bullet)) {
                GameObject effect = userShip.hitBy(bullet);
                if (effect != null)
                    effects.add(effect);
            }

        }
    }

    private void update( GameStateInfo gameStateInfo ) {
        double elapsedTime = gameStateInfo.getElapsedTime();
        long cycleCount = gameStateInfo.getCurrentCycleCounter();

        if( userShip.isCleared() || alienShips.size() == 0) {
            isOver = true;
            return;
        }

        userShip.update(elapsedTime, cycleCount);

        for(int i = 0; i< alienShips.size(); i++ )
        {
            GameObject alienShip = alienShips.get(i);
            if( alienShip.isCleared() ) {
                alienShips.remove(i--);
                gameStateInfo.incrementScoreBy(alienShip.getBounty());
            }
            else {
                GameObject possibleNewBullet = alienShip.update(elapsedTime, cycleCount);
                if (possibleNewBullet != null)
                    alienBullets.add(possibleNewBullet);
            }
        }

        updateHelper(elapsedTime, cycleCount, userBullets);
        updateHelper(elapsedTime, cycleCount, alienBullets);
        updateHelper(elapsedTime, cycleCount, effects);
    }

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

    public MouseMoveEventHandler getCustomizedMouseMoveEventHandler() {
        return customizedMouseMoveEventHandler;
    }

    public MouseClickEventHandler getCustomizedMouseClickEventHandler() {
        return customizedMouseClickEventHandler;
    }

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

    public void MouseMoveEventHandle( MouseEvent mouseEvent ){
        double lastMousePositionX = mouseEvent.getX();
        double lastMousePositionY = mouseEvent.getY() > 3*ApplicationConstants.ScreenHeight/5.0? mouseEvent.getY() : 3*ApplicationConstants.ScreenHeight/5.0;
        userShip.setFlyingPositionX(lastMousePositionX);
        userShip.setFlyingPositionY(lastMousePositionY);
    }

    public void MouseClickedEventHandle( MouseEvent mouseEvent ){
        userBullets.add(userShip.shoot());
    }

    public boolean isOver() {
        return isOver;
    }
}