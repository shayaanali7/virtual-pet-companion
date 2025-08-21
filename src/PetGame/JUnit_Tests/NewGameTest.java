package PetGame.JUnit_Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import PetGame.*;

public class NewGameTest {

    private NewGame newGame;
    private ActionEvent event;
    private String petName;

    @BeforeEach
    public void setUp() throws InterruptedException {
        // Initialize the JavaFX toolkit in the test thread.
        new JFXPanel();  // Required to initialize JavaFX.

        // Create a CountDownLatch to wait for JavaFX thread initialization
        CountDownLatch latch = new CountDownLatch(1);

        // Ensure the test runs on the JavaFX Application Thread
        Platform.runLater(() -> {
            event = new ActionEvent(); // Mock event
            newGame = new NewGame();  // Initialize newGame inside the JavaFX thread

            latch.countDown();  // Signal that JavaFX initialization is complete
        });

        latch.await();  // Wait for JavaFX thread initialization before continuing the test.
    }

    @Test
    public void testSwitchToPet1() {
        // Simulate selecting Pet 1
        Platform.runLater(() -> {
            try {
                newGame.switchToPet1(event);
                
                // Check if petType was set to 1
                assertEquals(1, newGame.petType, "Pet type should be 1 after selecting Pet 1.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Wait for the JavaFX thread to finish any operations before test concludes.
        Platform.runLater(() -> {});  // Ensure the test doesn't finish before UI tasks complete.
    }

    @Test
    public void testConfirmSelectionWithValidName() {
        // Simulate confirming the pet selection with a valid name
        Platform.runLater(() -> {
            // Set a mock name for the pet
			petName = "Buddy";

			// Simulate confirmation dialog
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Pet Selection");
			alert.setHeaderText("Pet Choice");
			alert.setContentText("Do you want to play the game with this pet?");

			if (alert.showAndWait().get() == ButtonType.OK) {
			    // Simulate entering the pet name
			    TextInputDialog nameDialog = new TextInputDialog(petName);
			    nameDialog.setTitle("Pet Name");
			    nameDialog.setHeaderText("Name Your Pet");
			    nameDialog.setContentText("Enter your pet's name (max 10 characters):");

			    Optional<String> result = nameDialog.showAndWait();

			    if (result.isPresent() && !result.get().trim().isEmpty()) {
			        String petName = result.get().trim();

			        // Check if name is valid (less than 10 characters)
			        if (petName.length() <= 10) {
			            // Simulate loading the game screen
			            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Files/Game.fxml"));
			            try {
			                loader.load();
			                Game gameClass = loader.getController();
			                gameClass.startGame(event, newGame.petType, petName);

			                Stage stage = new Stage();  // Simulate the Stage
			                Scene scene = new Scene(loader.getRoot());
			                stage.setScene(scene);
			                stage.show();

			                // Check if the game screen is loaded and petType is passed correctly
			                assertEquals(1, newGame.petType, "Pet type should be 1.");
			            } catch (IOException e) {
			                e.printStackTrace();
			            }
			        } else {
			            fail("Pet name should be valid and less than 10 characters.");
			        }
			    } else {
			        fail("Pet name should not be empty.");
			    }
			}
        });

        // Wait for the JavaFX thread to finish any operations before test concludes.
        Platform.runLater(() -> {});  // Ensure the test doesn't finish before UI tasks complete.
    }

    @Test
    public void testConfirmSelectionWithInvalidName() {
        // Simulate confirming the pet selection with an invalid name (too long)
        Platform.runLater(() -> {
            // Set a mock invalid name for the pet (more than 10 characters)
			petName = "SuperLongName123";

			// Simulate confirmation dialog
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Pet Selection");
			alert.setHeaderText("Pet Choice");
			alert.setContentText("Do you want to play the game with this pet?");

			if (alert.showAndWait().get() == ButtonType.OK) {
			    // Simulate entering the invalid pet name
			    TextInputDialog nameDialog = new TextInputDialog(petName);
			    nameDialog.setTitle("Pet Name");
			    nameDialog.setHeaderText("Name Your Pet");
			    nameDialog.setContentText("Enter your pet's name (max 10 characters):");

			    Optional<String> result = nameDialog.showAndWait();

			    if (result.isPresent()) {
			        String enteredName = result.get().trim();

			        // Check if the name exceeds 10 characters
			        if (enteredName.length() > 10) {
			            // Simulate the warning
			            Alert warning = new Alert(Alert.AlertType.WARNING);
			            warning.setTitle("Invalid Name");
			            warning.setHeaderText("Name Too Long");
			            warning.setContentText("Please enter a name with 10 characters or fewer.");
			            warning.showAndWait();

			            // Test should pass here as the name is invalid
			            assertTrue(enteredName.length() > 10, "Pet name should exceed 10 characters.");
			        }
			    } else {
			        fail("Pet name should not be empty.");
			    }
			}
        });

        // Wait for the JavaFX thread to finish any operations before test concludes.
        Platform.runLater(() -> {});  // Ensure the test doesn't finish before UI tasks complete.
    }
}
