package main.com.ceng453.frontend.pagecontrollers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.com.ceng453.ApplicationConstants;
import main.com.ceng453.frontend.main.Main;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URL;
import java.util.*;

public class LeaderBoardController extends PageController{

    @FXML
    private TableView<Object> table; // This table holds the all time leaders

    @FXML
    Button back;  // Back button to go the main menu

    @FXML
    ComboBox<String> combobox; // For selecting which LeaderBoard to show. Last 7 days and All Time

    // We have created a simple class to imitate the User
    @SuppressWarnings("WeakerAccess")
    public class Leader {
        private final String name;
        private final String score;

        Leader(String name, String score) {
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
    // The Parameter is used to alternate between 7 days and All Time
    private void getLeaderBoardHandler(String timeInterval) {
        Main.primaryStage.setTitle(timeInterval);
        getBoardWithREST(timeInterval);
    }

    // We make a REST request to Server
    private void getBoardWithREST(String timeInterval) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(new URI(ApplicationConstants.REST_SERVER_ADDRESS +"/leaderboard/" + timeInterval), String.class);
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
        JSONArray jsonArr = new JSONArray(Objects.requireNonNull(response.getBody()));
        for (int i = 0; i < jsonArr.length(); i++)
        {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            Leader person = new Leader( jsonObj.get("username").toString(), jsonObj.get("score").toString());
            table.getItems().add(person);
        }
    }

    // We set the properties of the table
    @SuppressWarnings("unchecked")
    private void configureTable() {
        TableColumn<Object, Object> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Object, Object> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        nameColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.68));
        scoreColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.29));
        table.getColumns().addAll(nameColumn, scoreColumn);
    }

    // It updates the table according to the selected option
    private void changeTable(Event event) {
        table.getItems().clear();
        if(combobox.getValue().equals("All Time"))
            getLeaderBoardHandler("all_time");
        else
            getLeaderBoardHandler("7_days");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureTable();
        getLeaderBoardHandler("7_days");
        back.setOnAction(this::backHandler);
        combobox.getItems().addAll("All Time", "Last 7 days");
        combobox.getSelectionModel().select("All Time");
        combobox.getSelectionModel().select("Last 7 days");
        combobox.setOnAction(this::changeTable);
    }

}