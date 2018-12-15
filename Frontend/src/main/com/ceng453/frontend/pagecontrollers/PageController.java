package main.com.ceng453.frontend.pagecontrollers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import main.com.ceng453.frontend.main.ApplicationConstants;
import main.com.ceng453.frontend.main.Main;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

// This abstract class is used to implement basic functions that are in the
// Other Controller classes that manage Pages.
public abstract class PageController implements Initializable {

    // This is used for changing the Scene
    protected Parent root;

    // Helper function to change the page
    // Set The new Scene in the Stage
    protected void changeScene(Parent root) {
        Scene scene = new Scene(root, ApplicationConstants.ScreenWidth, ApplicationConstants.ScreenHeight);
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }

    // Handler written for <back> button that redirects you to index(the first page when you open the game)
    // This handler forwards the user to the index page
    // This handler is used by every Controller except the IndexController
    public void backHandler(javafx.event.ActionEvent actionEvent) {
        try {
            root = FXMLLoader.load(getClass().getResource("Index.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeScene(root);
    }

    // When user enters an invalid Field or Wrong username or password
    // Exception that is thrown by the server will be handled here
    protected void handleWrongInput(HttpClientErrorException e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error Happened");
        alert.setHeaderText("Look, an Information Dialog");
        if(e.getRawStatusCode()==400) {
            alert.setContentText("Please Fill in the Blanks!");
        }
        else if(e.getRawStatusCode()==403) {
            alert.setContentText("No such User");
        }
        alert.showAndWait();
    }


}