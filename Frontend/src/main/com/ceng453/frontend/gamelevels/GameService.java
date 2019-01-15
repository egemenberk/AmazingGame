package main.com.ceng453.frontend.gamelevels;

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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.com.ceng453.ApplicationConstants;
import main.com.ceng453.frontend.pagecontrollers.PageController;
import main.com.ceng453.frontend.main.ClientCommunicationHandler;
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
        //levels.push(new GameLevel3());
        //levels.push(new GameLevel2());
        //levels.push(new GameLevel1());
        levels.push(new MultiplayerGameLevel());

        this.userAuthToken = userAuthToken;
    }

    public void startGame(Stage stage) throws IOException {
        stage.setTitle("Amazing Game");

        // Setting Background Music
        String musicFile = ApplicationConstants.GameMusicFilename;     // For example
        Media sound = new Media(new File(System.getProperty("user.dir") + "/assets/" + musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.45);
        mediaView = new MediaView(mediaPlayer);
        mediaPlayer.play();

        // Creating group, scene and game's canvas. That canvas will be passed to all GameObjects to rendering
        Group root = new Group();
        Scene scene = new Scene(root, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
        Canvas canvas = new Canvas( ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
        gc = canvas.getGraphicsContext2D();

        // Adding canvas and mediaView to the root
        root.getChildren().add(canvas);
        root.getChildren().add(mediaView);
        stage.setScene(scene); // Stage is setting its scene
        stage.show();

        updateCurrentLevel(canvas, false, 0); // Getting first level from levels
        gameStateInfo.setPreviousLoopTime(System.nanoTime()); // Calibrate initial game start time

        gameLoop = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - gameStateInfo.getPreviousLoopTime()) / 1000000000.0; // in secs
                gameStateInfo.setElapsedTime(elapsedTime); // Setting game Info class' corresponding variables
                // From the elapsed time
                if(elapsedTime > 15/1000.0) { // TODO is it correct?
                    gameStateInfo.setPreviousLoopTime(currentNanoTime); // GameObjects will calculate their displacements
                    currentLevel.gameLoop(gameStateInfo, gc); // This call will generate a new frame of the game
                }
                if( currentLevel.levelPassed() || currentLevel.isOver() || currentLevel.won != 0) {
                    updateCurrentLevel(canvas, currentLevel.isOver(), currentLevel.won); // Get new level from levels list
                    gameStateInfo.restartCycleCounter(); // Restaring cycle counter to make the new level to start from cycle=0
                }
                gameStateInfo.incrementCurrentCycleCount(); // increase cycle counter
            }
        };

        gameLoop.start(); // Starting animation timer

    }

    private void updateCurrentLevel(Canvas canvas, boolean isOver, int won) {

        if ( (isOver && won == 0 ) || won == -1) { // Game is lost
            // Draw game over text
            System.out.println("Game lost");
            gameLoop.stop(); // Stop the game loop
            gc.drawImage(ApplicationConstants.GameOverImage, 0,0, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);

            // Setting mediaView's player to play Dattiridat song
            mediaView.getMediaPlayer().stop();
            Media sound = new Media(new File(System.getProperty("user.dir") + "/assets/" + ApplicationConstants.Dattiridatdat).toURI().toString());
            mediaView.setMediaPlayer(new MediaPlayer(sound));
            mediaView.getMediaPlayer().setVolume(0.3);
            mediaView.getMediaPlayer().setCycleCount(1);
            mediaView.getMediaPlayer().play();
            //pause = new PauseTransition( Duration.seconds(5) );
            waitAtTheEnd(4);
        }

        else if( !levels.isEmpty() && won <= 0) { // Meaning there is still levels exists
            currentLevel = levels.pop(); // Get the next level
            // And set mouse handlers of the canvas
            canvas.setOnMouseMoved(currentLevel.getCustomizedMouseMoveEventHandler());
            canvas.setOnMouseClicked(currentLevel.getCustomizedMouseClickEventHandler());
        }
        else // In this case, we have completed the game
        {
            System.out.println("Game won");
            // Drawing the 'Amazing!' image
            gc.drawImage(ApplicationConstants.JustWowImage, 0,0, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
            gameLoop.stop(); // Stop the game loop
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
                //sendCurrentScoreLog(); // And send score to server
                PageController.root = FXMLLoader.load(getClass().getResource("../pagecontrollers/LeaderBoard.fxml"));
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
            new RestTemplate().postForEntity(ApplicationConstants.RestServerBaseAddress +"/score", request, String.class);

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
