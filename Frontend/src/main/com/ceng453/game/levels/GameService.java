package main.com.ceng453.game.levels;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.com.ceng453.ApplicationConstants;
import main.com.ceng453.client.multiplayer.MultiplayerGameLevel;
import main.com.ceng453.client.pagecontrollers.PageController;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.LinkedList;

/*
 * This class is an interface between Javafx framework and GameLevels.
 * Stage initialization and calls to the GameLevels are done from this class
 */
public class GameService {

    private final LinkedList<AbstractGameLevel> levels; // Game levels that is added to the game
    private AbstractGameLevel currentLevel; // Current game level
    private GraphicsContext gc; // Canvas of our screen
    private final String userAuthToken; // Logged in user's token, saved for sending scores
    private AnimationTimer gameLoop; // A timer to call AbstractGameLevel's frame generation
    private MediaView mediaView; // Media View, for background musics
    private final GameStateInfo gameStateInfo = new GameStateInfo(System.nanoTime()); // GameStateInfo, described in details in its class
    private PauseTransition pause;

    public GameService(String userAuthToken) {
        levels = new LinkedList<>();

        // Adding levels to our list to pop, when current level is completed
        levels.push(new MultiplayerGameLevel());
        levels.push(new GameLevel3());
        levels.push(new GameLevel2());
        levels.push(new GameLevel1());

        this.userAuthToken = userAuthToken;
    }

    public void startGame(Stage stage) {
        stage.setTitle("Amazing Game");

        // Setting Background Music
        Media sound = new Media(getClass().getResource("/assets/" + ApplicationConstants.GAME_MUSIC_FILENAME).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.45);
        mediaView = new MediaView(mediaPlayer);
        mediaPlayer.play();

        // Creating group, scene and game's canvas. That canvas will be passed to all GameObjects to rendering
        Group root = new Group();
        Scene scene = new Scene(root, ApplicationConstants.SCREEN_WIDTH, ApplicationConstants.SCREEN_HEIGHT);
        Canvas canvas = new Canvas( ApplicationConstants.SCREEN_WIDTH, ApplicationConstants.SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Adding canvas and mediaView to the root
        root.getChildren().add(canvas);
        root.getChildren().add(mediaView);
        stage.setScene(scene); // Stage is setting its scene
        stage.show();

        updateCurrentLevel(canvas, false); // Getting first level from levels
        gameStateInfo.setPreviousLoopTime(System.nanoTime()); // Calibrate initial game start time

        // Space to pass the level.
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.SPACE && levels.size() != 0) {
                System.out.println("CHEAT ACTIVATED ! xd");
                currentLevel.levelPassed = true;
            }
        });

        gameLoop = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - gameStateInfo.getPreviousLoopTime()) / 1000000000.0; // in secs
                gameStateInfo.setElapsedTime(elapsedTime); // Setting game Info class' corresponding variables
                // From the elapsed time
                if( currentLevel.levelPassed() || currentLevel.isOver()) {
                    updateCurrentLevel(canvas, currentLevel.isOver()); // Get new level from levels list
                    gameStateInfo.restartCycleCounter(); // Restarting cycle counter to make the new level to start from cycle=0
                }
                else if(elapsedTime >= ApplicationConstants.TICK_MS/1000.0) {
                    gameStateInfo.setPreviousLoopTime(currentNanoTime); // GameObjects will calculate their displacements
                    currentLevel.gameLoop(gameStateInfo, gc); // This call will generate a new frame of the game
                }
                gameStateInfo.incrementCurrentCycleCount();
            }
        };

        gameLoop.start(); // Starting animation timer

    }

    private void updateCurrentLevel(Canvas canvas, boolean isOver) {

        if ( isOver ) { // Game is lost
            // Draw game over text
            System.out.println("Game lost");
            gameLoop.stop(); // Stop the game loop
            gc.drawImage(ApplicationConstants.GAME_OVER_IMAGE, 0,0, ApplicationConstants.SCREEN_WIDTH, ApplicationConstants.SCREEN_HEIGHT);

            // Setting mediaView's player to play Dattiridat song
            mediaView.getMediaPlayer().stop();
            Media sound = new Media(getClass().getResource("/assets/"+ApplicationConstants.DATDIRIDAT_WAV).toString());
            mediaView.setMediaPlayer(new MediaPlayer(sound));
            mediaView.getMediaPlayer().setVolume(0.3);
            mediaView.getMediaPlayer().setCycleCount(1);
            mediaView.getMediaPlayer().play();
            //pause = new PauseTransition( Duration.seconds(5) );
            waitAtTheEnd(4);
        }

        else if( !levels.isEmpty() ) { // Meaning there is still levels exists
            currentLevel = levels.pop(); // Get the next level
            currentLevel.triggerInitiation();
            // And set mouse handlers of the canvas
            canvas.setOnMouseMoved(currentLevel.getCustomizedMouseMoveEventHandler());
            canvas.setOnMouseClicked(currentLevel.getCustomizedMouseClickEventHandler());
        }
        else // In this case, we have completed the game
        {
            System.out.println("Game won");
            // Drawing the 'Amazing!' image
            gameLoop.stop(); // Stop the game loop
            gc.drawImage(ApplicationConstants.JUST_WOW_IMAGE, 0,0, ApplicationConstants.SCREEN_WIDTH, ApplicationConstants.SCREEN_HEIGHT);
            waitAtTheEnd(3);
        }

    }

    // Wait for Images to Load
    // Send the User Score to the Server
    // Then Redirect the User to the LeaderBoard
    private void waitAtTheEnd(int seconds) {
        pause = new PauseTransition( Duration.seconds(seconds) );
        pause.setOnFinished(event -> {
            try {
                sendCurrentScoreLog(); // And send score to server
                PageController.root = FXMLLoader.load(getClass().getResource("/fxml/LeaderBoard.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            PageController.changeScene();
        });
        pause.play();
    }

    // This method sends game score to the server
    private void sendCurrentScoreLog(){
        // Creating HTTP Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Creating http params
        JSONObject params = new JSONObject();
        params.put("token", userAuthToken );
        params.put("score", gameStateInfo.getCurrentGameScore());

        HttpEntity<String> request = new HttpEntity<>(params.toString(), headers);
        try {
            // Make http call with headers & params
            new RestTemplate().postForEntity(ApplicationConstants.REST_SERVER_ADDRESS +"/score", request, String.class);

        } catch (HttpClientErrorException e) { // There, we catch non 200 response types
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("An exception occurred!");
            alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Error while sending scores to the server, params : "+params.toString())));
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
