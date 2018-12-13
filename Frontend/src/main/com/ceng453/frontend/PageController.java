package main.com.ceng453.frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;


public abstract class PageController implements Initializable {

    protected Parent root;

    // Helper function to change the page
    protected void changeScene(Parent root) {
        Scene scene = new Scene(root, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }

    // This handler forwards the user to the index page
    // This handler is used by every Controller except the IndexController
    public void backHandler(javafx.event.ActionEvent actionEvent) {
        try {
            root = FXMLLoader.load(getClass().getResource("Index.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeScene(root);
    }

}
