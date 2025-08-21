package PetGame.JUnit_Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import PetGame.Inventory;
import PetGame.ItemShop;
import PetGame.Player;
import PetGame.PurchasableItem;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ItemShopTest {
	private Stage stage;
	private Inventory inventory;
	private Player player;
	
	@BeforeEach
	public void setUp() throws Exception {
		new JFXPanel();
		
		CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
        	stage = new Stage();
        	inventory = new Inventory();
        	player = new Player(40);
        	
        	latch.countDown();  
        });
        latch.await();  
    }
	
	@Test
	public void openShop() {
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
        	ItemShop shop = new ItemShop(stage, inventory, 10, player);
            shop.openShop();

            String output = outputStream.toString().trim();

            assertEquals("Welcome to the Item Shop!", output);
        } finally {
            System.setOut(originalOut);
        }
	}
	
	@Test 
	public void testAddFreeItem() throws InterruptedException {
		PurchasableItem item = new PurchasableItem("Cake", 0);
		ItemShop shop = new ItemShop(stage, inventory, 40, player);
		
		Platform.runLater(() -> {
            shop.addItemToInventory(item);
        });
		Thread.sleep(1000);
		
		boolean result = inventory.getInventoryItems().contains(item);
		assertTrue(result);
	}
	
	@Test
	public void testAddMoneyItem() throws InterruptedException {
		PurchasableItem item = new PurchasableItem("Cake", 10);
		ItemShop shop = new ItemShop(stage, inventory, 40, player);
		
		Platform.runLater(() -> {
            shop.addItemToInventory(item);
        });
		Thread.sleep(1000);
		
		boolean result = inventory.getInventoryItems().contains(item);
		assertTrue(result);
	}
	
	@Test
	public void testHandleItemChoice() throws InterruptedException {
		PurchasableItem item = new PurchasableItem("Cake", 10);
		ItemShop shop = new ItemShop(stage, inventory, 40, player);
		
		Platform.runLater(() -> {
           shop.handleItemChoice(item);
        });
		Thread.sleep(1000);
		
		boolean result = inventory.getInventoryItems().contains(item);
		assertTrue(result);
	}
	
	
}
