package PetGame;

import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;

/**
 * <b>Main class</b> used as a starting point for the program.
 * 
 * <p>
 * The main class inherits the {@link Application} class which allows the program to run a JavaFX application.
 * It initializes the game, loads the main menu screen and handles the exit process.
 * </p>
 * 
 * @author Muhammad Shayaan Ali
 */
public class Main extends Application {

    /**
     * start method as an entry point to the game
     * 
     * <p>
     * Sets the initial screen size and ensures fullscreen ability is not available. 
     * Loads the Main.fxml file as the first screen
     * </p>
     * 
     * @param primaryStage the first stage that will be used to open the game
     * @throws Exception if there is an error loading the FXML file, an exception is thrown
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Statistics.loadStatistics(); /** Loads the statistics for the save files */
        Limitations.load();
        Statistics.playSessions++;
        
        Parent root = FXMLLoader.load(getClass().getResource("FXML_Files/Main.fxml"));
        primaryStage.setTitle("Pet Game");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.setFullScreen(false);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setResizable(false);
        
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            exit(primaryStage);
        });
    }
 
    /**
     * Handles the process of leaving the game
     * <p>
     * method sends different alerts on screen to the user to confirm if they want to leave the game or not.
     * If the user confirms, they will be asked if they want to save the game file.
     * </p>
     * 
     * @param stage the stage that the user is currently on so that it can be closed
     */
    public void exit(Stage stage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit Game");
        alert.setHeaderText("Leaving Game");
        alert.setContentText("Do you want to leave the game?");

        SaveGame savegame = new SaveGame(); /** creates a savegame instance so the game file can be saved */
        Game game = Game.getInstance();

        if (alert.showAndWait().get() == ButtonType.OK) {
            if (game != null) {
                List<String> saveOptions = List.of("save1.json", "save2.json", "save3.json", "save4.json", "save5.json");
                ChoiceDialog<String> choiceDialog = new ChoiceDialog<>("save1.json", saveOptions);
                choiceDialog.setTitle("Select Save File");
                choiceDialog.setHeaderText("Choose a save file to save your progress:");
                choiceDialog.setContentText("Save File:");

                choiceDialog.showAndWait().ifPresent(selectedFile -> {
                    savegame.initializeSaveGame(game);
                    savegame.saveToFile(selectedFile); /** saves the game to the selected json file */
                    System.out.println("Game saved to: " + selectedFile);
                });
            }
            Statistics.saveStatistics(); 
            stage.close(); /** saves the statistics and ensures the stage is closed */
        }
    }

    /**
     * Main class used to launch the application with the provided arguments
     * 
     * @param args command-line arguments which are passed to the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}

