package PetGame;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

/**
 * Handles the commands and actions that can be performed on to the pet.
 * 
 * This class manages user interactions with the pet such as feeding, playing, etc.
 * It also handles cooldowns when an action is done while updating the pet's state and UI accordingly. 
 * 
 * @see PetGame.Game
 * @author Muhammad Shayaan Ali
 * @author Novak Vukoijcic
 */
public class PetCommands {
    /** Game instance taken from the {@link PetGame.Game} */
    private Game game;
    /** Player instance taken from the {@link PetGame.Game} */
    private Player player;
    /** flipping timeline taken from the {@link PetGame.Game} */
    private Timeline flipping;
    /** sleep timeline to put the pet to sleep and change the sprite*/
    private Timeline sleepTimeline;
    /** Pet instance taken from the {@link PetGame.Game} */
    private Pet pet;

    /** boolean variable keeping if the pet can sleep right now */
    public boolean canSleep = true;
    /** boolean variable keeping if the pet can be fed right now */
    public static boolean canFeed = true;
    /** boolean variable keeping if the pet can be given a gift right now */
    public static boolean canGiveGift = true;
    /** boolean variable keeping if the pet can use the vet right now */
    public boolean canUseVet = true;
    /** boolean variable keeping if the pet can play right now */
    public boolean canPlay = true;
    /** boolean variable keeping if the pet can exercise right now */
    public boolean canExercise = true;

    /**
     * Consturctor for the petcommands class. 
     *  
     * @param game the current game instance.
     * @param player the current player instance. 
     * @param flipping the current timline for the flipping animation.
     * @param pet the current pet instance.
     */
    public PetCommands(Game game, Player player, Timeline flipping, Pet pet) {
        this.game = game;
        this.player = player;
        this.flipping = flipping;
        this.pet = pet;
    }

    /** 
     * Function used to handle user inputs and perform corresponding actions.
     * 
     * @param keyCode key which was pressed.
     * @param event action event triggered by user when clicking a keybind/button.
     * @throws IOException expection thrown if there is trouble handling the KeyCode.
     */
    public void handleInput(KeyCode keyCode, ActionEvent event) throws IOException {
        PetStatistics petStats = game.pet.getPetStats();
        String petState = petStats.getState();

        if (petState.equals("Dead") || petState.equals("Sleeping")) {
            game.statusTextArea.appendText("No actions available in this state.\n"); /** No actions can be done in Sleeping or Dead states */
            return;
        }
        if (petState.equals("Angry") || petState.equals("Angry") && petState.equals("Hungry")) {
            if (keyCode != KeyCode.K && keyCode != KeyCode.O) { /** Only certain actions can be done in Angry state. */
                game.statusTextArea.appendText("Pet is Angry, only able to play or give a gift.\n");
                return;
            }
        }

        switch (keyCode) { /** switch command to handle each different action */
            case U: /** when player clicks sleep */
                if ((petState.equals("Normal") || petState.equals("Hungry")) && canSleep) {
                    petStats.updateStatistics(0, 0, 0, 0);
                    boolean wasHungry = petState.equals("Hungry");
                    petStats.changeState("Sleeping");
                    changeToSleep(wasHungry); /** private function to change the pet sprite */
                    game.statusTextArea.appendText("Pet is now sleeping..." + "\n");
                } else {
                    game.statusTextArea.appendText("Sleep is on cooldown!" + "\n");
                }
                break;
    
            case I: /** when player clicks feed */
                if ((petState.equals("Normal") || petState.equals("Hungry") || petState.equals("Angry")) && canFeed) {
                    game.goToInventory(event);
                    
                } else {
                    game.statusTextArea.appendText("Feed is on cooldown!" + "\n");
                }
                break;
    
            case O: /** when player clicks gift */
                if ((petState.equals("Normal") || petState.equals("Hungry") || petState.equals("Angry")) && canGiveGift) {
                    game.goToInventory(event);
                    
                } else {
                    game.statusTextArea.appendText("Giving gift is on cooldown!" + "\n");
                }
                break;
    
            case J: /** when player clicks go to vet */
                if ((petState.equals("Normal") || petState.equals("Hungry")) && canUseVet) {
                    changeToHappy();
                    petStats.updateStatistics(20, 0, 0, 0);
                    game.statusTextArea.appendText("Pet visited the vet! +20 Health" + "\n");
                    startCooldown("vet");
                    player.getScore().decreaseScore(15);
                    game.setScore();
                    game.statusTextArea.appendText("Pet went to the vet, lost 15 points!\n");
                } else {
                    game.statusTextArea.appendText("Vet is on cooldown!" + "\n");
                }
                break;
    
            case K: /** when player clicks play */
                if ((petState.equals("Normal") || petState.equals("Hungry") || petState.equals("Angry")) && canPlay) {
                    player.getScore().increaseScore(10);
                    changeToHappy();
                    petStats.updateStatistics(0, 0, 0, 10);
                    game.statusTextArea.appendText("Pet is playing! +10 Happiness" + "\n");
                    startCooldown("play");
                } else {
                    game.statusTextArea.appendText("Play is on cooldown!" + "\n");
                }
                break;
    
            case L: /** when player clicks exercise */
                if ((petState.equals("Normal") || petState.equals("Hungry")) && canExercise) {
                    player.getScore().increaseScore(10);
                    changeToHappy();
                    petStats.updateStatistics(10, -10, -10, 0);
                    game.statusTextArea.appendText("Pet exercised! +10 Health, -10 Sleep, -10 Fullness" + "\n");
                    startCooldown("exercise");
                } else {
                    game.statusTextArea.appendText("Exercise is on cooldown!" + "\n");
                }
                break;
    
            default:
                game.statusTextArea.appendText("Invalid key: " + keyCode.getCode() + "\n");
        }
    }

    /**
     * method used by handleInput and {@link PetGame.InventoryController#useItem(ActionEvent)} to issue an cooldown on an command
     * 
     * @param action the command that will be needing a cooldown
     */
    public void startCooldown(String action) {
        int cooldownTime;
    
        switch (action) {
            case "sleep":
                cooldownTime = 5; canSleep = false;
                break;
            case "feed":
                cooldownTime = 5; canFeed = false;
                break;
            case "gift":
                cooldownTime = 5; canGiveGift = false;
                break;
            case "vet":
                cooldownTime = 5; canUseVet = false;
                break;
            case "play":
                cooldownTime = 5; canPlay = false;
                break;
            case "exercise":
                cooldownTime = 5; canExercise = false;
                break;
            default:
                return;
        }
    
        Timeline cooldown = new Timeline(new KeyFrame(Duration.seconds(cooldownTime), e -> resetCooldown(action)));
        cooldown.setCycleCount(1); /** Cooldown set for 5 seconds */
        cooldown.play();
    }

    /**
     * Helper method used to reset the cooldowns
     * @param action command which cooldown needs to be reset
     */
    private void resetCooldown(String action) {
        switch (action) {
            case "sleep":
                canSleep = true;
                game.pet.getPetStats().changeState("Normal");
                break;
            case "feed":
                canFeed = true;
                break;
            case "gift":
                canGiveGift = true;
                break;
            case "vet":
                canUseVet = true;
                break;
            case "play":
                canPlay = true;
                break;
            case "exercise":
                canExercise = true;
                break;
        }
        if (action != "sleep") {
            game.statusTextArea.appendText(action + " is now available again!" + "\n");
        }
        else if (action == "sleep" && canSleep) {
            game.statusTextArea.appendText("Sleeping is now available again!" + "\n");
        }
    }

    /**
     * method used by handleInput and {@link PetGame.InventoryController#useItem(ActionEvent)} to change pet's sprite to happy.
     * 
     */
    public void changeToHappy() {
        game.setScore();
        flipping.stop();
        game.petImageView.setImage(new Image(getClass().getResource(pet.getHappyImage()).toExternalForm()));
        PauseTransition pause = new PauseTransition(Duration.seconds(2)); /** pauses current transition to display a happy sprite */
        pause.setOnFinished(event -> {
            game.petImageView.setImage(new Image(getClass().getResource(game.regularImage).toExternalForm()));
            flipping.play();
        });
        pause.play();
    }


    /**
     * Helper method used to display the sprite as sleeping when sleeping
     * @param wasHungry checks if the pet was in a hungry state
     */
    private void changeToSleep(Boolean wasHungry) {
        PetStatistics petStats = game.pet.getPetStats();
        player.getScore().increaseScore(10); 
        game.setScore();

        flipping.stop(); /** stops flipping animation and playing sleeping sprite */
        game.petImageView.setImage(new Image(getClass().getResource(pet.getSleepImage()).toExternalForm()));
        sleepTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {;
            if (petStats.sleep.get() >= petStats.maxSleep) {
                sleepTimeline.stop();
                game.petImageView.setImage(new Image(getClass().getResource(game.regularImage).toExternalForm()));
                resetCooldown("sleep");
                if (wasHungry) { /** sets state back to hungry if pet was hungry and sleepy */
                    petStats.changeState("Hungry");
                }
            }
            else {
                petStats.sleep.set(petStats.validateStat(petStats.sleep.get() + 5, petStats.maxSleep));
            }
        }));
        sleepTimeline.setCycleCount(Timeline.INDEFINITE);
        sleepTimeline.play();
    }
}
