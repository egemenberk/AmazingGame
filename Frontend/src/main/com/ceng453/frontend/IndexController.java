package main.com.ceng453.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IndexController extends PageController{

    @FXML
    Button login;
    @FXML
    Button register;
    @FXML
    Button leaderBoard;

    public void loginHandler(ActionEvent actionEvent){
        try {
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeScene(root);
    }

    public void registerHandler(ActionEvent actionEvent){
        try {
            root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeScene(root);
    }

    public void leaderBoardHandler(ActionEvent actionEvent) {
        try {
            root = FXMLLoader.load(getClass().getResource("LeaderBoard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeScene(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login.setOnAction(this::loginHandler);
        register.setOnAction(this::registerHandler);
        leaderBoard.setOnAction(this::leaderBoardHandler);
    }
}

