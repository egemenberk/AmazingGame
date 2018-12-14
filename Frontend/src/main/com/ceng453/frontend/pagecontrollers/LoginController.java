package main.com.ceng453.frontend.pagecontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.com.ceng453.frontend.gamelevels.GameService;
import main.com.ceng453.frontend.main.Main;
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

    // This function logins to the server
    // If login is successful game will start
    public void login(ActionEvent actionEvent) {

        JSONObject params = getUserFields();

        HttpHeaders headers = createHttpHeaders();

        HttpEntity<String> request = createRESTRequest(params, headers);

        loginToServer(request);
    }

    private HttpEntity<String> createRESTRequest(JSONObject params, HttpHeaders headers) {
        return new HttpEntity<>(params.toString(), headers);
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private JSONObject getUserFields() {
        JSONObject params = new JSONObject();
        params.put("username", usernameField.getText() );
        params.put("password", passwordField.getText() );
        return params;
    }

    private void loginToServer(HttpEntity<String> request) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/login", request, String.class);
            System.out.println(response.getBody());

            GameService newGame = new GameService( new JSONObject( response.getBody() ).getString("Token") );
            newGame.startGame( Main.primaryStage );

        } catch (HttpClientErrorException e) {
            handleWrongInput(e);
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
