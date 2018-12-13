package main.com.ceng453.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    Button signUp;
    @FXML
    TextField usernameField;
    @FXML
    TextField emailField;
    @FXML
    PasswordField passwordField;

    public void registerHandler(ActionEvent actionEvent){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject params = new JSONObject();
        params.put("username", usernameField.getText() );
        params.put("email", emailField.getText() );
        params.put("password", passwordField.getText() );

        HttpEntity<String> request = new HttpEntity<>(params.toString(), headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/signup", request, String.class);
            System.out.println(response.getBody());

            GameService newGame = new GameService( new JSONObject( response.getBody() ).getString("session") );
            newGame.startGame( Main.primaryStage );

        } catch (HttpClientErrorException e) {
            if(e.getRawStatusCode()==400)
                System.out.println("Fill the blanks");
            else if(e.getRawStatusCode()==403)
                System.out.println("No such User");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUp.setOnAction(this::registerHandler);
    }
}

