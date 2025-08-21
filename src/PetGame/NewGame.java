package PetGame;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class NewGame extends MainController {
    public int petType;

    public void switchToPet1(ActionEvent event) throws IOException {
        petType = 1;
        changeScene(event, "FXML_Files/Pet1.fxml");
    }
    public void switchToPet2(ActionEvent event) throws IOException {
        petType = 2;
        changeScene(event, "FXML_Files/Pet2.fxml");
    }
    public void switchToPet3(ActionEvent event) throws IOException {
        petType = 3;
        changeScene(event, "FXML_Files/Pet3.fxml");
    }
    private void changeScene(ActionEvent event, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        NewGame controller = loader.getController();
        controller.petType = this.petType;
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
    }
    
    public void confirmSelection(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Pet Selection");
        alert.setHeaderText("Pet Choice");
        alert.setContentText("Do you want to play the game with this pet?");
        
        if (alert.showAndWait().get() == ButtonType.OK) {
            TextInputDialog nameDialog = new TextInputDialog();
            nameDialog.setTitle("Pet Name");
            nameDialog.setHeaderText("Name Your Pet");
            nameDialog.setContentText("Enter your pet's name (max 10 characters):");
        
            Optional<String> result = nameDialog.showAndWait();
        
            if (result.isPresent() && !result.get().trim().isEmpty()) {
                String petName = result.get().trim();
        
                if (petName.length() > 10) {  // Limit name to 10 characters
                    Alert warning = new Alert(Alert.AlertType.WARNING);
                    warning.setTitle("Invalid Name");
                    warning.setHeaderText("Name Too Long");
                    warning.setContentText("Please enter a name with 10 characters or fewer.");
                    warning.showAndWait();
                    return;
                }
        
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Files/Game.fxml"));
                Parent root = loader.load();
        
                Game gameClass = loader.getController();
                gameClass.startGame(event, petType, petName);
        
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
        
                scene.setOnKeyPressed(eventKey -> {
                    try {
                        gameClass.handleKeyPress(eventKey.getCode(), event);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });
        
                stage.setScene(scene);
                stage.show();
            } else {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Invalid Name");
                warning.setHeaderText("No Name Entered");
                warning.setContentText("Please enter a valid name for your pet.");
                warning.showAndWait();
            }
        }
    }
}
