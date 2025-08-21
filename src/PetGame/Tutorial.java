package PetGame;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * The Tutorial class handles navigation to the tutorial and help screens in the game.
 * It extends the MainController class and provides methods to load and display the respective scenes.
 * 
 * @author Ishaan Misra
 */
public class Tutorial extends MainController {

    @FXML private Button tutorialButton, helpButton;

    /**
     * Navigates to the tutorial screen.
     * 
     * @param event The ActionEvent triggered by clicking the tutorial button.
     * @throws IOException If the FXML file for the tutorial screen cannot be loaded.
     */
    public void goToTutorial(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Files/TutorialScreen.fxml"));
        Scene scene = new Scene(loader.load());
    
        TutorialController controller = loader.getController();
        controller.setPreScene(tutorialButton.getScene());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Navigates to the help screen.
     * 
     * @param event The ActionEvent triggered by clicking the help button.
     * @throws IOException If the FXML file for the help screen cannot be loaded.
     */
    public void goToHelp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Files/Help.fxml"));
        Scene scene = new Scene(loader.load());
    
        HelpController controller = loader.getController();
        controller.setPreScene(helpButton.getScene());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}

