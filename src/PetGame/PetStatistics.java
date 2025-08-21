
package PetGame;
import javafx.animation.KeyFrame;
import javafx.scene.image.Image;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.ImageView;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Duration;
/**
 * The {@code PetStatistics} class manages the core stats of a pet: health,
 * sleep, fullness, and happiness. These stats automatically decay over time,
 * and the system triggers visual and behavioral changes in response to low values.
 * <p>
 * It uses JavaFX properties for real-time binding with the UI and manages pet state transitions,
 * including reactions like hunger, anger, sleep, and death.
 * </p>
 *
 * @author Ishaan Misra
 * @author Muhammad Shayaan Ali
 */
public class PetStatistics {
    /** Represents the pet's current health as a JavaFX property for UI binding. */
    public IntegerProperty health;
    /** Represents the pet's current sleep level as a JavaFX property for UI binding. */
    public IntegerProperty sleep;
    /** Represents the pet's current fullness level as a JavaFX property for UI binding. */
    public IntegerProperty fullness;
    /** Represents the pet's current happiness level as a JavaFX property for UI binding. */
    public IntegerProperty happiness;
    /** Represents the pet's current state (e.g., Normal, Angry, Hungry) as a JavaFX property. */
    public StringProperty state;
    /** The maximum health value the pet can reach. */
    private final int maxHealth;
    /** The maximum sleep value the pet can reach. */
    public final int maxSleep;
    /** The maximum fullness value the pet can reach. */
    private final int maxFullness;
    /** The maximum happiness value the pet can reach. */
    private final int maxHappiness;
    /** The rate at which the sleep stat decreases over time. */
    private final int sleepDecayRate;
    /** The rate at which the fullness stat decreases over time. */
    private final int fullnessDecayRate;
    /** The rate at which the happiness stat decreases over time. */
    private final int happinessDecayRate;
    /** Reference to the main game instance for accessing shared game components. */
    private Game game;
    /** Reference to the timeline used for flipping the pet's image. */
    private Timeline flipping;
    /** Reference to the pet associated with this statistics tracker. */
    private Pet pet;
    /** Indicates whether the pet has already received a penalty for staying awake too long. */
    private boolean sleepPenaltyApplied = false;
    /** Tracks whether the pet is currently sleeping to manage state transitions. */
    private boolean isSleeping = false;
    /** Timeline for the automatic stat decay */
    transient Timeline decayTimer;
    /**
     * The constructor. It constructs a {@code PetStatistics} object with given max values and decay rates.
     * @param maxHealth max health value
     * @param maxSleep max sleep value
     * @param maxFullness max fullness value
     * @param maxHappiness max happiness value
     * @param sleepDecayRate how quickly sleep decays
     * @param fullnessDecayRate how quickly fullness decays
     * @param happinessDecayRate how quickly happiness decays
     * @param game the game instance
     * @param flipping the flipping animation timeline
     * @param pet the pet object associated with these stats
     */
    public PetStatistics(int maxHealth, int maxSleep, int maxFullness, int maxHappiness, int sleepDecayRate, int fullnessDecayRate, int happinessDecayRate,
                Game game, Timeline flipping, Pet pet) {
        this.maxHealth = maxHealth;
        this.maxSleep = maxSleep;
        this.maxFullness = maxFullness;
        this.maxHappiness = maxHappiness;
        this.sleepDecayRate = sleepDecayRate;
        this.fullnessDecayRate = fullnessDecayRate;
        this.happinessDecayRate = happinessDecayRate;
        this.game = game;
        this.flipping = flipping;
        this.pet = pet;
        this.health = new SimpleIntegerProperty(maxHealth);
        this.sleep = new SimpleIntegerProperty(maxSleep);
        this.fullness = new SimpleIntegerProperty(maxFullness);
        this.happiness = new SimpleIntegerProperty(maxHappiness);
        this.state = new SimpleStringProperty("Normal");
        /** Starting automatic stat decay */
        startStatDecay();
    }
    /**
     * Starts a periodic stat decay system that decreases stats over time.
     */
    private void startStatDecay() {
        decayTimer = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            if (state.get().equals("Sleeping")) { /** If the pet is in any state other than sleeping, decay the stats except health */
                updateStatistics(0, 0, -fullnessDecayRate, -happinessDecayRate);
            }
            else {
                updateStatistics(0, -sleepDecayRate, -fullnessDecayRate, -happinessDecayRate);
            }
        }));
        decayTimer.setCycleCount(Timeline.INDEFINITE);
        decayTimer.play();
    }
    /**
     * Updates all pet stats with the specified change values.
     *
     * @param healthChange amount to change health
     * @param sleepChange amount to change sleep
     * @param fullnessChange amount to change fullness
     * @param happinessChange amount to change happiness
     */
    public void updateStatistics(int healthChange, int sleepChange, int fullnessChange, int happinessChange) {
        health.set(validateStat(health.get() + healthChange, maxHealth));
        sleep.set(validateStat(sleep.get() + sleepChange, maxSleep));
        fullness.set(validateStat(fullness.get() + fullnessChange, maxFullness));
        happiness.set(validateStat(happiness.get() + happinessChange, maxHappiness));
        checkWarnings();
    }
    /** 
    * Timeline used to gradually restore the pet's sleep stat while it is in the "Sleeping" state. 
    * This timeline runs independently until the sleep stat reaches its maximum value.
    */
    private Timeline sleepRecovery;
    /**
    * Checks if any statistics have dropped below critical levels and changes the pet's state accordingly.
    */
    private void checkWarnings() {
        if (getHealth() == 0) {
            game.statusTextArea.setText("Your pet has died. Please go to Main Menu and make a new or load a game.\n");
            if (sleepRecovery != null && sleepRecovery.getStatus() == Timeline.Status.RUNNING) {
                sleepRecovery.stop();
            }
            changeState("Dead");
            decayTimer.stop();
            hungerDecayTimer.stop();
            String dead = pet.getDeadImage();
            changeImage(dead, "");
            return;
        } 
        if (getState().equals("Sleeping")) {
            return;
        }
        else if (getSleep() == 0) {
            if (!isSleeping) {
                isSleeping = true;
                sleepRecover();
            }
        }  
        else if (getFullness() == 0) {
            if (!state.get().equals("Hungry")) {
                changeState("Hungry");
                game.statusTextArea.setText("Your pet is hungry, please feed it.\n");
                changeImage(pet.getHungryImage(), pet.getHungryFImage());
                startHungerDecay();
            }
            
        } 
        else if (getHappiness() == 0) {
            changeState("Angry");
            game.statusTextArea.setText("Your pet is angry, please play with it to make it happy.\n");
            changeImage(pet.getAngryImage(), pet.getAngryFImage());
        }
        else if (getState().equals("Hungry") && getFullness() > 0) {
            changeState("Normal");
            game.statusTextArea.setText("Your pet is no longer hungry.\n");
            changeImage(pet.getRegularImage(), pet.getFlippedImage());
            stopHungerDecay();
        }
        else if (getState().equals("Angry") && getHappiness() > maxHappiness / 2) {
            changeState("Normal");
            System.out.println("Stuff");
            game.statusTextArea.setText("Your pet is no longer angry.\n");
            changeImage(pet.getRegularImage(), pet.getFlippedImage());
        }
        /** Checking warnings for low stats */
        if(getHealth() < maxHealth * 0.25) {
            game.statusTextArea.setText("Warning: Low Health!\n");
        }
        if(getSleep() < maxSleep * 0.25) {
            game.statusTextArea.setText("Warning: Low Sleep!\n");
        }
        if(getFullness() < maxFullness * 0.25) {
            game.statusTextArea.setText("Warning: Low Fullness!\n");
        }
        if(getHappiness() < maxHappiness * 0.25) {
            game.statusTextArea.setText("Warning: Low Happiness!\n");
        }
    }
    /** 
    * Timeline that triggers periodic health and happiness loss when the pet is in a hungry state. 
    * It continues running until the pet is fed.
    */
    private Timeline hungerDecayTimer;
    /**
     * Starts a timeline that reduces health and happiness while pet is hungry.
     */
    private void startHungerDecay() {
        hungerDecayTimer = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            if (getFullness() == 0) {
                updateStatistics(-5, 0, 0, -(int)(happinessDecayRate + happinessDecayRate * 0.5));
            }
        }));
        hungerDecayTimer.setCycleCount(Timeline.INDEFINITE);
        hungerDecayTimer.play();
    }
    /**
     * Stops the hunger decay timeline if it's running.
     */
    private void stopHungerDecay() {
        if (hungerDecayTimer != null) {
            hungerDecayTimer.stop();
        }
    }
    /** 
    * Flag indicating whether the pet was hungry before it went to sleep. 
    * Used to restore the correct state after waking up.
    */
    private boolean isHungryDuringSleep = false;
    /** 
    * Flag indicating whether the pet was angry before it went to sleep. 
    * Used to restore the correct state after waking up.
    */
    private boolean isAngryDuringSleep = false;
    /**
     * Initiates pet sleep behavior to restore sleep stat and transition back to normal state.
     */
    private void sleepRecover() {
        isHungryDuringSleep = getState().equals("Hungry");
        isAngryDuringSleep = getState().equals("Angry");
        changeState("Sleeping");
        game.statusTextArea.appendText("Pet is going into sleep");
        if (!sleepPenaltyApplied) {
            sleepPenaltyApplied = true;
            updateStatistics(-10, 0, 0, 0);
        }
        String sleepSprite = pet.getSleepImage();
        /** Keeping the pet in sleep mode until health reaches max */
        sleepRecovery = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            changeImage(sleepSprite, "");
            if (getSleep() < maxSleep) {
                sleep.set(validateStat(sleep.get() + 12, maxSleep));
            }
            /** When fully restored, wake up the pet */
            if (getSleep() >= maxSleep) {
                isSleeping = false;
                sleepPenaltyApplied = false;
                sleepRecovery.stop();
                if (isHungryDuringSleep) {
                    changeState("Hungry");
                    changeImage(pet.getHungryImage(), pet.getHungryFImage());
                }
                else if (isAngryDuringSleep) {
                    changeState("Angry");
                    changeImage(pet.getAngryImage(), pet.getAngryFImage());
                }
                else {
                    changeState("Normal");
                    changeImage(pet.getRegularImage(), pet.getFlippedImage());
                }
                flipping.play();
                game.statusTextArea.setText("Your pet has woken up.\n");
            }
        }));
        sleepRecovery.setCycleCount(Timeline.INDEFINITE);
        sleepRecovery.play();
    }
    /**
     * Sets the appropriate pet image based on current state
     *
     * @param image image path to use
     * @param flipped alternate (flipped) image path
     */
    public void changeImage(String image, String flipped) {
        if (getState().equals("Dead") || getState().equals("Sleeping")) {
            flipping.stop();
            game.petImageView.setImage(new Image(getClass().getResource(image).toExternalForm()));
        }
        else {
            game.flippedImage = flipped;
            game.regularImage = image;
            game.petImageView.setImage(new Image(getClass().getResource(image).toExternalForm()));
        }
    }
     /**
     * Changes the pet's state dynamically.
     *
     * @param newState The new state of the pet
     */
    public void changeState(String newState) {
        state.set(newState);
    }
    /**
     * Ensures that stats remain within valid ranges.
     *
     * @param value The stat value to validate
     * @param max The maximum allowed value for the stat
     * @return A valid stat value within the allowed range
     */
    public int validateStat(int value, int max) {
        return Math.max(0, Math.min(max, value));
    }
    /**
     * Gets the current health value of the pet.
     * 
     * @return the current health as an integer
     */
    public int getHealth() {
        return health.get();
    }
    /**
     * Gets the current sleep value of the pet.
     * 
     * @return the current sleep level as an integer
     */
    public int getSleep() {
        return sleep.get();
    }
    /**
     * Gets the current fullness value of the pet.
     * 
     * @return the current fullness level as an integer
     */
    public int getFullness() {
        return fullness.get();
    }
    /**
     * Gets the current happiness value of the pet.
     * 
     * @return the current happiness level as an integer
     */
    public int getHappiness() {
        return happiness.get();
    }
    /**
     * Gets the current state of the pet.
     * 
     * @return the pet's current state as a string
     */
    public String getState() {
        return state.get();
    }
    /**
     * Gets the JavaFX property for health, allowing UI bindings.
     * 
     * @return the health property
     */
    public IntegerProperty healthProperty() {
        return health;
    }
    /**
     * Gets the JavaFX property for sleep, allowing UI bindings.
     * 
     * @return the sleep property
     */
    public IntegerProperty sleepProperty() {
        return sleep;
    }
    /**
     * Gets the JavaFX property for fullness, allowing UI bindings.
     * 
     * @return the fullness property
     */
    public IntegerProperty fullnessProperty() {
        return fullness;
    }
    /**
     * Gets the JavaFX property for happiness, allowing UI bindings.
     * 
     * @return the happiness property
     */
    public IntegerProperty happinessProperty() {
        return happiness;
    }
    /**
     * Gets the JavaFX property for the pet's state, allowing UI bindings.
     * 
     * @return the state property
     */
    public StringProperty stateProperty() {
        return state;
    }
    
    /**
    * Returns the maximum health for the pet
    * @return the max health value
    */
    public int getMaxHealth() {
        return maxHealth;
    }
    /**
    * Returns the maximum sleep value for the pet
    * @return the max sleep value
    */
    public int getMaxSleep() {
        return maxSleep;
    }
    /**
    * Returns the maximum fullness value for the pet.
    * @return the max fullness value
    */
    public int getMaxFullness() {
        return maxFullness;
    }
    /**
    * Returns the maximum happiness value for the pet
    * @return the max happiness value
    */
    public int getMaxHappiness() {
        return maxHappiness;
    }
}