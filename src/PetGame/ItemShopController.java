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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * <b>Controller for managing the Item Shop UI.</b>
 * <p>
 * This class handles item purchases, updates the player's coin balance, 
 * and manages interactions within the item shop.
 * </p>
 * 
 * @author Alessia Pilla
 */

public class ItemShopController {

    @FXML private Button witchHatButton, bowButton, crownButton, glassesButton;
    @FXML private Button burgerButton, orangeButton, cakeButton, iceCreamButton, pizzaButton;
    @FXML private Button energyDrinkButton, healingPotionButton;
    @FXML private Button ballButton, trampolineButton;
    @FXML private Button backButton;
    @FXML public Label coinBalanceLabel;
    @FXML private ImageView backgroundImageView;

    public ItemShop itemShop; 
    public Inventory inventory; 
    public Player player;
    private Scene preScene;

    /**
     * Default constructor for the ItemShopController class
     */
    public ItemShopController() {

    }

    /**
     * Sets the player and item shop, initializes the player's inventory, and updates the coin balance display
     *
     * @param player The player whose coins and inventory will be used
     * @param itemShop The item shop used for item purchases
     */
    public void setPlayer(Player player, ItemShop itemShop) {
        this.player = player;
        this.inventory = player.getInventory();
        this.itemShop = itemShop;
        updateCoins();
    }

    /**
     * Handles the purchase of an item based on the button that the player clicks.
     * Updates the coin balance and inventory if the purchase was successful.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void handleItemPurchase(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String itemName = clickedButton.getText();

        PurchasableItem itemToPurchase = getItemFromButtonName(itemName);

        if (itemToPurchase != null && player != null) {
            itemShop.addItemToInventory(itemToPurchase);
            updateCoins();
        }
    }

    /**
     * Updates the displayed coin balance in the UI after earning or spending coins
     */
    public void updateCoins() {
        coinBalanceLabel.setText("Player Coins: " + player.getCoins());
    }

    /**
     * Returns the corresponding {@code PurchasableItem} based on the item name.
     *
     * @param itemName The name of the item as displayed on the button
     * @return The corresponding {@code PurchasableItem}, or {@code null} if not found
     */
    private PurchasableItem getItemFromButtonName(String itemName) {

        // Items available to purchase in the item shop
        switch (itemName) {
            case "Witch Hat":
                return new PurchasableItem("Witch Hat", 50);
            case "Bow":
                return new PurchasableItem("Bow", 0);  // Free item
            case "Crown":
                return new PurchasableItem("Crown", 100);
            case "Burger":
                return new PurchasableItem("Burger", 25);
            case "Orange":
                return new PurchasableItem("Orange", 0);  // Free item
            case "Cake":
                return new PurchasableItem("Cake", 15);
            case "Energy Drink":
                return new PurchasableItem("Energy Drink", 60);
            case "Ball":
                return new PurchasableItem("Ball", 20);
            case "Trampoline":
                return new PurchasableItem("Trampoline", 150);
            case "Healing Potion":
                return new PurchasableItem("Healing Potion", 120);
            default:
                return null;
        }
    }

    /**
     * Sets the previous scene to where the game should return when the back button is pressed.
     *
     * @param preScene The previous scene before entering the shop (in this case it will return to the main game screen)
     */
    @FXML
    public void setPreScene(Scene preScene) {
        this.preScene = preScene;
    }

    /**
     * Returns the previous scene before entering the shop.
     *
     * @param preScene The previous scene before entering the shop (in this case it will return the main game screen)
     */
    public Scene getPreScene() {
        return preScene;
    }

    /**
     * Handles the back button action to return to the previous scene (the main game screen)
     * Updates the player's coin balance and switches back to the previous scene.
     *
     * @param event The ActionEvent triggered by the back button click
     */
    @FXML
    private void goBackToGame(ActionEvent event) {
        // Update the player's coin balance before returning to the game screen
        Game.getInstance().updateCoins();

        // Get the current stage (window) where the event was triggered and set it 
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(preScene);
        stage.show();
    }

    /**
     * Initializes the controller by setting the background image and adjusting its opacity for a nice visual
     */
    public void initialize() {
        // Set the background image
        Image backgroundImage = new Image(getClass().getResource("/images/inventory.png").toExternalForm());
        backgroundImageView.setImage(backgroundImage);  // Set the image to ImageView
        
        // Set the transparency (opacity) of the background image
        backgroundImageView.setOpacity(0.3);  // 0 is fully transparent, 1 is fully opaque
    }
}
