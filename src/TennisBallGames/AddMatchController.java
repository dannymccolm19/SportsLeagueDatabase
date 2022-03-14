package TennisBallGames;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

//Author: Daniel McColm

public class AddMatchController implements Initializable {
    // Some @FXML declarations
    @FXML
    Button cancelBtn;

    @FXML
    ComboBox homeTeamBox;

    @FXML
    ComboBox visitorTeamBox;

    // Some local variable declarations
    // The data variable is used to populate the ComboBoxs

    final ObservableList<String> data = FXCollections.observableArrayList();
    // To reference the models inside the controller
    private MatchesAdapter matchesAdapter;
    private TeamsAdapter teamsAdapter;
    public void setModel(MatchesAdapter match, TeamsAdapter team) {
    matchesAdapter = match;
    teamsAdapter = team;
    buildComboBoxData();
    }

    @FXML
    public void cancel() {
    Stage stage = (Stage) cancelBtn.getScene().getWindow();
    stage.close();}

    @FXML
    public void save() {
        try {
            matchesAdapter.insertMatch(matchesAdapter.getMax(), homeTeamBox.getValue().toString(), visitorTeamBox.getValue().toString());
        } catch (SQLException ex) {
            displayAlert("ERROR: " + ex.getMessage());
        }

        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void buildComboBoxData() {
        try {data.addAll(teamsAdapter.getTeamsNames());}
        catch (SQLException ex) {displayAlert("ERROR: " + ex.getMessage()); }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeTeamBox.setItems(data);
        visitorTeamBox.setItems(data);
    }

    private void displayAlert(String msg) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Alert.fxml"));
            Parent ERROR = loader.load();
            AlertController controller = (AlertController) loader.getController();

            Scene scene = new Scene(ERROR);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.getIcons().add(new Image("file:src/TennisBallGames/WesternLogo.png"));
            controller.setAlertText(msg);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException ex1) {

        }
    }
}




