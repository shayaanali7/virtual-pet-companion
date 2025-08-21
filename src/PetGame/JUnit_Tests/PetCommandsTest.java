package PetGame.JUnit_Tests;

import static org.junit.jupiter.api.Assertions.*;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.embed.swing.JFXPanel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.*;

import PetGame.EnergeticPet;
import PetGame.Game;
import PetGame.Pet;
import PetGame.PetCommands;
import PetGame.PetStatistics;
import PetGame.Player;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.application.Platform;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;


public class PetCommandsTest {

    private PetCommands petCommands;
    private Game game;
    private Player player;
    private Pet pet;
    private Timeline flipping;
    private TextArea statusTextArea;
    private Label scoreLabel;

   
    @BeforeEach
    public void setup() throws Exception {
        // Initialize JavaFX environment
        new JFXPanel();  // Initializes JavaFX runtime

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Initialize real instances of necessary classes on the FX thread
            statusTextArea = new TextArea();
            scoreLabel = new Label();
            ImageView dummy = new ImageView();
            
            pet = new EnergeticPet(dummy); 
            PetStatistics dummyStats = new PetStatistics(90, 75, 80, 100, 6, 3, 4, game, flipping, pet);
            pet.setPetStats(dummyStats);

            player = new Player(10);         // Assuming a simple constructor
            flipping = new Timeline();       // Empty Timeline for the test
            game = new Game(); 
            game.pet = pet;
            game.statusTextArea = statusTextArea;
            game.petImageView = dummy;
            game.scoreLabel = scoreLabel;
            
            // Optionally, if your Game constructor needs statusTextArea,
            // you can pass it or set it here.
            petCommands = new PetCommands(game, player, flipping, pet);
            
            latch.countDown();  // Signal that initialization is complete
        });
        latch.await();  // Wait for the FX thread to finish initialization
    }
    
    @Test
    public void testDeadOrSleepState() throws IOException {
    	PetStatistics test = pet.getPetStats();
    	test.changeState("Dead");
    	petCommands.handleInput(KeyCode.U, null);
    	assertTrue(statusTextArea.getText().contains("No actions available in this state."));
    	
    	test.changeState("Sleeping");
    	petCommands.handleInput(KeyCode.U, null);
    	assertTrue(statusTextArea.getText().contains("No actions available in this state."));
    }

    @Test
    public void testHandleInput_sleepNormalState() throws Exception {
        // Set pet state to "Normal"
        PetStatistics petStats = pet.getPetStats();
        petStats.changeState("Normal");

        // Simulate 'U' key press for sleep action
        petCommands.handleInput(KeyCode.U, null);

        // Verify that the statusTextArea was updated with "Pet is now sleeping..."
        assertTrue(statusTextArea.getText().contains("Pet is now sleeping..."));
    }
    
    @Test
    public void testAngryState() throws IOException {
    	PetStatistics petStats = pet.getPetStats();
        petStats.changeState("Angry");

        // Simulate 'U' key press for sleep action
        petCommands.handleInput(KeyCode.U, null);

        // Verify that the statusTextArea was updated with "Pet is now sleeping..."
        assertTrue(statusTextArea.getText().contains("Pet is Angry, only able to play or give a gift."));
    }
    
    @Test
    public void testCoolDown() throws IOException {
    	PetStatistics petStats = pet.getPetStats();
        petStats.changeState("Normal");
        petCommands.canFeed = false;
        petCommands.handleInput(KeyCode.I, null);
        assertTrue(statusTextArea.getText().contains("Feed is on cooldown!"));  
    }
    
    @Test
    public void testVet() throws IOException {
    	PetStatistics petStats = pet.getPetStats();
        petStats.changeState("Normal");
        player.getScore().increaseScore(30);
        petCommands.handleInput(KeyCode.J, null);
        int result = player.getScore().getScore();
        assertEquals(15, result);
    }
    
    @Test
    public void testPlay() throws IOException {
    	PetStatistics petStats = pet.getPetStats();
        petStats.changeState("Normal");
        petCommands.handleInput(KeyCode.K, null);
        int result = player.getScore().getScore();
        assertEquals(10, result);
    }
    
    @Test
    public void testExercise() throws IOException {
    	PetStatistics petStats = pet.getPetStats();
        petStats.changeState("Normal");
        petCommands.handleInput(KeyCode.L, null);
        int result = player.getScore().getScore();
        assertEquals(10, result);
    }
    
    @Test
    public void testCoolDownMethod() {
    	petCommands.startCooldown("sleep");
    	boolean expected = false;
    	boolean result = petCommands.canSleep;
    	assertEquals(expected, result);
    }
    
    @AfterEach
    public void tearDown() {
        petCommands = null;
        game = null;
        player = null;
        pet = null;
        flipping = null;
        statusTextArea = null;
        scoreLabel = null;

        // Run JavaFX cleanup if necessary
        Platform.runLater(() -> {
            game = null;
        });
    }
    

}
    