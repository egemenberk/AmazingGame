package main.com.ceng453.frontend.pagecontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.com.ceng453.frontend.gamelevels.GameService;
import main.com.ceng453.frontend.main.ApplicationConstants;
import main.com.ceng453.frontend.main.Main;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController extends PageController{

    @FXML
    Button signUp;
    @FXML
    TextField usernameField;
    @FXML
    TextField emailField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button back;

    public void registerHandler(ActionEvent actionEvent){

        JSONObject params = getUserFields();

        HttpHeaders headers = createHttpHeaders();

        HttpEntity<String> request = new HttpEntity<>(params.toString(), headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(ApplicationConstants.ServerBaseAdress +"/signup", request, String.class);
            System.out.println(response.getBody());

            GameService newGame = new GameService( new JSONObject( response.getBody() ).getString("session") );
            newGame.startGame( Main.primaryStage );

        } catch (HttpClientErrorException e) {
            handleWrongInput(e);
        } catch (ResourceAccessException e) {
            handleSystemIsDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private JSONObject getUserFields() {
        JSONObject params = new JSONObject();
        params.put("username", usernameField.getText() );
        params.put("email", emailField.getText() );
        params.put("password", passwordField.getText() );
        return params;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUp.setOnAction(this::registerHandler);
        back.setOnAction(this::backHandler);
    }

}

