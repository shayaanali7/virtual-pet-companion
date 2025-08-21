package PetGame.JUnit_Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import PetGame.Inventory;
import PetGame.PurchasableItem;

public class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    public void setUp() {
        // Initialize a new Inventory before each test
        inventory = new Inventory();
    }

    @Test
    public void testAddItem() {
        // Create a PurchasableItem to add
        PurchasableItem item = new PurchasableItem("Toy", 5);

        // Add the item to the inventory
        inventory.addItem(item);

        // Verify that the item was added
        assertEquals(1, inventory.getInventoryItems().size(), "Inventory should contain 1 item.");
        assertEquals(item, inventory.getInventoryItems().get(0), "The item added should be the same as the retrieved item.");
    }

    @Test
    public void testRemoveItem() {
        // Create a PurchasableItem and add it to the inventory
        PurchasableItem item = new PurchasableItem("Toy", 5);
        inventory.addItem(item);

        // Remove the item from the inventory
        boolean result = inventory.removeItem("Toy");

        // Verify that the item was removed and the method returned true
        assertTrue(result, "The item should be removed successfully.");
        assertEquals(0, inventory.getInventoryItems().size(), "Inventory should be empty after removal.");
    }

    @Test
    public void testRemoveItemNotFound() {
        // Attempt to remove an item that doesn't exist in the inventory
        boolean result = inventory.removeItem("NonexistentItem");

        // Verify that the method returns false when the item is not found
        assertFalse(result, "The method should return false when the item is not found.");
    }

    @Test
    public void testGetItemQuantity() {
        // Create multiple instances of the same item and add them to the inventory
        PurchasableItem item1 = new PurchasableItem("Toy", 5);
        PurchasableItem item2 = new PurchasableItem("Toy", 5);
        inventory.addItem(item1);
        inventory.addItem(item2);

        // Verify that the quantity of the item is 2
        int quantity = inventory.getItemQuantity("Toy");
        assertEquals(2, quantity, "The quantity of 'Toy' should be 2.");
    }

    @Test
    public void testGetItemQuantityNotFound() {
        // Verify that the quantity of a non-existent item is 0
        int quantity = inventory.getItemQuantity("NonexistentItem");
        assertEquals(0, quantity, "The quantity of a non-existent item should be 0.");
    }

    @Test
    public void testConstructorWithInitialItems() {
        // Create a list of items to initialize the inventory
        ArrayList<PurchasableItem> initialItems = new ArrayList<>();
        initialItems.add(new PurchasableItem("Toy", 5));
        initialItems.add(new PurchasableItem("Food", 3));

        // Create an Inventory with initial items
        Inventory newInventory = new Inventory(initialItems);

        // Verify that the inventory contains the correct number of items
        assertEquals(2, newInventory.getInventoryItems().size(), "The inventory should contain 2 items.");
        assertEquals("Toy", newInventory.getInventoryItems().get(0).getName(), "The first item should be 'Toy'.");
        assertEquals("Food", newInventory.getInventoryItems().get(1).getName(), "The second item should be 'Food'.");
    }
}
