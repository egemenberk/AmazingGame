package main.com.ceng453.frontend.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class Main extends Application {

    public static Stage primaryStage; // This is the primary Stage,
                                      // will be used by other methods to set new scenes to screen

    @Override
    public void start(Stage stage) throws Exception { // Init of the app.
        Main.primaryStage = stage;
        stage.setResizable(false); // Those two lines disables resizing of the window
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Amazing Game");

        startMainPage(stage);

    }

    // Loads Main Menu to the screen
    private void startMainPage(Stage stage) throws java.io.IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../pagecontrollers/Index.fxml"));
        Scene scene = new Scene(root, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
