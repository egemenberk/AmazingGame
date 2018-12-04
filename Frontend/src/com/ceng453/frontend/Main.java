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

        for( int i=0; i<9; i++ )
        {
            EasyEnemy alienShip = new EasyEnemy(image, 50,80);
            alienShip.initialize(i+1,2,2);
            alienShip.setPosition(50 +150*(i%3),50+130*(i/3));

            alienShips.add(alienShip);
        }
    }

    public void generateUserShip() throws FileNotFoundException {
        Image image = new Image(getResourceFromAssets("user_ship_5sided.png"));
        userShip = new EasyEnemy(image, 100,100);
        userShip.initialize(0,2,2);
        userShip.setPosition(500,250);
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        stage.setTitle("Game Level 1");

        Group root = new Group();
        Scene scene = new Scene(root, 800, 600);
        Canvas canvas = new Canvas( 800, 600 );
        root.getChildren().add(canvas);
        stage.setScene(scene);


        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                userShip.setPosition( mouseEvent.getX()-userShip.getWidth()/2, mouseEvent.getY()-userShip.getHeight()/2 );
            }
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        generateAliens();
        generateUserShip();

        final long[] outsideWorldData = {System.nanoTime(), 0L}; // Make a class or something xd
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

                // render

                draw(gc);

                outsideWorldData[1]++;
            }
        }.start();

        stage.show();
    }

    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, 800,600);

        userShip.render(gc);

        for( GameObject alien : alienShips )
            alien.render(gc);
    }

    private void collision_detection() {

    }

    private void update(double elapsedTime, long cycleCount) {
        for( GameObject alien : alienShips )
            alien.update( elapsedTime, cycleCount );
    }

    private void process_input() {

    }


    public static void main(String[] args) {
        launch(args);
    }
}
