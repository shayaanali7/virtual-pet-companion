package PetGame;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>The LoadGame class handles the functionality for loading saved game files.
 * It dynamically creates buttons for each save file, displays their details, 
 * and allows the player to load a selected save file into the game.
 * 
 * This class extends the MainController class and provides methods for 
 * initializing the save file buttons, loading game data, and transitioning 
 * back to the main menu.
 * 
 * Features:
 * - Reads save files in JSON format.
 * - Displays save file details such as pet type, health, sleep, and state.
 * - Allows the player to load a save file and resume the game.
 * 
 * Dependencies:
 * - Gson library for parsing JSON save files.
 * - JavaFX for UI components and scene transitions.</p>
 * 
 * @author Novak Vukojicic
 */
public class LoadGame extends MainController {

    @FXML
    private VBox saveFilesContainer; // Container to hold dynamically created save file buttons
    public Game gameClass;

    /**
     * Initializes the save file buttons by creating a button for each predefined save file.
     * This method is automatically called after the FXML file is loaded.
     */
    public void initialize() {
        createSaveFileButton("save1.json");
        createSaveFileButton("save2.json");
        createSaveFileButton("save3.json");
        createSaveFileButton("save4.json");
        createSaveFileButton("save5.json");
    }

    /**
     * Creates a button for a save file and adds it to the saveFilesContainer.
     * If the save file exists and contains valid data, the button displays the save details.
     * If the save file is empty or invalid, the button indicates that the save file is empty.
     * 
     * @param filename The name of the save file to create a button for.
     */
    private void createSaveFileButton(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            Gson gson = new Gson();
            Map<String, Object> saveData = gson.fromJson(reader, Map.class);

            // Extract save file details for the petType and pet statistics
            int petType = ((Double) saveData.get("petType")).intValue();
            Map<String, Object> petStats = (Map<String, Object>) saveData.get("petStats");
            int health = ((Double) petStats.get("health")).intValue();
            int sleep = ((Double) petStats.get("sleep")).intValue();
            String state = (String) petStats.get("state");

            // Create a button with save file details
            Button saveButton = new Button("Pet Type: " + petType + ", Health: " + health + ", Sleep: " + sleep + ", State: " + state);
            saveButton.setPrefWidth(600);
            saveButton.setOnAction(event -> loadGame(event, filename));

            saveFilesContainer.getChildren().add(saveButton); // Add the button to the container
        } catch (IOException e) {
            // Create a button for an empty or invalid save file
            Button saveButton = new Button("Empty save file for file: " + filename);
            saveButton.setPrefWidth(600);

            saveFilesContainer.getChildren().add(saveButton); // Add the button to the container
        }
    }

    /**
     * Loads the game data from the specified save file and transitions to the game screen.
     * Displays a confirmation dialog before loading the save file.
     * 
     * @param event The ActionEvent triggered by clicking a save file button.
     * @param filename The name of the save file to load.
     */
    public void loadGame(ActionEvent event, String filename) {
        System.out.println("Loading game from: " + filename);
        try (FileReader reader = new FileReader(filename)) {
            Gson gson = new Gson();
            Map<String, Object> saveData = gson.fromJson(reader, Map.class);

            // Extract save file details for the petType and pet statistics
            int petType = ((Double) saveData.get("petType")).intValue();
            String petName = (String) saveData.get("petName");
            Map<String, Object> petStats = (Map<String, Object>) saveData.get("petStats");
            Map<String, Object> player = (Map<String, Object>) saveData.get("player");
            Map<String, Object> gameStats = (Map<String, Object>) saveData.get("gameStats");

            // Show confirmation dialog
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Game Save Selection");
            alert.setHeaderText("");
            alert.setContentText("Do you want to load this save file?");
            
            if (alert.showAndWait().get() == ButtonType.OK) {
                // Load the game screen
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Files/Game.fxml"));
                Parent root = loader.load();

                // Pass save data to the game controller
                this.gameClass = loader.getController();
                gameClass.startGame(event, petType, petName);

                // Set pet stats
                gameClass.pet.getPetStats().health.set(((Double) petStats.get("health")).intValue());
                gameClass.pet.getPetStats().sleep.set(((Double) petStats.get("sleep")).intValue());
                gameClass.pet.getPetStats().fullness.set(((Double) petStats.get("fullness")).intValue());
                gameClass.pet.getPetStats().happiness.set(((Double) petStats.get("happiness")).intValue());
                gameClass.pet.getPetStats().state.set((String) petStats.get("state"));

                // Set player stats
                gameClass.player.setCoins(((Double) player.get("money")).intValue());
                gameClass.player.getScore().increaseScore((((Double) player.get("score")).intValue()));
                gameClass.setScore();
                
                // Set inventory
                ArrayList<PurchasableItem> inventory = gson.fromJson(gson.toJson(player.get("inventory")), new TypeToken<List<PurchasableItem>>() {}.getType());
                gameClass.player.inventory.items = inventory;
                
                // Transition to the game screen
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);

                // Add key press handler for the game screen
                scene.setOnKeyPressed(eventKey -> {
                    try {
                        gameClass.handleKeyPress(eventKey.getCode(), event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                stage.setScene(scene);
                stage.show();
            }

            System.out.println("Game loaded successfully from: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches back to the main menu screen.
     * This method is triggered by the "Back to Main Menu" button.
     */
    @FXML
    private void switchToMainMenu() {
        // Logic to switch back to the main menu
        System.out.println("Switching to Main Menu...");
    }
}
