package PetGame.JUnit_Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import PetGame.*;

public class SaveGameTest {

    private SaveGame saveGame;
    private Game game;
    private String saveFile = "mockSaveFile.json";
    private PetCommands petCommands;
    private Player player;
    private Pet pet;
    private Timeline flipping;
    private TextArea statusTextArea;
    private Label scoreLabel;

    @BeforeEach
    public void setUp() throws InterruptedException {
        // Initialize the JavaFX toolkit in the test thread.
        new JFXPanel();  // Required to initialize JavaFX.

        // Create a CountDownLatch to wait for JavaFX thread initialization
        CountDownLatch latch = new CountDownLatch(1);

        // Ensure the test runs on the JavaFX Application Thread
        Platform.runLater(() -> {
            game = new Game(); // Initialize game object
            saveGame = new SaveGame(); // Initialize saveGame object
            
            ImageView petImage = new ImageView();
            PetStatistics dummyStats = new PetStatistics(90, 75, 80, 100, 6, 3, 4, game, flipping, pet);
            game.pet = new EnergeticPet(petImage);
            game.pet.setPetStats(dummyStats);

            // Set up mock game state
            game.petType = 1;
            game.petName = "Buddy";
            game.pet.getPetStats().health.set(100);
            game.pet.getPetStats().sleep.set(50);
            game.pet.getPetStats().fullness.set(80);
            game.pet.getPetStats().happiness.set(90);
            game.pet.getPetStats().state.set("Awake");

            game.player.setCoins(200);
            game.player.score.setScore(3000);
            
            player = new Player(10);         // Assuming a simple constructor
            flipping = new Timeline();       // Empty Timeline for the test
            game.statusTextArea = statusTextArea;
            game.scoreLabel = scoreLabel;

            latch.countDown();  // Signal that JavaFX initialization is complete
        });

        latch.await();  // Wait for JavaFX thread initialization before continuing the test.
    }

    @Test
    public void testSaveGame() throws IOException, InterruptedException {
        // Ensure this operation runs on the JavaFX application thread.
        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            // Initialize the SaveGame object with the current game state
            saveGame.initializeSaveGame(game);

            // Save the game to a file
            saveGame.saveToFile(saveFile);

            // Verify that the file is created
            File file = new File(saveFile);
            assertTrue(file.exists(), "The save file should be created.");

            // Verify that the file contains the correct data
            // Read the saved JSON file and check if it contains the correct values
            try {
                String json = new String(java.nio.file.Files.readAllBytes(file.toPath()));

                // Parse the JSON data and assert correct values
                Gson gson = new Gson();
                SaveGame savedGame = gson.fromJson(json, SaveGame.class);

                assertNotNull(savedGame, "The saved game should not be null.");

                // Verify pet information
                assertEquals(1, savedGame.petType, "The saved pet type should be 1.");
                assertEquals("Buddy", savedGame.petName, "The saved pet name should be 'Buddy'.");

                // Verify pet stats
                assertEquals(100.0, savedGame.petStats.get("health"), "The saved pet health should be 100.");
                assertEquals(50.0, savedGame.petStats.get("sleep"), "The saved pet sleep should be 50.");
                assertEquals(80.0, savedGame.petStats.get("fullness"), "The saved pet fullness should be 80.");
                assertEquals(90.0, savedGame.petStats.get("happiness"), "The saved pet happiness should be 90.");
                assertEquals("Awake", savedGame.petStats.get("state"), "The saved pet state should be 'Awake'.");

                // Verify player information
                assertEquals(200.0, savedGame.player.get("money"), "The saved player's money should be 200.");
                assertEquals(3000.0, savedGame.player.get("score"), "The saved player's score should be 3000.");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Clean up: delete the saved file after the test
            File file1 = new File(saveFile);
            file1.delete();

            latch.countDown();  // Signal that the test is complete
        });

        latch.await();  // Wait for the JavaFX thread to finish any operations before test concludes.
    }

    @Test
    public void testSaveGameEmpty() {
        // Ensure this operation runs on the JavaFX application thread.
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            // Initialize an empty SaveGame object
            SaveGame emptySaveGame = new SaveGame();

            // Attempt to save the empty game state
            emptySaveGame.saveToFile(saveFile);

            // Verify that the file is created
            File file = new File(saveFile);
            assertTrue(file.exists(), "The empty save file should be created.");

            // Clean up: delete the saved file after the test
            file.delete();

            latch.countDown();  // Signal that the test is complete
        });

        try {
            latch.await();  // Wait for the JavaFX thread to finish any operations before test concludes.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
