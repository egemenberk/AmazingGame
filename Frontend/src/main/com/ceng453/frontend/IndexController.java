package main.com.ceng453.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IndexController implements Initializable {

    @FXML
    Button login;
    @FXML
    Button register;


    public void loginHandler(ActionEvent actionEvent){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeScene(root);

    }

    public void registerHandler(ActionEvent actionEvent){
         Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeScene(root);
    }

    private void changeScene(Parent root) {
        Scene scene = new Scene(root, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login.setOnAction(this::loginHandler);
        register.setOnAction(this::registerHandler);
    }
}

