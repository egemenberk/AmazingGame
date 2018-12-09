package com.ceng453.frontend;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.LinkedList;

public class Main extends Application {

    double lastMousePositionX = 500;
    double lastMousePositionY = 500;


    Collection<GameObject> effects;
    Collection<GameObject> alienShips;
    Collection<GameObject> userBullets;
    Collection<GameObject> alienBullets;
    UserShip userShip;

    @Override
    public void init() throws Exception {
        super.init();
        alienShips = new LinkedList<>();
        userBullets = new LinkedList<>();
        effects = new LinkedList<>();
        alienBullets = new LinkedList<>();
    }

    public void generateAliens() throws FileNotFoundException {
        //Creating an image
        int OffsetX = 50, OffsetY = 30;
        int StepX = 60, StepY = 100;
        int alienCountInRow = 9;
        int rowCount = 3;

        for( int i=0; i<alienCountInRow*rowCount; i++ )
        {
            EasyEnemyShip alienShip = new EasyEnemyShip(ApplicationConstants.AlienShipImage, 40,70);
            alienShip.initialize(2,2);
            alienShip.setPosition(OffsetX + StepX*(i%alienCountInRow),OffsetY+StepY*(i/alienCountInRow));

            alienShips.add(alienShip);
        }
    }

    public void generateUserShip() throws FileNotFoundException {
        int userShipWidth = 100, userShipHeight = 100;
        userShip = new UserShip(ApplicationConstants.UserShipImage, userShipWidth,userShipHeight);
        userShip.initialize(3,2);
        userShip.setPosition(200,2*ApplicationConstants.ScreenHeight/3 );
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        stage.setTitle("Game Level 1");

        Group root = new Group();
        Scene scene = new Scene(root, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
        Canvas canvas = new Canvas( ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
        root.getChildren().add(canvas);
        stage.setScene(scene);


        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                lastMousePositionX = mouseEvent.getX();
                lastMousePositionY = mouseEvent.getY() > 3*ApplicationConstants.ScreenHeight/5.0? mouseEvent.getY() : 3*ApplicationConstants.ScreenHeight/5.0;
                //userShip.setPosition( mouseEvent.getX()-userShip.getWidth()/2, mouseEvent.getY() > 2*ScreenHeight/3? mouseEvent.getY()-userShip.getHeight()/2 : 600   );
            }
        });

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                userBullets.add(userShip.shoot());
            }
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        generateAliens();
        generateUserShip();

        final long[] outsideWorldData = {System.nanoTime(), 0L}; // TODO Make a class or something xd

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - outsideWorldData[0]) / 1000000000.0; // in secs
                outsideWorldData[0] = currentNanoTime;

                process_input();

                update(elapsedTime, outsideWorldData[1]);

                collision_detection();

                draw(gc);

                outsideWorldData[1]++;
            }
        }.start();

        stage.show();
    }

    private void draw(GraphicsContext gc) {
        gc.drawImage(ApplicationConstants.BackGroundImage, 0,0, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);

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

    private void update(double elapsedTime, long cycleCount) {

        if( !userShip.isCleared() )
            userShip.update(elapsedTime, cycleCount);

        for( GameObject alien : alienShips ) {
            if( alien.isCleared() )
                alienShips.remove(alien);
            else {
                GameObject possibleNewBullet = alien.update(elapsedTime, cycleCount);
                if( possibleNewBullet != null )
                    alienBullets.add(possibleNewBullet);
            }
        }

        for( GameObject bullet : userBullets){
            if( bullet.isCleared() )
                userBullets.remove(bullet);
            else
                bullet.update(elapsedTime,cycleCount);
        }

        for( GameObject bullet : alienBullets ){
            if( bullet.isCleared() )
                alienBullets.remove(bullet);
            else
                bullet.update(elapsedTime,cycleCount);
        }

        for( GameObject effect : effects){
            if( effect.isCleared() )
                effects.remove(effect);
            else
                effect.update(elapsedTime,cycleCount);
        }

    }

    private void process_input() {
        userShip.setVelocityX( -5*(userShip.getPositionX() - lastMousePositionX+userShip.getWidth()/2 ) );
        userShip.setVelocityY( -5*(userShip.getPositionY() - lastMousePositionY+userShip.getHeight()/2 ) );
    }


    public static void main(String[] args) {
        launch(args);
    }
}
