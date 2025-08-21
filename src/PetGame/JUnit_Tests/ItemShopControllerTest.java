package PetGame.JUnit_Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import PetGame.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ItemShopControllerTest {
    private ItemShopController controller;
    private Player player;
    private ItemShop itemShop;
    private Label coinBalanceLabel;
    private Stage stage;
    private Scene scene;
    
    @BeforeEach
    public void setup() throws InterruptedException {
        new JFXPanel(); 
       
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            player = new Player(30); 
            itemShop = new ItemShop(stage, new Inventory(), 10, player);
            controller = new ItemShopController();
            controller.coinBalanceLabel = new Label();
            controller.setPlayer(player, itemShop); 
            
            scene = new Scene(new StackPane(), 300, 200);
            
            latch.countDown();
        });
        
        latch.await();
    }
    
    @Test
    public void testSetPlayer() {
    	controller.setPlayer(player, itemShop);
    	assertNotNull(controller.player);
    	assertNotNull(controller.itemShop);
    }
    
    @Test
    public void testUpdateCoins() {
    	double res = player.getCoins();
    	assertTrue(controller.coinBalanceLabel.getText().contains("Player Coins: " + player.getCoins()));
    }
    
    @Test
    public void testPreScene() {
    	controller.setPreScene(scene);
    	assertEquals(scene, controller.getPreScene());
    }
    

}
