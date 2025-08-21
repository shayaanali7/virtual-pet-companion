package PetGame;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * <b>Manages the player's inventory interface.</b>
 * <p>
 * This class allows the player to use items, and navigate back to the game scene.
 * </p>
 * 
 * @author Julia Kasperek
 */

public class InventoryController extends MainController {
    @FXML
    private Scene preScene;

    @FXML private Button gameButton, useBallButton, useBowButton, useCrownButton, useTrampolineButton, useHatButton;
    @FXML private Button useBurgerButton, useCakeButton, useEnergyDrinkButton, useHealingPotionButton, useOrangeButton;
    @FXML private Label burgerLabel, orangeLabel, drinkLabel, potionLabel, cakeLabel;
    @FXML private Label bowLabel, ballLabel, trampolineLabel, crownLabel, hatLabel;
    @FXML private ImageView backgroundImageView;

    @FXML
    private Label popupLabel;

    public Player player;
    public Game game = Game.getInstance();
    public PetCommands commands = game.petCommand;

    /**
     * Sets the player instance for this controller and updates inventory labels.
     * 
     * @param player The player whose inventory will be managed.
     */
    public void setPlayer(Player player) {
        this.player = player;
        updateInventoryLabels();
    }

    /**
     * Updates the UI labels to reflect the current invenoty quantities.
     */
    private void updateInventoryLabels() {
        burgerLabel.setText("Burger: " + player.getInventory().getItemQuantity("Burger"));
        orangeLabel.setText("Orange: " + player.getInventory().getItemQuantity("Orange"));
        bowLabel.setText("Bow: " + player.getInventory().getItemQuantity("Bow"));
        ballLabel.setText("Ball: " + player.getInventory().getItemQuantity("Ball"));
        cakeLabel.setText("Cake: " + player.getInventory().getItemQuantity("Cake"));
        crownLabel.setText("Crown: " + player.getInventory().getItemQuantity("Crown"));
        trampolineLabel.setText("Trampoline: " + player.getInventory().getItemQuantity("Trampoline"));
        hatLabel.setText("Witch Hat: " + player.getInventory().getItemQuantity("Witch Hat"));
        drinkLabel.setText("Energy Drink: " + player.getInventory().getItemQuantity("Energy Drink"));
        potionLabel.setText("Healing Potion: " + player.getInventory().getItemQuantity("Healing Potion"));
    }

    /**
     * Handles an even of using an item, updating the player's status and inventory accordingly.
     * 
     * @param e The action event triggered by clicking an item button.
     */
    public void useItem(ActionEvent e) {
        // Identify which button was clicked.
        String button = ((Button) e.getSource()).getId();
        String itemName = "";
        int statIncrease = 0;
        String statType = "";

        // Map button IDs to item attributes.
        switch (button) {
            case "useBallButton":
                itemName = "Ball";
                statType = "Happiness";
                statIncrease = 10;
                break;
            case "useBowButton":
                itemName = "Bow";
                statType = "Happiness";
                statIncrease = 8;
                break;
            case "useBurgerButton":
                itemName = "Burger";
                statType = "Fullness";
                statIncrease = 15;
                break;
            case "useCakeButton":
                itemName = "Cake";
                statType = "Fullness";
                statIncrease = 10;
                break;
            case "useCrownButton":
                itemName = "Crown";
                statType = "Happiness";
                statIncrease = 15;
                break;
            case "useEnergyDrinkButton":
                itemName = "Energy Drink";
                statType = "Fullness";
                statIncrease = 45;
                break;
            case "useHealingPotionButton":
                itemName = "Healing Potion";
                statType = "Health";
                statIncrease = 200;
                break;
            case "useOrangeButton":
                itemName = "Orange";
                statType = "Fullness";
                statIncrease = 3;
                break;
            case "useTrampolineButton":
                itemName = "Trampoline";
                statType = "Happiness";
                statIncrease = 40;
                break;
            case "useHatButton":
                itemName = "Witch Hat";
                statType = "Happiness";
                statIncrease = 15;
                break;
            default:
                return;
        }


        if (player.getInventory().removeItem(itemName)) {
            // Update pet stats
            switch (statType) {
                case "Happiness":
                    player.getScore().increaseScore(15);
                    commands.changeToHappy();
                    game.statusTextArea.appendText("Pet received a gift!" + "\n");
                    commands.startCooldown("gift");
                    game.pet.getPetStats().updateStatistics(0, 0, 0, statIncrease);
                    break;
                case "Fullness":
                    player.getScore().increaseScore(20);
                    commands.changeToHappy();
                    game.statusTextArea.appendText("Pet has been fed!" + "\n");
                    commands.startCooldown("feed");
                    game.pet.getPetStats().updateStatistics(0, 0, statIncrease, 0);
                    break;
                case "Health":
                    game.pet.getPetStats().updateStatistics(statIncrease, 0, 0, 0);
                    break;
            }

            // Update inventory labels
            updateInventoryLabels();

            // Show the pop-up
            showPopup(itemName, statType, statIncrease);
        }
        
    }

    /**
     * Displays a pop-up message when an item is used.
     * 
     * @param itemName The name of the used item
     * @param statType The type of stat affected
     * @param statIncrease The amount by which the stat is increased
     */
    private void showPopup(String itemName, String statType, int statIncrease) {
        popupLabel.setText(itemName + " used! " + statType + " increased by " + statIncrease);
        popupLabel.setVisible(true);

        // Hide the pop-up after 2 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> popupLabel.setVisible(false)));
        timeline.setCycleCount(1);
        timeline.play();
    }

    /**
     * Sets the previous scene reference.
     * 
     * @param preScene The previous scene before entering the inventory (in this case it will return the main game screen)
     */
    public void setPreScene(Scene preScene) {
        this.preScene = preScene;
    }

    /**
     * Gets the previous scene before entering the inventory.
     * 
     * @param preScene The previous scene before entering the inventory (in this case it will return the main game screen)
     */
    public void getPreScene(Scene preScene) {
        this.preScene = preScene;
    }

    /**
     * Navigates back to the previous game scene.
     * 
     * @param event The action event triggered by clicking the back button
     */
    @FXML
    private void goBackToGame(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(preScene);
        stage.show();
    }

    /**
     * Initializes the invenotry scene, setting up the background image and its transparency.
     */
    public void initialize() {
        // Set the background image
        Image backgroundImage = new Image(getClass().getResource("/images/inventory.png").toExternalForm());
        backgroundImageView.setImage(backgroundImage);  // Set the image to ImageView
        
        // Set the transparency of the background image
        backgroundImageView.setOpacity(0.3);  // 0 is fully transparent, 1 is fully opaque
    }
}


