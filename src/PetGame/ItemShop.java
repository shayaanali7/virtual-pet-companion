package PetGame;

import javafx.stage.Stage;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * <b>Represents the in-game item shop where players can purchase items.</b>
 * <p>
 * The shop allows players to buy items if they have enough coins and adds
 * the items to their inventory. These items can be used to improve their pet's stats.
 * </p>
 *
 * @author Alessia Pilla
 */
public class ItemShop {
private Stage stage;
private Inventory inventory;
private double userCoins; 
private Player player;

    /**
     * Constructs the Item Shop with the specified stage, inventory, and user coins from the player.
     *
     * @param stage      The primary stage of the game
     * @param inventory  The player's inventory
     * @param userCoins  The amount of coins the player has
     * @param player     The player making purchases
     */
    public ItemShop(Stage stage, Inventory inventory, double userCoins, Player player) {
        this.stage = stage;
        this.inventory = inventory;
        this.userCoins = userCoins;
        this.player = player;
    }

    /**
     * Opens the shop and displays a welcome message to the terminal
     */
    public void openShop() {
        System.out.println("Welcome to the Item Shop!");
    }

    /**
     * Attempts to add a purchasable item to the player's inventory.
     * <p>
     * - If the item is free, it is added immediately. 
     * - If the player has enough coins, the item is purchased, and the coins are deducted. 
     * - If the player lacks sufficient funds, an alert is displayed.
     * </p>
     *
     * @param item The purchasable item to be added to the player's inventory
     */
    public void addItemToInventory(PurchasableItem item) {
       // Check if the item is an instance of PurchasableItem
        if (item instanceof PurchasableItem) {
        PurchasableItem purchasableItem = (PurchasableItem) item;  // Cast item to PurchasableItem

        // If the item is free (price is 0)
        if (purchasableItem.getPrice() == 0) {
            handleItemChoice(purchasableItem);  // Add item to inventory without deducting coins
        } 
        // If the player has enough coins to purchase the item
        else if (player.getCoins() >= purchasableItem.getPrice()) {
            player.subtractCoins(purchasableItem.getPrice());  // Deduct the item price from the player's coins
            System.out.println(purchasableItem.getName() + " purchased for " + purchasableItem.getPrice() + " coins.");
            handleItemChoice(purchasableItem);  // Add the purchased item to the player's inventory
        } 
        // If the player doesn't have enough coins
        else {
            // Display an alert indicating the purchase failed due to insufficient coins
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Purchase Failed");
            alert.setHeaderText("Not Enough Coins");
            alert.setContentText("You don't have enough coins to purchase " + purchasableItem.getName() + ".");

            ButtonType okButton = new ButtonType("OK");
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait();  // Show the alert to the player
        }
    } 
    // If the item is not a PurchasableItem (free), add directly to inventory
    else {
        handleItemChoice(item); 
    }
    }
    
    /**
     * Adds the specified item to the player's inventory and displays a confirmation alert
     *
     * @param item The purchasable item to be added to the player's inventory
     */
    public void handleItemChoice(PurchasableItem item) {
        // Add item to inventory
        inventory.addItem(item);

        // Confirmation message of purchase
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Item Added");
        alert.setHeaderText(null);
        alert.setContentText(item.getName() + " has been added to your inventory.");

        ButtonType okButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okButton);
        alert.showAndWait();
    }
}

