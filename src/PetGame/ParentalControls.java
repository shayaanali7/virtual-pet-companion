package PetGame;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The {@code ParentalControls} class handles access to parental control features,
 * including navigation to limitation settings, playtime statistics, and reviving pets.
 * <p>
 * This class includes UI controls for managing playtime restrictions and session tracking.
 * </p>
 *
 * @author Ishaan Misra
 */
public class ParentalControls extends MainController {

    /** Text field for entering the parental control password. */
    @FXML private TextField passwordField;

    /** Button to submit the parental control password. */
    @FXML private Button passwordSubmitBtn;

    /** Pane containing the playtime limitation controls. */
    @FXML private AnchorPane limitationsPane;

    /** Pane displaying playtime statistics. */
    @FXML private AnchorPane statisticsPane;

    /** Checkbox to enable or disable playtime restrictions. */
    @FXML private CheckBox playtimeCheckbox;

    /** Label displaying total playtime in the statistics pane. */
    @FXML private Label totalPlaytimeLabel;

    /** Label displaying average playtime per session in the statistics pane. */
    @FXML private Label averagePlaytimeLabel;

    /**
     * Flag to track if access to parental controls has been granted.
     */
    public boolean accessGranted = false;

    /**
     * Total playtime accumulated in hours (used for display or session calculation).
     */
    public int totalPlaytime = 0;

    /**
     * The total number of play sessions
     */
    public int playSessions = 0;

    /**
     * Switches the view to the Limitations screen where playtime restrictions can be set.
     *
     * @param event the ActionEvent triggered by the Limitations button
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void switchToLimitationsScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Files/Limitations.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Switches the view to the Statistics screen to show total and average playtime.
     * Loads the persisted statistics before switching scenes.
     *
     * @param event the ActionEvent triggered by the Statistics button
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void switchToStatisticsScreen(ActionEvent event) throws IOException {
        Statistics.loadStatistics();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Files/Statistics.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Switches the view to the Revive Pet screen where users can restore a pet’s health and state.
     *
     * @param event the ActionEvent triggered by the Revive button
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void switchToRevivePetScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Files/RevivePet.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Adds a new play session of a specified number of hours to the total playtime count.
     *
     * @param hours the number of hours played in the session
     */
    public void addPlaySession(int hours) {
        totalPlaytime += hours;
        playSessions++;
    }
} 
