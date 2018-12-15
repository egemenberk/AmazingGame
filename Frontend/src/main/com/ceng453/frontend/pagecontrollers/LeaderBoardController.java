package main.com.ceng453.frontend.pagecontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URL;
import java.util.*;

public class LeaderBoardController extends PageController{

    @FXML
    private TableView table;

    @FXML
    Button back;

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

    public void getLeaderBoardHandler() {
        Main.primaryStage.setTitle("LeaderBoard");
        final Label label = new Label("Scores");

        configureTable();

        getBoardWithREST();
    }

    private void getBoardWithREST() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(new URI(ApplicationConstants.ServerBaseAdress+"/leaderboard/all_time"), String.class);
            addToTable(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addToTable(ResponseEntity<String> response) {
        JSONArray jsonArr = new JSONArray(response.getBody());
        for (int i = 0; i < jsonArr.length(); i++)
        {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            Leader person = new Leader( jsonObj.get("username").toString(), jsonObj.get("score").toString());
            table.getItems().add(person);
        }
    }

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