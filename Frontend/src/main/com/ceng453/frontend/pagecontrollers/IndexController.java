package main.com.ceng453.frontend.pagecontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IndexController extends PageController{

    @FXML
    Button login;  // When this button is clicked User is directed to Log In page
    @FXML
    Button register;  // When this button is clicked User is directed to Register page
    @FXML
    Button leaderBoard; // When this button is clicked User is directed to LeaderBoard page

    public void loginHandler(ActionEvent actionEvent){
        try {
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeScene();
    }

    public void registerHandler(ActionEvent actionEvent){
        try {
            root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeScene();
    }

    public void leaderBoardHandler(ActionEvent actionEvent) {
        try {
            root = FXMLLoader.load(getClass().getResource("LeaderBoard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeScene();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login.setOnAction(this::loginHandler);
        register.setOnAction(this::registerHandler);
        leaderBoard.setOnAction(this::leaderBoardHandler);
    }
}

