 package PetGame.JUnit_Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import PetGame.EnergeticPet;
import PetGame.Game;
import PetGame.Pet;
import PetGame.PetCommands;
import PetGame.PetStatistics;
import PetGame.Player;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

/**
 * JUnit test class for the Game class.
 */

class GameTest {
	 private PetCommands petCommands;
	    private Game game;
	    private Player player;
	    private Pet pet;
	    private Timeline flipping;
	    private TextArea statusTextArea;
	    private Label scoreLabel;
	    private ImageView imageView;
	    private PetStatistics petStats;

	   
	    @BeforeEach
	    public void setup() throws Exception {
	        // Initialize JavaFX environment
	        new JFXPanel();  // Initializes JavaFX runtime

	        CountDownLatch latch = new CountDownLatch(1);
	        Platform.runLater(() -> {
	            player = new Player(10);   
	            game = new Game();
	            ImageView imageView = new ImageView();
	           
	            game.petNameLabel = new Label();
	            game.coinsLabel = new Label();
	            game.scoreLabel = new Label();
	            game.petImageView = new ImageView();
	            
	            game.goToBedButton = new Button();
	            game.feedButton = new Button();
	            game.giveGiftButton = new Button();
	            game.playButton = new Button();
	            game.takeToVetButton = new Button();
	            game.exerciseButton = new Button();
	            
	            game.pet = new EnergeticPet(imageView);
	            petStats = new PetStatistics(0, 0, 0, 0, 0, 0, 0, game, flipping, pet);
	            game.pet.setPetStats(petStats);
	            game.petCommand = new PetCommands(game, player, flipping, pet);
	            
	            game.healthBar = new ProgressBar();
	            game.sleepBar = new ProgressBar();
	            game.fullnessBar = new ProgressBar();
	            game.healthBar = new ProgressBar();
	            game.happinessBar = new ProgressBar();
	            latch.countDown();  
	        });
	        latch.await();  
	    }
    
    @Test
    public void testInstance() {
    	Game instance = game.getInstance();
    	Game instance2 = game.getInstance();
    	assertEquals(instance, instance2);
    }
    
    @Test
    public void testStartGame() {
    	game.startGame(null, 1, "Minion");
    	String name = game.petName;
    	int type = game.petType;
    	Pet pet = game.pet;
    	PetStatistics stats = game.pet.getPetStats();
    	
    	assertEquals("Minion", name);
    	assertEquals(1, type);
    	assertNotNull(pet);
    	assertNotNull(stats);
    	assertNotNull(game.regularImage);
    	assertNotNull(game.flippedImage);
    }
    
    @Test
    public void testSetScore() {
    	game.player.getScore().increaseScore(10);
    	game.setScore();
    	assertTrue(game.scoreLabel.getText().contains("Score: " + 10));
    }
    
    @Test
    public void testUpdateCoins() {
    	game.player.getScore().increaseScore(10);
    	game.setCoins();
    	
    	game.player.getScore().increaseScore(100);
    	game.setCoins();
    	assertTrue(game.coinsLabel.getText().contains("Player Coins: " + 35));
    }
    
    @Test
    public void testBedButton() {
    	KeyCode keyCode = KeyCode.U;
    	ActionEvent event = new ActionEvent();
    	assertDoesNotThrow(() -> game.handleKeyPress(keyCode, event));
    }
    
    @Test
    public void testFeedButton() {
    	KeyCode keyCode = KeyCode.I;
    	ActionEvent event = new ActionEvent();
    	assertDoesNotThrow(() -> game.handleKeyPress(keyCode, event));
    }
    
    @Test
    public void testGiftButton() {
    	KeyCode keyCode = KeyCode.O;
    	ActionEvent event = new ActionEvent();
    	assertDoesNotThrow(() -> game.handleKeyPress(keyCode, event));
    }

    @Test
    public void testVetButton() {
    	KeyCode keyCode = KeyCode.J;
    	ActionEvent event = new ActionEvent();
    	assertDoesNotThrow(() -> game.handleKeyPress(keyCode, event));
    }
    
    @Test
    public void testPlayButton() {
    	KeyCode keyCode = KeyCode.K;
    	ActionEvent event = new ActionEvent();
    	assertDoesNotThrow(() -> game.handleKeyPress(keyCode, event));
    }
    
    @Test
    public void testExerciseButton() {
    	KeyCode keyCode = KeyCode.L;
    	ActionEvent event = new ActionEvent();
    	assertDoesNotThrow(() -> game.handleKeyPress(keyCode, event));
    }
    
    @AfterEach
    public void tearDown() {
    	game = null;
        player = null;
        pet = null;
        petCommands = null;
        petStats = null;
        flipping = null;
        statusTextArea = null;
        scoreLabel = null;
        imageView = null;
        
        // Run JavaFX cleanup if necessary
        Platform.runLater(() -> {
            game = null;
        });
    }
    
    
}
