package main.com.ceng453.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends PageController{

    @FXML
    Button login;
    @FXML
    Button back;
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;


    public void login(ActionEvent actionEvent) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject params = new JSONObject();
        params.put("username", usernameField.getText() );
        params.put("password", passwordField.getText() );
        //System.out.println(params.toString());

        HttpEntity<String> request = new HttpEntity<>(params.toString(), headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/login", request, String.class);
            System.out.println(response.getBody());

            GameService newGame = new GameService( new JSONObject( response.getBody() ).getString("Token") );
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
        login.setOnAction(this::login);
        back.setOnAction(this::backHandler);
    }
}
