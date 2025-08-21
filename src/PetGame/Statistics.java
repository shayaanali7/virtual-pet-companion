package PetGame;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * The {@code Statistics} class handles the tracking, displaying, and resetting of
 * the players playtime statistics specifically the total playtime and average playtime.
 * It also provides UI updates for the Statistics screen in the game.
 * <p>
 * Data is persisted across sessions using a `stats.json` file.
 * </p>
 *
 * @author Ishaan Misra
 */
public class Statistics extends MainController {

    /** Total playtime in minutes across all sessions. */
    public static int totalPlaytime = 0;

    /** Number of total play sessions. */
    public static int playSessions = 0;

    /** Seconds played during the current session. */
    public static int secondsPlayed = 0;

    /** Label displaying total playtime on the statistics screen. */
    @FXML
    private Label totalPlaytimeLabel;

    /** Label displaying average playtime per session on the statistics screen. */
    @FXML
    private Label averagePlaytimeLabel;


    /**
     * Switches to the statistics screen.
     * This method is triggered when the "Statistics" button is clicked.
     * @param event the ActionEvent triggered by the button press
     * @throws IOException if the FXML file cannot be loaded
     */
    public void switchToStatisticsScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Files/Statistics.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Returns to the parental controls screen from the statistics screen
     * @param event the ActionEvent triggered by the button press
     * @throws IOException if the FXML file cannot be loaded
     */
    public void switchToParentalControls(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Files/ParentalControls.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles resetting the global playtime statistics.
     * This method clears all saved values and updates the UI labels accordingly.
     *
     * @param event the ActionEvent triggered by the reset button
     */
    public void handleResetStats(ActionEvent event) {
        /** Reset the static fields */
        totalPlaytime = 0;
        playSessions = 0;
        secondsPlayed = 0;

        saveStatistics();

        /** Updating the UI */
        Label totalPlaytimeLabel = (Label) ((Node) event.getSource()).getScene().lookup("#totalPlaytimeLabel");
        Label averagePlaytimeLabel = (Label) ((Node) event.getSource()).getScene().lookup("#averagePlaytimeLabel");

        if(totalPlaytimeLabel != null && averagePlaytimeLabel != null) {
            totalPlaytimeLabel.setText("0 Minutes");
            averagePlaytimeLabel.setText("0 Minutes");
        }
        System.out.println("Playtime statistics have been reset.");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Stats Reset");
        alert.setHeaderText(null);
        alert.setContentText("Playtime statistics have been reset.");
        alert.showAndWait();
    }

    /**
     * Handles the Back button on the Statistics screen.
     * @param event the ActionEvent triggered by the button press
     * @throws IOException if the FXML file cannot be loaded
     */
    public void handleBack(ActionEvent event) throws IOException {
        switchToParentalControls(event);
    }

    /**
     * Updates the text displayed in the statistics labels based on current values.
     *
     * @param totalLabel the label for total playtime
     * @param avgLabel the label for average playtime
     */
    public void updateStatsDisplay(Label totalLabel, Label avgLabel) {
        int totalMinutes = secondsPlayed / 60;
        int avgMinutes = (playSessions == 0) ? 0 : (totalMinutes / playSessions);
    
        totalLabel.setText(totalMinutes + " minutes");
        avgLabel.setText(avgMinutes + " minutes");
    }

    /**
     * Initializes the statistics screen, loading statistics from file and updating labels.
     */
    @FXML
    public void initialize() {
        int totalMinutes = totalPlaytime;
        int avgMinutes = (playSessions <= 0) ? 0 : (totalMinutes / playSessions);

        if(totalPlaytimeLabel != null) {
            totalPlaytimeLabel.setText(totalMinutes + " minutes");
        }

        if(averagePlaytimeLabel != null) {
            averagePlaytimeLabel.setText(avgMinutes + " minutes");
        }
    }

    /**
     * Saves the current statistics to the `stats.json` file for persistence across sessions.
     */
    public static void saveStatistics() {
        try(java.io.FileWriter writer = new java.io.FileWriter("stats.json")) {
            com.google.gson.Gson gson = new com.google.gson.Gson();
            Map<String, Integer> stats = new HashMap<>();
            stats.put("totalPlaytime", totalPlaytime);
            stats.put("playSessions", playSessions);
            stats.put("secondsPlayed", secondsPlayed);
            gson.toJson(stats, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads saved statistics from the `stats.json` file.
     * If the file does not exist or cannot be read, default values remain.
     */
    public static void loadStatistics() {
        try(java.io.FileReader reader = new java.io.FileReader("stats.json")) {
            com.google.gson.Gson gson = new com.google.gson.Gson();
            Map<String, Double> stats = gson.fromJson(reader, Map.class);
            totalPlaytime = stats.get("totalPlaytime").intValue();
            playSessions = stats.get("playSessions").intValue();
            secondsPlayed = stats.get("secondsPlayed").intValue();
        } 
        catch (IOException e) {
            /**File might not exist the first time running */
        }
    }

}