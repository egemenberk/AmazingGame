package main.com.ceng453.frontend;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
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
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.LinkedList;

public class GameService {

    LinkedList<GameLevel> levels;
    GameLevel currentLevel;
    GraphicsContext gc;
    String userAuthToken;
    AnimationTimer gameLoop;
    final GameStateInfo gameStateInfo = new GameStateInfo(System.nanoTime());


    public GameService(String userAuthToken) {
        levels = new LinkedList<>();
        levels.push(new GameLevel3());
        levels.push(new GameLevel2());
        levels.push(new GameLevel1());

        this.userAuthToken = userAuthToken;
    }

    public void startGame(Stage stage) throws Exception {
        stage.setTitle("Amazing Game");

        String musicFile = ApplicationConstants.GameMusicFilename;     // For example

        Media sound = new Media(new File(System.getProperty("user.dir") + "/assets/" + musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        MediaView mediaView = new MediaView(mediaPlayer);


        Group root = new Group();
        Scene scene = new Scene(root, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
        Canvas canvas = new Canvas( ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        root.getChildren().add(mediaView);
        stage.setScene(scene);

        stage.show();
        mediaPlayer.play();

        updateCurrentLevel(canvas, false);
        gameStateInfo.setPreviousLoopTime(System.nanoTime());

        gameLoop = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - gameStateInfo.getPreviousLoopTime()) / 1000000000.0; // in secs
                gameStateInfo.setElapsedTime(elapsedTime);
                gameStateInfo.setPreviousLoopTime(currentNanoTime);

                currentLevel.gameLoop(gameStateInfo, gc);

                if( currentLevel.levelPassed() || currentLevel.isOver()) {
                    try {
                        updateCurrentLevel(canvas, currentLevel.isOver());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                gameStateInfo.incrementCurrentCycleCount();
            }
        };
        gameLoop.start();

    }

    private void updateCurrentLevel(Canvas canvas, boolean isOver) throws Exception {

        if(isOver){
            gc.drawImage(ApplicationConstants.GameOverImage, 0,0, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
            gameLoop.stop();
            sendCurrentScoreLog();

            //TODO that will be leaderboard screen
        }

        else if( !levels.isEmpty() ) {
            currentLevel = levels.pop();
            canvas.setOnMouseMoved(currentLevel.getCustomizedMouseMoveEventHandler());
            canvas.setOnMouseClicked(currentLevel.getCustomizedMouseClickEventHandler());
        }

    }

    private void sendCurrentScoreLog(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject params = new JSONObject();
        params.put("token", userAuthToken );
        params.put("score", gameStateInfo.getCurrentGameScore());

        HttpEntity<String> request = new HttpEntity<>(params.toString(), headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(ApplicationConstants.ServerBaseAdress+"/score", request, String.class);
            System.out.println(response.getBody());

        } catch (HttpClientErrorException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("An exception occurred!");
            alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea("Error while sending scores to the server, params : "+params.toString())));
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
