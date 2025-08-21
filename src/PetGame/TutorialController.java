package PetGame;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * <p>The TutorialController class manages the tutorial screen of the game.
 * It provides guidance to players on how to navigate the game and take care of their pets.
 * 
 * Features include:
 * - Displaying a detailed tutorial in a TextArea.
 * - Navigating back to the previous scene.</p>
 * 
 * @author Ishaan Misra
 */
public class TutorialController {

    @FXML private Button goBack; // Button to navigate back to the previous scene
    @FXML private TextArea textArea; // TextArea to display the tutorial content

    private Scene preScene; // Stores the previous scene to return to

    /**
     * Default constructor for the TutorialController.
     */
    public TutorialController() {
        // No initialization required in the constructor
    }

    /**
     * Initializes the controller and sets the tutorial text in the TextArea.
     */
    public void initialize() {
        // Setting the tutorial text to guide the player
        textArea.setText(
            "Here’s a guide to help you navigate the game and ensure your pet stays happy and healthy!!!\n" + 
            "\n" + 
            "Once you start the game, you'll be prompted to choose a pet. Your options are:\n" + 
            "- Energetic: High stamina, loves to play.\n" + 
            "- Balanced: A mix of traits that offer a good balance of playfulness and care.\n" + 
            "- Lazy: Needs more attention but becomes incredibly loyal.\n" +
            "\n" + 
            "After choosing and naming your pet, you'll be brought to the Game Screen, where you can begin taking care of your pet!\n" + 
            "\n" +
            "GAME PLAY:\n" + 
            "\n" + 
            "Player Score and Coins:\n" + 
            "Each game begins with 10 coins. For every 100 points you score, you'll earn an additional 25 coins. Keep track of your score and coin balance, as they’re essential for buying items in the game!\n" + 
            "\n" + 
            "Item Shop:\n" + 
            "Visit the Item Shop to buy food, gifts, and other items for your pet. Each item in the shop has a price and shows how it benefits your pet (e.g., boosting happiness or health). You can only buy items if you have enough coins, so manage your balance wisely!\n" + 
            "\n" + 
            "Inventory:\n" + 
            "Your Inventory stores all the items you’ve purchased. To use them, simply go to your inventory and select the item you want. Keep an eye on your inventory and use items strategically to keep your pet healthy and happy.\n" + 
            "\n" + 
            "Textbox for Feedback:\n" + 
            "The Textbox provides important updates about your pet’s status and actions. It informs you when actions like feeding or playing are available or on cooldown and displays the results of your actions (e.g., how much happiness or health your pet gains). If you try an invalid action, the textbox will notify you. Keep an eye on this box to stay informed!\n" + 
            "\n" + 
            "Pet Statistics:\n" +
            "The bars on the Game Screen represent your pet’s important stats, including Health, Fullness, Happiness, and Sleep. Keeping these bars in the green means your pet is happy and healthy!\n" + 
            "\n" + 
            "If you need more details on how these stats work and what happens when they get too low, check out the Help section for a full breakdown of game mechanics and tips!\n" +
            "\n" +
            "Save and Load Game:\n" + 
            "Click Save Game to save your progress. Later, return to the main menu and select Load Game to pick up right where you left off. This way, you can continue your adventure without losing progress!"
        );
    }

    /**
     * Sets the previous scene to allow navigation back to it.
     * 
     * @param preScene The previous scene to return to.
     */
    @FXML
    public void setPreScene(Scene preScene) {
        this.preScene = preScene; // Assign the provided scene to the preScene variable
    }

    /**
     * Retrieves the previous scene.
     * 
     * @param preScene The previous scene to retrieve.
     */
    public void getPreScene(Scene preScene) {
        this.preScene = preScene; // Assign the provided scene to the preScene variable
    }

    /**
     * Handles the action of the "Go Back" button.
     * Navigates back to the previous scene.
     * 
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void goBack(ActionEvent event) {
        // Retrieve the current stage and set the scene back to the previous one
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(preScene); // Set the previous scene
        stage.show(); // Display the updated stage
    } 
}

