package com.ceng453.frontend;

import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Main extends Application {

    Collection<GameObject> alienShips;

    @Override
    public void init() throws Exception {
        super.init();
        alienShips = new LinkedList<>();
    }

    public void generateAliens(Group root) throws FileNotFoundException {
        //Creating an image
        Image image = new Image(new FileInputStream(System.getProperty("user.dir") + "/assets/alien4.png"));

        for( int i=0; i<9; i++ )
        {
            EasyEnemy alienShip = new EasyEnemy(image, 50,130);
            alienShip.initialize(i,2,2);
            alienShip.setPosition(150*(i%3+1),130*(i/3+1));

            alienShips.add(alienShip);
        }
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        stage.setTitle("Game Level 1");

        Group root = new Group();
        Scene scene = new Scene(root, 800, 600);
        Canvas canvas = new Canvas( 800, 600 );
        root.getChildren().add(canvas);
        stage.setScene(scene);


        GraphicsContext gc = canvas.getGraphicsContext2D();

        generateAliens(root);

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

                colision_detection();

                // render

                draw(gc);

                outsideWorldData[1]++;
            }
        }.start();

        stage.show();
    }

    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, 800,600);
        for( GameObject alien : alienShips )
            alien.render(gc);
    }

    private void colision_detection() {

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
