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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

//Author: Daniel McColm

public class AddScoreController implements Initializable {

    @FXML
    ComboBox matchBox;

    @FXML
    Button cancelBtn;

    @FXML
    TextField homeScore;

    @FXML
    TextField visitorScore;

    final ObservableList<String> data = FXCollections.observableArrayList();
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
        stage.close();
    }

    @FXML
    public void save() {
        try {
            String s = matchBox.getValue().toString();
            String[] arr = s.split("  ");
            System.out.println(Integer.parseInt(arr[0]));
            System.out.println(Integer.parseInt(homeScore.getText()));
            System.out.println(Integer.parseInt(visitorScore.getText()));
            System.out.println(matchesAdapter.getMatchesList().get(Integer.parseInt(arr[0]) - 1).getHomeTeam());
            System.out.println(matchesAdapter.getMatchesList().get(Integer.parseInt(arr[0]) - 1).getVisitorTeam());

            teamsAdapter.setStatus(matchesAdapter.getMatchesList().get(Integer.parseInt(arr[0]) - 1).getHomeTeam(), matchesAdapter.getMatchesList().get(Integer.parseInt(arr[0]) - 1).getVisitorTeam(), Integer.parseInt(homeScore.getText()), Integer.parseInt(visitorScore.getText()));
            matchesAdapter.setTeamsScore(Integer.parseInt(arr[0]),Integer.parseInt(homeScore.getText()),Integer.parseInt(visitorScore.getText()));
        } catch (SQLException ex) {
            displayAlert("ERROR: " + ex.getMessage());
        }

        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void buildComboBoxData() {
        try {data.addAll(matchesAdapter.getMatchesNamesList());}
        catch (SQLException ex) {displayAlert("ERROR: " + ex.getMessage()); }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        matchBox.setItems(data);
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
