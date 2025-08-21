package PetGame.JUnit_Tests; 

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import PetGame.*;

public class LoadGameTest {

    private LoadGame loadGame;
    private String mockSaveFile = "mockSaveFile.json";  // Path to the mock JSON file
    private ActionEvent event;

    @BeforeEach
    public void setUp() throws InterruptedException {
        // Initialize the JavaFX toolkit in the test thread.
        new JFXPanel();  // Required to initialize JavaFX.
        
        // Create a CountDownLatch to wait for JavaFX thread initialization
        CountDownLatch latch = new CountDownLatch(1);

        // Ensure the test runs on the JavaFX Application Thread
        Platform.runLater(() -> {
            event = new ActionEvent(); // Mock event
            loadGame = new LoadGame();  // Initialize loadGame inside the JavaFX thread

            latch.countDown();  // Signal that JavaFX initialization is complete
        });

        latch.await();  // Wait for JavaFX thread initialization before continuing the test.

        // Write the mock JSON data to the file before testing
        try (FileWriter file = new FileWriter(mockSaveFile)) {
            String json = "{\n" +
                    "    \"petType\": 1,\n" +
                    "    \"petName\": \"Buddy\",\n" +
                    "    \"petStats\": {\n" +
                    "        \"health\": 100,\n" +
                    "        \"sleep\": 50,\n" +
                    "        \"fullness\": 80,\n" +
                    "        \"happiness\": 90,\n" +
                    "        \"state\": \"Awake\"\n" +
                    "    },\n" +
                    "    \"player\": {\n" +
                    "        \"money\": 200,\n" +
                    "        \"score\": 3000,\n" +
                    "        \"inventory\": []\n" +
                    "    },\n" +
                    "    \"gameStats\": {}\n" +
                    "}";
            file.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadGame() throws IOException {
        // Simulate loading the game from the mock save file
        // Ensure this operation runs on the JavaFX application thread.
        Platform.runLater(() -> {
            loadGame.loadGame(event, mockSaveFile);

			// Access the instance of the Game class from the loaded scene
			Game game = loadGame.gameClass;
			assertNotNull(game, "Game instance should not be null.");

			// Check pet type and pet name
			assertEquals(1, game.petType, "Pet type should be 1 after loading from the save file.");
			assertEquals("Buddy", game.petName, "Pet name should be 'Buddy' after loading from the save file.");

			// Check pet stats
			assertEquals(100, game.pet.getPetStats().health.get(), "Pet health should be 100.");
			assertEquals(50, game.pet.getPetStats().sleep.get(), "Pet sleep should be 50.");
			assertEquals(80, game.pet.getPetStats().fullness.get(), "Pet fullness should be 80.");
			assertEquals(90, game.pet.getPetStats().happiness.get(), "Pet happiness should be 90.");
			assertEquals("Awake", game.pet.getPetStats().state.get(), "Pet state should be 'Awake'.");

			// Check player stats
			assertEquals(200, game.player.getCoins(), "Player money should be 200.");
			assertEquals(3000, game.player.getScore().getScore(), "Player score should be 3000.");

			// Check if inventory is correctly initialized (it is empty in the mock data)
			assertNotNull(game.player.inventory, "Player inventory should not be null.");
			assertEquals(0, game.player.inventory.items.size(), "Player inventory should be empty.");
        });

        // Wait for the JavaFX thread to finish any operations before test concludes.
        Platform.runLater(() -> {});  // Ensure the test doesn't finish before UI tasks complete.
    }
}
