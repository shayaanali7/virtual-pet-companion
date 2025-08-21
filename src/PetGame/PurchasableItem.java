package PetGame;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.List;
import java.util.Optional;

/**
 * <b>Represents an item that can be purchased in the game.</b>
 * <p>
 * A purchasable item has a name and a price in coins.
 * Some items may be free (costing 0 coins), while others require the player to spend their in-game coins.
 * </p>
 * 
 * @author Alessia Pilla
 */
public class PurchasableItem {

protected double coinsRequired;
protected String itemName;

    /**
     * Constructs a {@code PurchasableItem} with the specified item name and price in coins
     *
     * @param itemName      The name of the item
     * @param coinsRequired The price of the item in coins
     */
    public PurchasableItem(String itemName, double coinsRequired) {
        this.itemName = itemName;
        this.coinsRequired = coinsRequired;
    }

    /**
     * Gets the name of the item
     *
     * @return The name of the item
     */
    public String getName() {
        return itemName;
    }

    /**
     * Gets the price of the item
     *
     * @return The price of the item in coins
     */
    public double getPrice() {
        return coinsRequired;
    }

    /**
     * Deducts the specified amount of coins from the player's balance if they have enough coins.
     * If the player does not have enough coins, a {@code NotEnoughCoinsException} is thrown indicating that the purchase cannot be completed.
     *
     * @param player The player making the purchase
     * @throws NotEnoughCoinsException If the player does not have enough coins to make the purchase an exception is thrown
     */
    public void deductCoins(Player player) throws NotEnoughCoinsException {
        if (player.getCoins() >= coinsRequired) {
            player.subtractCoins(coinsRequired);
        } 
        else {
            throw new NotEnoughCoinsException("Not enough coins to purchase this item.");
        }
    }
}
