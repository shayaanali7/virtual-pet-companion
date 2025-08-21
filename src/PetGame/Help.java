package PetGame;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * The Help class manages the help screen of the game.
 * It provides detailed instructions and tips for the player to understand the game mechanics.
 * This class also allows navigation back to the tutorial screen.
 * 
 * @author Ishaan Misra
 */
public class Help extends MainController {

    @FXML
    private TextArea textArea; // Text area to display help information

    /**
     * Initializes the help screen by populating the text area with game instructions and tips.
     * This method is automatically called after the FXML file is loaded.
     */
    @FXML
    public void initialize() {
        textArea.setText(
            "The pet needs sleep!!!\n" +
            "- While sleeping, your pet cannot perform actions like eating, exercising, or playing.\n" +
            "- If the sleep bar reaches zero, a health penalty is applied, and the pet will fall asleep and can no longer be interacted with. In the sleeping state the sleep value will slowly increase until it hits the maximum value. Once the max is reached, the pet wakes and returns to its normal state. During the sleeping state, the other statistics still decline normally.\n\n" +

            "The pet has health, so be careful!!\n" +
            "- Low health means your pet may stop responding, so take your pet to the vet to restore its health.\n" +
            "- If the health bar reaches zero, the pet dies and the game is over. You will then have the option to start a new game or load a game.\n\n" +

            "Feeding and Fullness:\n" +
            "- When the fullness bar reaches zero, the pet enters a hungry state, and happiness declines faster. Health points also start decreasing until the pet exits the hungry state.\n" +
            "- Use the Feed button to increase fullness. Be careful though, because all this food is coming from your inventory!\n\n" +

            "Happy Pet, Happy Life:\n" +
            "- Play, give gifts, or interact with your pet.\n" +
            "- When happiness reaches zero, the pet will enter the angry state. In the angry state, the pet will refuse all commands except those that increase happiness!\n\n" +

            "Parental Controls:\n" +
            "- If the playtime has a set limit selected by a parent, the game ends when that time is reached.\n\n" +

            "Tips for the game:\n" +
            "- Green bars --> Healthy, \tOrange --> Moderate, \tRed --> LOW!!!\n" +
            "- Make sure to check the stats and bars often and take care of your pet accordingly!!\n\n" + 

            "- Based on the pet’s current state, the following commands are available to the player:\n" + 
            "\t1. Dead State: No commands.\n" + 
            "\t2. Sleeping State: No commands.\n" + 
            "\t3. Angry State: Give Gift and Play.\n" + 
            "\t4. Hungry State: All commands.\n"  + 
            "\t5. Normal State: All commands.\n\n" +

            "Keyboard Controls:\n" +
            "- **U** → Go to bed (sleep)\n" +
            "- **I** → Feed\n" +
            "- **O** → Give gift\n" +
            "- **J** → Take to vet\n" +
            "- **K** → Play\n" +
            "- **L** → Exercise\n"
        );
    }

    /**
     * Navigates back to the tutorial screen.
     * 
     * @param event The ActionEvent triggered by clicking the "Go Back" button.
     * @throws IOException If the FXML file for the tutorial screen cannot be loaded.
     */
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Files/Tutorial.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
