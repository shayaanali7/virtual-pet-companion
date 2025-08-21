package PetGame;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller class used to manage switching to different screens in the main menu.
 * 
 * The class handles user interactions in terms of switching to different screens connected
 * to the main menu as well as exiting the program, and accessing parental controls.
 * 
 * @author Muhammad Shayaan Ali
 * @author Ishaan Misra
 */
public class MainController {

    @FXML
    /** stage that is being used on the application */
    public Stage stage;
    /** scene that is currently being displayed on the stage */
    public Scene scene; 
    /** the root node of the FXML file that is going to be added */
    public Parent root; 

    @FXML
    /** The exit button which is used to leave the program */
    private Button exitButton; 

    @FXML
    /** the container of the scene used to manage the layout  */
    public AnchorPane scenePane;

    /**
     * Switches the the new game screen.
     * 
     * @param event the action event triggered when a player clicks on the new game button
     * @throws IOException if the FXML file can't be opened, it will throw an expection
     */
    public void switchToNewGame(ActionEvent event) throws IOException {
        switchScreen(event, "FXML_Files/NewGame.fxml");
    }

    /**
     * Switches the the main menu screen.
     * 
     * @param event the action event triggered when a player clicks on the new game button
     * @throws IOException if the FXML file can't be opened, it will throw an expection
     */
    public void switchToMainMenu(ActionEvent event) throws IOException {
        switchScreen(event, "FXML_Files/Main.fxml");
    }

    /**
     * Switches the the load game screen.
     * 
     * @param event the action event triggered when a player clicks on the new game button
     * @throws IOException if the FXML file can't be opened, it will throw an expection
     */
    public void switchToLoadGame(ActionEvent event) throws IOException {
        switchScreen(event, "FXML_Files/LoadGame.fxml");
    }

    /**
     * Switches the the tutorial screen.
     * 
     * @param event the action event triggered when a player clicks on the new game button
     * @throws IOException if the FXML file can't be opened, it will throw an expection
     */
    public void switchToTutorial(ActionEvent event) throws IOException {
        switchScreen(event, "FXML_Files/Tutorial.fxml");
    }

    /**
     * Switches the the parental controls screen.
     * 
     * @param event the action event triggered when a player clicks on the new game button
     * @throws IOException if the FXML file can't be opened, it will throw an expection
     */
    public void switchToParentalControls(ActionEvent event) throws IOException {
        switchScreen(event, "FXML_Files/ParentalControls.fxml");
    }

    /**
     * Switches the the game screen.
     * 
     * @param event the action event triggered when a player clicks on the new game button
     * @throws IOException if the FXML file can't be opened, it will throw an expection
     */
    public void switchToGame(ActionEvent event, int petType) throws IOException {
        switchScreen(event, "FXML_Files/Game.fxml");
        // call the game class with petType
    }

    /**
     * Switches the the inventory screen.
     * 
     * @param event the action event triggered when a player clicks on the new game button
     * @throws IOException if the FXML file can't be opened, it will throw an expection
     */
    public void switchToInventory(ActionEvent event) throws IOException {
        switchScreen(event, "/PetGame/Inventory.fxml");
    }

    /**
     * method used by all other methods to switch to the new scene using JavaFX components
     * 
     * This method is used by other methods in the class to load a new FXLM file and set a new scene 
     * 
     * @param event the event triggered when a player wants to switch to another screen
     * @param fxml  the string value of the new FXML file
     * @throws IOException if the FXML file can't be opened it will throw an expection
     */
    protected void switchScreen(ActionEvent event, String fxml) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxml));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); /**Gets the window and stores it in the stage*/
        scene = new Scene(root);
        stage.setScene(scene); 
        stage.show(); /** sets the new scene and shows it, ensuring the switch screen is done */
    } 

    /**
     * This exit method is used when the user clicks the actual exit button in main menu
     * It is similar to the {@link PetGame.Main} exit function but does not ask to save.
     * @param event event triggered when clicking on the exit button
     */
    public void exit(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit Game");
        alert.setHeaderText("Leaving Game");
        alert.setContentText("Do you want to leave the game?");
        
        if (alert.showAndWait().get() == ButtonType.OK) {
            Statistics.saveStatistics();
            stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("Exiting game...");
            stage.close();
        }
        
    }

    @FXML
    /**
     * Helper method used to display a pop-up asking for a password
     * 
     * <p>
     * This private method is used when the player wants to access the parental controls.
     * A password is asked and handled, ensuring only the parent has access to the screen.
     * </p>
     * 
     * @param event event triggered when the player clicks on the parental controls button
     * @throws IOException expection thrown if the FXML file can't be loaded
     */
    private void handleParentalControls(ActionEvent event) throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Password Needed");
        dialog.setHeaderText("Enter password"); 
        dialog.setContentText("Password:"); /** Asks the user to input a password */

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent() && result.get().equals("123")) { /** Ensures the password is the same as our hardcoded password "123" */
            switchToParentalControls(event);
        } 
        else {
            Alert alert = new Alert(AlertType.ERROR); /** Denied message which pops up if the password is incorrect*/
            alert.setTitle("Access Denied");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect password.");
            alert.showAndWait();
        }
    }



}
