package PetGame;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * The {@code RevivePet} class allows the user to revive a previously saved pet by restoring
 * its stats to maximum values and setting its state to "Normal". This feature is accessible
 * through the parental controls menu
 * <p>
 * It loads and displays available save files (save1.json to save5.json),
 * and provides an option to revive each pet with a button.
 * </p>
 *
 * @author Ishaan Misra
 */
public class RevivePet extends MainController {

    /** VBox container that holds the list of save files for pet revival. */
    @FXML
    private VBox saveFileContainer;
    
    /**
     * Initializes the revive screen by attempting to read and display all 5 save files.
     * For each valid file, it creates a UI section with the pet's current stats and a "Revive" button.
     * If a save file is missing or invalid, it displays a placeholder label.
     */
    @FXML
    public void initialize() {
        Gson gson = new Gson();

        for(int i = 1; i <= 5; i++) { /** Going through the save files */
            String filename = "save" + i + ".json";
            try(FileReader reader = new FileReader(filename)) {
                Map<String, Object> saveData = gson.fromJson(reader, Map.class);

                String petName = (String) saveData.getOrDefault("petName", "Unnamed");
                Map<String, Object> petStats = (Map<String, Object>) saveData.get("petStats");

                /** Getting the pets stats and state */
                int health = ((Double) petStats.get("health")).intValue();
                int sleep = ((Double) petStats.get("sleep")).intValue();
                int fullness = ((Double) petStats.get("fullness")).intValue();
                int happiness = ((Double) petStats.get("happiness")).intValue();
                String state = (String) petStats.get("state");

                String titleLine = String.format("Revive \"%s\" (%s)", petName, filename);
                String statsLine = String.format("Health: %d | Sleep: %d | Fullness: %d | Happiness: %d | State: %s", health, sleep, fullness, happiness, state);

                VBox buttonBox = new VBox(2); /** 2px spacing */
                buttonBox.setPrefWidth(600);
                buttonBox.setStyle("-fx-alignment: center;");

                Label title = new Label(titleLine);
                title.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: center;");

                Label stats = new Label(statsLine);
                stats.setStyle("-fx-font-size: 12;");

                Button reviveBtn = new Button("Revive");
                reviveBtn.setOnAction(e -> reviveSaveFile(filename));

                buttonBox.getChildren().addAll(title, stats, reviveBtn);
                saveFileContainer.getChildren().add(buttonBox);

            } 
            catch (IOException e) {
                VBox errorBox = new VBox();
                errorBox.setPrefWidth(600);
                errorBox.setStyle("-fx-alignment: center;");

                Label errorLabel = new Label("Revive " + filename + " (Empty or Invalid Save)");
                errorLabel.setStyle("-fx-font-style: italic; -fx-opacity: 0.7;");
                errorBox.getChildren().add(errorLabel);

                saveFileContainer.getChildren().add(errorBox);
            }
        }
    }


    /**
     * Revives a pet by restoring its stats to their maximum values and changing its state to "Normal".
     * The updated data is saved back into the specified file.
     *
     * @param filename The name of the save file to revive
     */
    private void reviveSaveFile(String filename) {
        try(FileReader reader = new FileReader(filename)) {
            Gson gson = new Gson();
            Map<String, Object> saveData = gson.fromJson(reader, Map.class);

            /** Resetting the stats to max and state to normal */
            Map<String, Object> petStats = (Map<String, Object>) saveData.get("petStats");
            petStats.put("health", 100);
            petStats.put("sleep", 100);
            petStats.put("fullness", 100);
            petStats.put("happiness", 100);
            petStats.put("state", "Normal");

            try(FileWriter writer = new FileWriter(filename)) {
                gson.toJson(saveData, writer);
            }

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Pet Revived");
            alert.setHeaderText(null);
            alert.setContentText("Your pet in " + filename + " has been revived!\nAll the stats have been reset to maximum values. \nThe state of your pet is now Normal.");
            alert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the back button action and switches to the parental controls screen.
     *
     * @param event the action event triggered by the back button
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void goBack(javafx.event.ActionEvent event) throws IOException {
        switchToParentalControls(event);
    }
}
