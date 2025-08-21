package PetGame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This {@code BalancedPet} class represents an overall well rounded pet type in
 * the game.
 * This pet has moderate stats and balanced decay rates, making it a good choice
 * for players who prefer a more stable and relaxed experience.
 *
 * @author Ishaan Misra
 */
public class BalancedPet implements Pet {
    /** The ImageView to display the pet sprite on the screen */
    transient ImageView petImageView;

    /** The pet's statistics (health, hunger, etc.) */
    private PetStatistics petStats;

    /** The graphical sprite handler for the pet. */
    private PetSprite petSprite;

    /**
     * The current emotional/physical state of the pet (e.g. Normal, Hungry, etc.)
     */
    private String state;

    /** Path to the pet's regular (default) image. */
    private String regularImage = "/images/GreenSprite/GNormal.png";

    /** Path to the flipped version of the regular image. */
    private String flippedImage = "/images/GreenSprite/GNormalF.png";

    /** Path to the pet's happy state image. */
    private String happyImage = "/images/GreenSprite/GHappy.png";

    /** Path to the pet's hungry state image. */
    private String hungryImage = "/images/GreenSprite/GHungry.png";

    /** Path to the flipped version of the hungry image. */
    private String hungryFImage = "/images/GreenSprite/GHungryF.png";

    /** Path to the pet's sleeping state image. */
    private String sleepImage = "/images/GreenSprite/GSleep.png";

    /** Path to the pet's angry state image. */
    private String angryImage = "/images/GreenSprite/GAngry.png";

    /** Path to the flipped version of the angry image. */
    private String angryFImage = "/images/GreenSprite/GAngryF.png";

    /** Path to the pet's dead state image. */
    private String deadImage = "/images/GreenSprite/GDead.png";

    /**
     * The constructor. It constructs a BalancedPet using the provided
     * {@code ImageView} for rendering.
     * Initializes the pet's state and sets up the sprite display.
     *
     * @param petImage the ImageView that will display the pet
     */
    public BalancedPet(ImageView petImage) {
        this.petImageView = petImage;
        this.petSprite = new PetSprite(petImageView, new Image(getClass().getResource(regularImage).toExternalForm()));
        this.state = "Normal";
    }

    /**
     * Returns the statistics for this pet
     *
     * @return the {@code PetStatistics} object containing the pet's statistics
     */
    @Override
    public PetStatistics getPetStats() {
        return petStats;
    }

    /**
     * Sets the statistics for this pet
     *
     * @param petStats the {@code PetStatistics} to assign
     */
    public void setPetStats(PetStatistics petStats) {
        this.petStats = petStats;

    }

    /**
     * Returns the pet's sprite handler
     *
     * @return the {@code PetSprite} managing the pet's image
     */
    @Override
    public PetSprite getPetSprite() {
        return petSprite;
    }

    /**
     * Returns the current state of the pet.
     *
     * @return a string representing the pet's current state
     */
    @Override
    public String getState() {
        return state;
    }

    /**
     * Updates the pet's current state.
     *
     * @param newState the new state to apply
     */
    @Override
    public void setState(String newState) {
        this.state = newState;
    }

    /**
     * Applies periodic stat decay over time to simulate time progression.
     * The balanced pets decay moderately across hunger, sleep, and happiness
     */
    @Override
    public void update() {
        petStats.updateStatistics(0, -3, -3, -3); /** The balanced pet with steady stat decay rates */
    }

    /**
     * Returns the file path of the pet's regular (default) image.
     *
     * @return the regular image file path
     */
    @Override
    public String getRegularImage() {
        return regularImage;
    }

    /**
     * Returns the file path of the flipped version of the pet's regular image.
     *
     * @return the flipped image file path
     */
    @Override
    public String getFlippedImage() {
        return flippedImage;
    }

    /**
     * Returns the file path of the pet's happy state image.
     *
     * @return the happy image file path
     */
    @Override
    public String getHappyImage() {
        return happyImage;
    }

    /**
     * Returns the file path of the pet's hungry state image.
     *
     * @return the hungry image file path
     */
    @Override
    public String getHungryImage() {
        return hungryImage;
    }

    /**
     * Returns the file path of the flipped version of the pet's hungry image.
     *
     * @return the flipped hungry image file path
     */
    @Override
    public String getHungryFImage() {
        return hungryFImage;
    }

    /**
     * Returns the file path of the pet's sleeping state image.
     *
     * @return the sleep image file path
     */
    @Override
    public String getSleepImage() {
        return sleepImage;
    }

    /**
     * Returns the file path of the pet's angry state image.
     *
     * @return the angry image file path
     */
    @Override
    public String getAngryImage() {
        return angryImage;
    }

    /**
     * Returns the file path of the flipped version of the pet's angry image.
     *
     * @return the flipped angry image file path
     */
    @Override
    public String getAngryFImage() {
        return angryFImage;
    }

    /**
     * Returns the file path of the pet's dead state image.
     *
     * @return the dead image file path
     */
    @Override
    public String getDeadImage() {
        return deadImage;
    }
}
