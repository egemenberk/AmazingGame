package main.com.ceng453.frontend.pagecontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.com.ceng453.frontend.main.ApplicationConstants;
import main.com.ceng453.frontend.main.Main;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URL;
import java.util.*;

public class LeaderBoardController extends PageController{

    @FXML
    private TableView table; // This table holds the all time leaders

    @FXML
    Button back;  // Back button to go the main menu

    // We have created a simple class to imitate the User
    public class Leader {
        private String name;
        private String score;

        public Leader(String name, String score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public String getScore() {
            return score;
        }
    }

    // Function to get the all time Scores from the Server
    public void getLeaderBoardHandler() {
        Main.primaryStage.setTitle("LeaderBoard");
        configureTable();
        getBoardWithREST();
    }

    // We make a REST request to Server
    private void getBoardWithREST() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(new URI(ApplicationConstants.ServerBaseAdress+"/leaderboard/all_time"), String.class);
            addToTable(response);
        } catch (HttpClientErrorException e) { // User has provided wrong input Server responds it with HTTP* Exception
            handleWrongInput(e);
        } catch(ResourceAccessException e) { // Server is probably not started or down
            handleSystemIsDown();
        } catch (Exception e) { // Some other thing is going around
            e.printStackTrace();
        }
    }

    // Adding the scores that is coming from the Server by parsing it as a Json Response
    private void addToTable(ResponseEntity<String> response) {
        JSONArray jsonArr = new JSONArray(response.getBody());
        for (int i = 0; i < jsonArr.length(); i++)
        {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            Leader person = new Leader( jsonObj.get("username").toString(), jsonObj.get("score").toString());
            table.getItems().add(person);
        }
    }

    // We set the properties of the table
    private void configureTable() {
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn scoreColumn = new TableColumn("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        nameColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.68));
        scoreColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.29));
        table.getColumns().addAll(nameColumn, scoreColumn);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getLeaderBoardHandler();
        back.setOnAction(this::backHandler);
    }

}