package main.com.ceng453.client.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import main.com.ceng453.ApplicationConstants;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;

public class Main extends Application {

    public static Stage primaryStage; // This is the primary Stage,
                                      // will be used by other methods to set new scenes to screen

    @Override
    public void start(Stage stage) throws Exception { // Init of the app.
        Main.primaryStage = stage;
        //stage.setResizable(false); // Those two lines disables resizing of the window
        stage.setTitle("Amazing Game");
        startMainPage(stage);

    }

    // Loads Main Menu to the screen
    private void startMainPage(Stage stage) throws java.io.IOException {
        System.out.println(getClass().toString());
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Index.fxml"));
        //Working ->
        //new Media(getClass().getResource("/assets/" + ApplicationConstants.GAME_MUSIC_FILENAME).toString());
        Scene scene = new Scene(root, ApplicationConstants.SCREEN_WIDTH, ApplicationConstants.SCREEN_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

