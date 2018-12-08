package com.ceng453.frontend;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.LinkedList;

public class Main extends Application {

    final int ScreenWidth = 600;
    final int ScreenHeight = 1000;

    double lastMousePositionX = 500;
    double lastMousePositionY = 500;


    Collection<GameObject> alienShips;
    GameObject userShip;

    @Override
    public void init() throws Exception {
        super.init();
        alienShips = new LinkedList<>();
    }

    private FileInputStream getResourceFromAssets( String filename ) throws FileNotFoundException {
        System.out.println(System.getProperty("user.dir") + "/assets/" + filename);
        return new FileInputStream(System.getProperty("user.dir") + "/assets/" + filename);
    }

    public void generateAliens() throws FileNotFoundException {
        //Creating an image
        Image image = new Image(getResourceFromAssets("alien4.png"));
        int OffsetX = 50, OffsetY = 30;
        int StepX = 60, StepY = 100;
        int alienCountInRow = 9;
        int rowCount = 3;

        for( int i=0; i<alienCountInRow*rowCount; i++ )
        {
            EasyEnemyShip alienShip = new EasyEnemyShip(image, 40,70);
            alienShip.initialize(i+1,2,2);
            alienShip.setPosition(OffsetX + StepX*(i%alienCountInRow),OffsetY+StepY*(i/alienCountInRow));

            alienShips.add(alienShip);
        }
    }

    public void generateUserShip() throws FileNotFoundException {
        Image image = new Image(getResourceFromAssets("user_ship_5sided.png"));
        int userShipWidth = 100, userShipHeight = 100;
        userShip = new UserShip(image, userShipWidth,userShipHeight);
        userShip.initialize(0,2,2);
        userShip.setPosition(200,2*ScreenHeight/3 );
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        stage.setTitle("Game Level 1");

        Group root = new Group();
        Scene scene = new Scene(root, ScreenWidth, ScreenHeight);
        Canvas canvas = new Canvas( ScreenWidth, ScreenHeight);
        root.getChildren().add(canvas);
        stage.setScene(scene);


        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                lastMousePositionX = mouseEvent.getX();
                lastMousePositionY = mouseEvent.getY() > 3*ScreenHeight/5.0? mouseEvent.getY() : 3*ScreenHeight/5.0;
                //userShip.setPosition( mouseEvent.getX()-userShip.getWidth()/2, mouseEvent.getY() > 2*ScreenHeight/3? mouseEvent.getY()-userShip.getHeight()/2 : 600   );
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
        gc.clearRect(0, 0, ScreenWidth,ScreenHeight);

        userShip.render(gc);

        for( GameObject alien : alienShips )
            alien.render(gc);
    }

    private void collision_detection() {

    }

    private void update(double elapsedTime, long cycleCount) {

        userShip.update(elapsedTime, cycleCount);

        for( GameObject alien : alienShips )
            alien.update( elapsedTime, cycleCount );
    }

    private void process_input() {
        userShip.setVelocityX( -5*(userShip.getPositionX() - lastMousePositionX+userShip.getWidth()/2 ) );
        userShip.setVelocityY( -5*(userShip.getPositionY() - lastMousePositionY+userShip.getHeight()/2 ) );
    }


    public static void main(String[] args) {
        launch(args);
    }
}
