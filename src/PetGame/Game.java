package PetGame;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * <b>Game class</b> that manages the core gameplay logic and UI interactions. 
 * 
 * <p>
 * This class is responsible for initalizing the game, managing the pet's stats and sprites. 
 * It is also used to handle user interactions and switch between different screens.
 * Lastly, it is used to handle playtime limitations, progress bar updates and saving the game files.
 * </p>
 * 
 * @author Muhammad Shayaan Ali
 * @author Novak Vukoijcic
 * @author Ishaan Misra
 * @author Alessia Pilla
 * @author Julia Kasperek
 */
public class Game implements Initializable{
    @FXML
    /** ImageView which is being used to display the pet sprite */
    public ImageView petImageView;
    @FXML
    /** The health progress bar displaying the pets health */
    public ProgressBar healthBar;
    @FXML
    /** the sleep progress bar displaying the pets sleep  */
    public ProgressBar sleepBar;
    @FXML
    /** the fullness progress bar displaying the pets hunger */
    public ProgressBar fullnessBar;
    @FXML
    /** the happiness progress bar displaying the pets happiness */
    public ProgressBar happinessBar;
    
    @FXML
    public Label scoreLabel, coinsLabel, petNameLabel;
    /** score labels which display the players score and coins aswell as the pets name */
    @FXML
    /** button for the pet to go to sleep */
    public Button goToBedButton;
    @FXML
    /** button for the pet to get fed */
    public Button feedButton;
    @FXML
    /** button for the pet to get a gift */
    public Button giveGiftButton;
    @FXML
    /** button for the pet to go to the vet */
    public Button takeToVetButton;
    @FXML
    /** button for the pet to go to play */
    public Button playButton;
    @FXML
    /** button for the pet to go to exercise */
    public Button exerciseButton;
    @FXML
    /** A text area which displays feedback for actions that the user has taken, aswell as current cooldowns */
    public TextArea statusTextArea;
    @FXML
    /** button to go to the inventory screen */
    private Button inventoryButton;
    @FXML
    /** button to save the game */
    private Button saveGameButton;


    /** The current game istance */
    private static Game instance;
    /** the regular image of the sprite */
    public String regularImage = "";
    /** the regular flipped image of the sprite */
    public String flippedImage = "";
    /** the pet instance being used */
    public Pet pet;
    /** the type of pet being used */
    public int petType;
    /** a flipping timeline which switches between the regularImage and the flippedImage */
    public Timeline flipping;
    /** player instance */
    public Player player;
    /** pet commands instance */
    public PetCommands petCommand;
    /** pet statistics instance to keep track of pet stats */
    PetStatistics stats;
    /** a playtime timeline used to track the amount the user has played */
    private Timeline playtimeLimitTimer;
    /** The pet's name */
    public String petName;

    /** the amount of the time the user can actually play */
    /** the instance of the inventory, storing all of the users items */
    public Inventory inventory;
    /** the itemshop instance, storing the items the user can buy */
    public ItemShop itemShop;
    /** keeps track of the previous store to ensure when the user can recieve more coins */
    private int lastScore = 0;

    /**
     * Constructor for the Game class.
     * 
     * The constructor is used to initalize the inventory, itemshop, player and the flipping animation.
     */
    public Game() {
        instance = this;
        this.player = new Player(10);
        this.inventory = player.getInventory();
        this.itemShop = new ItemShop(new Stage(), inventory, player.getCoins(), player);
        flipping = new Timeline(new KeyFrame(Duration.seconds(5), event -> { /** Creates a new timeline for the flipping animation to occur every 5 seconds */
            if (petImageView.getImage().getUrl().equals(getClass().getResource(regularImage).toExternalForm())) {
                petImageView.setImage(new Image(getClass().getResource(flippedImage).toExternalForm()));
            } else {
                petImageView.setImage(new Image(getClass().getResource(regularImage).toExternalForm()));
            }
        }));
        flipping.setCycleCount(Timeline.INDEFINITE); /** sets the timeline to run the whole time the program is running */
    }

    /**
     * Gets the singleton instance of the game class
     * 
     * @return the game instance
     */
    public static Game getInstance() {
        return instance;
    }

    /**
     * Starts the game with the pet type and name choosen by the user 
     * 
     * Initializes the pet, its stats, game components such as progress bar bindings and updating the UI.
     * 
     * @param event action event triggered when the user starts playing the game
     * @param petType the type of pet the user has choosen
     * @param petName the name of the pet the user choose
     */
    public void startGame(ActionEvent event, int petType, String petName) {
    	if(Limitations.isBlockedTimeNow()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Access Denied");
            alert.setHeaderText(null);
            alert.setContentText("You are not allowed to play at this time due to limitations. Please come back later.");
            alert.showAndWait();
        
            try { /** Returns the player to the main menu if the player tried going into a new game during the restricted time */
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Files/Main.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

    	
        this.petName = petName;
        this.petType = petType;
        petNameLabel.setText(petName);
        switch (petType) { /** selects the choosen pet type, initalizing different pet stats based on the pet */
            case 1:
                pet = new EnergeticPet(petImageView);
                this.stats = new PetStatistics(90, 75, 80, 100, 6, 3, 4,
                        this, flipping, pet); 
                pet.setPetStats(stats);
                break;
            case 2:
                pet = new BalancedPet(petImageView);
                this.stats = new PetStatistics(85, 85, 85, 85, 3, 3, 3, 
                        this, flipping, pet);
                pet.setPetStats(stats);
                break;
            case 3: 
                pet = new LazyPet(petImageView);
                this.stats = new PetStatistics(50, 100, 70, 80, 2, 3, 5, 
                    this, flipping, pet);
                pet.setPetStats(stats);
                break;
            default:
                break;
        }

        regularImage = pet.getRegularImage();
        flippedImage = pet.getFlippedImage();
        flipping.play(); /** plays the flipping animation */
        this.petCommand = new PetCommands(this, player, flipping, pet);
        
        /** timeline used to add to the amount of time the player has been playing */
        Timeline playTimeTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            Statistics.secondsPlayed++;
        
            if (Statistics.secondsPlayed % 60 == 0) {
                Statistics.totalPlaytime++; 
                System.out.println("Logged 1 minute of playtime. Total minutes: " + Statistics.totalPlaytime);
            }
        
            
            if(Statistics.secondsPlayed % 1800 == 0) {
                System.out.println("Play session logged. Total sessions: " + Statistics.playSessions);
            }
        }));
        playTimeTimer.setCycleCount(Timeline.INDEFINITE); /** ensures the timeline is runned during the whole time */
        playTimeTimer.play();


        /** bind the progress bars displaying on screen to the pets current stats */
        healthBar.progressProperty().bind(stats.healthProperty().divide((double) stats.getMaxHealth()));
        sleepBar.progressProperty().bind(stats.sleepProperty().divide((double) stats.getMaxSleep()));
        fullnessBar.progressProperty().bind(stats.fullnessProperty().divide((double) stats.getMaxFullness()));
        happinessBar.progressProperty().bind(stats.happinessProperty().divide((double) stats.getMaxHappiness()));
        
        /** used to updated the bar colors if they drop to a certain threshold */
        Timeline colorUpdater = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateBarColours(stats)));
        colorUpdater.setCycleCount(Timeline.INDEFINITE);
        colorUpdater.play();

        updateCoins();
        Statistics.saveStatistics();
    }

    /**
    * Private functions that updates the colour of all progress bars depending on the current percentage of each stat
    * Green if ≥ 70%, Orange if between 25% to 70%, Red if < 25%
    * 
    * @param stats the PetStatistics instance containing the current stat values
    */
    private void updateBarColours(PetStatistics stats) { 
        barColour(healthBar, (double) stats.getHealth() / stats.getMaxHealth());
        barColour(sleepBar, (double) stats.getSleep() / stats.getMaxSleep());
        barColour(fullnessBar, (double) stats.getFullness() / stats.getMaxFullness());
        barColour(happinessBar, (double) stats.getHappiness() / stats.getMaxHappiness());
    }

    /**
     * Private function that sets the visual colour of a progress bar depending on its respective stat value
     * Green if ≥ 70%, Orange if between 25% and 70%, Red if < 25%.
     *
     * @param bar the ProgressBar to update
     * @param val the current percentage (0.0–1.0) of the stat
     */
    private void barColour(ProgressBar bar, double val) {
        if(val >= 0.7) {
            bar.setStyle("-fx-accent: green;");
        } 
        else if (val >= 0.25) {
            bar.setStyle("-fx-accent: orange;");
        } 
        else {
            bar.setStyle("-fx-accent: red;");
        }
    }

    /**
     * method used to display the players current score 
     * 
     * public method as it is used {@link PetGame.PetCommands} to update the score when a positive/negative action is done.
     * Calls upon the setCoins methods to check if the player's coins need to be updated
     * 
     */
    public void setScore() {
        scoreLabel.setText("Score: " + player.getScore().getScore());
        setCoins();
    }

    /**
     * Checks if the player's coins needs to be increased.
     * 
     * Players coin amount increases by 25 every 100 score, lastScore is used to check if over 100 points have been obtained.
     * 
     */
    public void setCoins() {
        int currentScore = player.getScore().getScore();
        int scoreIncrease = currentScore - lastScore;
        
        if (scoreIncrease >= 100) {
            int coinsToAdd = (scoreIncrease / 100) * 25;
            player.addCoins(coinsToAdd);
            lastScore += scoreIncrease;
            updateCoins();
        }
    }

    /*
     * Updates the amount of coins displayed on the screen
     */
    public void updateCoins() {
        coinsLabel.setText("Player Coins: " + player.getCoins());
    }

    @Override
    /**
     * Initializes the game UI components and sets the default styles for the progress bars
     * 
     * @param location the location used to resolve relative paths from the root
     * @param resources the resources used to localize the root
     * 
     */
    public void initialize(URL location, ResourceBundle resources) {
        healthBar.setStyle("-fx-accent: green;");
        sleepBar.setStyle("-fx-accent: green;");
        fullnessBar.setStyle("-fx-accent: green;");
        happinessBar.setStyle("-fx-accent: green;");
        setScore();
    }

    /**
     * Method used to switch to the inventory screen.
     * 
     * @param event Action event triggered when user clicks on the inventory button
     * @throws IOException Exeception thrown if FXML file cant be loaded
     */
    public void goToInventory(ActionEvent event) throws IOException {

    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Files/Inventory.fxml"));
        Scene scene = new Scene(loader.load());

        InventoryController controller = loader.getController(); /** Gets the controller from the InventoryController class. */
        controller.setPreScene(inventoryButton.getScene());
        controller.setPlayer(this.player); /** Sets the player and scene of game into the InventoryController so it can reference it back */

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method used to switch to the itemshop screen
     * 
     * @param event Action event triggered when user clicks on the itemshop button
     * @throws IOException Exeception thrown if FXML file cant be loaded
     */
    public void goToItemShop(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Files/ItemShop.fxml"));
        Scene scene = new Scene(loader.load());
        ItemShopController controller = loader.getController();
        controller.setPreScene(inventoryButton.getScene());
        controller.setPlayer(this.player, this.itemShop);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method used to switch to the main menu screen.
     * 
     * @param event Action event triggered when user clicks on the itemshop button
     * @throws IOException Exeception thrown if FXML file cant be loaded
     */
    public void goToMainMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Game");
        alert.setHeaderText("Do you want to save the game?");
        alert.setContentText("Choose an option:");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == yesButton) { /** checks if the user wants to save this game file */
                Alert saveAlert = new Alert(Alert.AlertType.CONFIRMATION);
                saveAlert.setTitle("Save Game");
                saveAlert.setHeaderText("Do you want to save the game?");

                SaveGame savegame = new SaveGame();
                Game game = Game.getInstance();

                if (game != null) { /** asks which file they would like to save to */
                    List<String> saveOptions = List.of("save1.json", "save2.json", "save3.json", "save4.json", "save5.json");
                    ChoiceDialog<String> choiceDialog = new ChoiceDialog<>("save1.json", saveOptions);
                    choiceDialog.setTitle("Select Save File");
                    choiceDialog.setHeaderText("Choose a save file to save your progress:");
                    choiceDialog.setContentText("Save File:");

                    choiceDialog.showAndWait().ifPresent(selectedFile -> {
                        savegame.initializeSaveGame(game);
                        savegame.saveToFile(selectedFile);
                        System.out.println("Game saved to: " + selectedFile);
                    });
                }
            }
        }
        Statistics.saveStatistics();

            /** Stops all animations and timelines and switches the main screen */
            if (flipping != null) flipping.stop();
            if (playtimeLimitTimer != null) playtimeLimitTimer.stop();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Files/Main.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
    }


    /**
     * Method used to handle different keyboard presses.
     * 
     * @param keyCode the key code of the pressed key
     * @param event the action event triggered by the user when clicking a key code
     * @throws IOException exception when an error occurs while handling input 
     */
    public void handleKeyPress(KeyCode keyCode, ActionEvent event) throws IOException {
        switch (keyCode) {
            case U: // Simulate clicking the "Go to Bed" button
                goToBedButton.fire();
                break;
            case I: // Simulate clicking the "Feed" button
                feedButton.fire();
                break;
            case O: // Simulate clicking the "Give Gift" button
                giveGiftButton.fire();
                break;
            case J: // Simulate clicking the "Take to Vet" button
                takeToVetButton.fire();
                break;
            case K: // Simulate clicking the "Play" button
                playButton.fire();
                break;
            case L: // Simulate clicking the "Exercise" button
                exerciseButton.fire();
                break;
            default:
                System.out.println("Unhandled key: " + keyCode);
        }
        System.out.println("Key Pressed: " + keyCode);
    }

    /**
     * Method used to handle the "Go to Bed" button click event through {@link PetGame.PetCommands}
     * 
     * @param event the action event triggered by user 
     * @throws IOException catches exception if error occurs when handling the input
     */
    public void bedButton(ActionEvent event) throws IOException {
        petCommand.handleInput(KeyCode.U, event);
    }

    /**
     * Method used to handle the "Feed" button click event through {@link PetGame.PetCommands}
     * 
     * @param event the action event triggered by user 
     * @throws IOException catches exception if error occurs when handling the input
     */
    public void feedButton(ActionEvent event) throws IOException {
        petCommand.handleInput(KeyCode.I, event);
    }

    /**
     * Method used to handle the "Gift" button click event through {@link PetGame.PetCommands}
     * 
     * @param event the action event triggered by user 
     * @throws IOException catches exception if error occurs when handling the input
     */
    public void giftButton(ActionEvent event) throws IOException {
        petCommand.handleInput(KeyCode.O, event);
    }

    /**
     * Method used to handle the "Go To Vet" button click event through {@link PetGame.PetCommands}
     * 
     * @param event the action event triggered by user 
     * @throws IOException catches exception if error occurs when handling the input
     */
    public void vetButton(ActionEvent event) throws IOException {
        petCommand.handleInput(KeyCode.J, event);
    }

    /**
     * Method used to handle the "Play" button click event through {@link PetGame.PetCommands}
     * 
     * @param event the action event triggered by user 
     * @throws IOException catches exception if error occurs when handling the input
     */
    public void playButton(ActionEvent event) throws IOException {
        petCommand.handleInput(KeyCode.K, event);
    }

    /**
     * Method used to handle the "Exercise" button click event through {@link PetGame.PetCommands}
     * 
     * @param event the action event triggered by user 
     * @throws IOException catches exception if error occurs when handling the input
     */
    public void exerciseButton(ActionEvent event) throws IOException {
        petCommand.handleInput(KeyCode.L, event);
    }

    /**
     * Method used to handle the "Save Game" button click event through {@link PetGame.PetCommands}
     * 
     * @param event the action event triggered by user 
     * @throws IOException catches exception if error occurs when handling the input
     */
    public void saveGameButton(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Save Game");
        alert.setContentText("Do you want to save the game?");

        SaveGame savegame = new SaveGame();
        Game game = Game.getInstance();

        if (alert.showAndWait().get() == ButtonType.OK) { /** checks if the user wants to save the game */
            if (game != null) {
                /** Asks the user what save file they would like to save to */
                List<String> saveOptions = List.of("save1.json", "save2.json", "save3.json", "save4.json", "save5.json");
                ChoiceDialog<String> choiceDialog = new ChoiceDialog<>("save1.json", saveOptions);
                choiceDialog.setTitle("Select Save File");
                choiceDialog.setHeaderText("Choose a save file to save your progress:");
                choiceDialog.setContentText("Save File:");

                choiceDialog.showAndWait().ifPresent(selectedFile -> {
                    savegame.initializeSaveGame(game);
                    savegame.saveToFile(selectedFile);
                    System.out.println("Game saved to: " + selectedFile);
                });
            }
        }
    }
    
}
