package PetGame;

import java.util.ArrayList;

/**
 * <b>Represents the player's inventory.</b>
 * <p>
 * The inventory stores {@link PurchasableItem} objects that the player has purchased, 
 * and allows for adding and removing them, as well as checking their quantity.
 * </p>
 * 
 * @author Julia Kasperek
 */
public class Inventory {
    // Initializes an empty ArrayList to store PurchasableItem objects.
    public ArrayList<PurchasableItem> items;

    /** 
     * Inventory constructor that initializes the items list.
    */
    public Inventory() {
        this.items = new ArrayList<>();
    }

    /**
     * Inventory constructor that constructs a list with the specified initial items.
     * 
     * @param initialItems A list of items to initialize the inventory with.
     */
    public Inventory(ArrayList<PurchasableItem> initialItems) {
        this.items = new ArrayList<>(initialItems); // Creates a copy to prevent external modifications.
    }

    /**
     * Gets the list of items in the inventory.
     * 
     * @return An ArrayList containing all the items in the inventory.
     */
    public ArrayList<PurchasableItem> getInventoryItems() {
        return items;
    }

    /**
     * Sets the inventory with the given list of items.
     * 
     * @param items The list of items to be stored in the inventory list.
     */
    public void setInventoryItems(ArrayList<PurchasableItem> items) {
        this.items = items;
    }
    /**
     * Adds a new item to the inventory.
     * 
     * @param item The purchasable item to add to the inventory.
     */
    public void addItem(PurchasableItem item) {
        items.add(item);
    }

    /**
     * Removes an item from the inventory.
     * 
     * @param itemName The name of the item to remove from the inventory.
     * @return True if the item was successfully removed; false if the item was not found.
     */
    public boolean removeItem(String itemName) {
        // Iterate through all the items in the inventory.
        for (PurchasableItem item : items) {
            // Check if the item's name matches the provided name.
            if (item.getName().equals(itemName)) {
                // If found remove the item.
                items.remove(item);
                return true; 
            }
        }
        return false; 
    }

    /**
     * Gets the quantity of a specific item in the inventory based on its name.
     * 
     * @param itemName The name of the item to check the quantity for.
     * @return The quantity of the specified item in the inventory.
     */
    public int getItemQuantity(String itemName) {
        int quantity = 0;
        // Iterate through all the items in the inventory.
        for (PurchasableItem item : items) {
            // Check if the item's name matches the provided name.
            if (item.getName().equals(itemName)) {
                // Increment the quantity.
                quantity++;
            }
        }
        return quantity;
    }
}
