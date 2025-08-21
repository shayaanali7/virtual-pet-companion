package PetGame.JUnit_Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import PetGame.Player;
import PetGame.Inventory;
import PetGame.PlayerScore;

public class PlayerTest {

    private Player player;

    @BeforeEach
    public void setUp() {
        // Initialize a new Player before each test with an initial coin balance of 100
        player = new Player(100);
    }

    @Test
    public void testAddCoins() {
        // Add coins to the player's balance
        player.addCoins(50);

        // Verify that the player's coin balance is updated
        assertEquals(150, player.getCoins(), "The player's coin balance should be 150 after adding 50 coins.");
    }

    @Test
    public void testSubtractCoins() {
        // Subtract coins from the player's balance
        player.subtractCoins(30);

        // Verify that the player's coin balance is updated
        assertEquals(70, player.getCoins(), "The player's coin balance should be 70 after subtracting 30 coins.");
    }

    @Test
    public void testSubtractCoinsNotEnough() {
        // Try to subtract more coins than the player has
        player.subtractCoins(200);

        // Verify that the player's coin balance doesn't change
        assertEquals(100, player.getCoins(), "The player's coin balance should remain 100 after trying to subtract 200 coins.");
    }

    @Test
    public void testSetCoins() {
        // Set the player's coin balance to a specific value
        player.setCoins(500);

        // Verify that the player's coin balance is updated
        assertEquals(500, player.getCoins(), "The player's coin balance should be set to 500.");
    }

    @Test
    public void testGetScore() {
        // Retrieve the player's score and verify that it is initialized correctly
        PlayerScore score = player.getScore();

        // Verify that the score is not null and its initial value is 0 (assuming default constructor sets it to 0)
        assertNotNull(score, "The player's score object should not be null.");
        assertEquals(0, score.getScore(), "The player's initial score should be 0.");
    }

    @Test
    public void testGetInventory() {
        // Retrieve the player's inventory and verify that it is initialized correctly
        Inventory inventory = player.getInventory();

        // Verify that the inventory is not null
        assertNotNull(inventory, "The player's inventory object should not be null.");

        // Verify that the inventory starts empty
        assertEquals(0, inventory.getInventoryItems().size(), "The player's inventory should be empty initially.");
    }
}
