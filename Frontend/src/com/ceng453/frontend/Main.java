package com.ceng453.frontend;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.util.LinkedList;

public class Main extends Application {

    LinkedList<GameLevel> levels;
    GameLevel currentLevel;

    @Override
    public void init() throws Exception {
        super.init();
        levels = new LinkedList<>();
        levels.push(new GameLevel2());
        levels.push(new GameLevel1());
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Amazing Game");

        String musicFile = ApplicationConstants.GameMusicFilename;     // For example

        Media sound = new Media(new File(System.getProperty("user.dir") + "/assets/" + musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        MediaView mediaView = new MediaView(mediaPlayer);


        Group root = new Group();
        Scene scene = new Scene(root, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
        Canvas canvas = new Canvas( ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        root.getChildren().add(mediaView);
        stage.setScene(scene);

        stage.show();
        mediaPlayer.play();

        updateCurrentLevel(canvas);

        final GameStateInfo gameStateInfo = new GameStateInfo(System.nanoTime());

        AnimationTimer currentTimer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - gameStateInfo.getPreviousLoopTime()) / 1000000000.0; // in secs
                gameStateInfo.setElapsedTime(elapsedTime);
                gameStateInfo.setPreviousLoopTime(currentNanoTime);

                currentLevel.gameLoop(gameStateInfo, gc);

                if( currentLevel.isOver() )
                    updateCurrentLevel(canvas);

                gameStateInfo.incrementCurrentCycleCount();
            }
        };
        currentTimer.start();
    }

    private void updateCurrentLevel(Canvas canvas) {
        if( !levels.isEmpty() ) {
            currentLevel = levels.pop();

            canvas.setOnMouseMoved(currentLevel.getCustomizedMouseMoveEventHandler());
            canvas.setOnMouseClicked(currentLevel.getCustomizedMouseClickEventHandler());
        }
        else
            ; // TODO What to do when game is over?
    }


    public static void main(String[] args) {
        launch(args);
    }
}

