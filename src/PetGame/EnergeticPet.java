package PetGame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The {@code EnergeticPet} class represents an energetic pet type in the game.
 * This pet is very active, meaning it loses fullness quickly.
 * Energetic pets experience faster stat decay, especially in fullness, requiring more frequent care from the player
 * This pet type is great for players who want a challenge
 *
 * @author Ishaan Misra
 */
public class EnergeticPet implements Pet {

    /** The ImageView responsible for displaying the pet's sprite */
    transient ImageView petImageView;

    /** The pet's statistics (health, sleep, fullness, happiness) */
    public PetStatistics petStats;

    /** The sprite manager for this pet, it handles the visual updates. */
    transient PetSprite petSprite;

    /** The pet's current behavioral/emotional state */
    private String state;

    /** Path to the pet's regular (default) image. */
    private String regularImage = "/images/blueSprite/BNormal.png";

    /** Path to the flipped version of the regular image. */
    private String flippedImage = "/images/blueSprite/BNormalF.png";

    /** Path to the pet's happy state image. */
    private String happyImage = "/images/blueSprite/BHappy.png";

    /** Path to the pet's hungry state image. */
    private String hungryImage = "/images/blueSprite/BHungry.png";

    /** Path to the flipped version of the hungry image. */
    private String hungryFImage = "/images/blueSprite/BHungryF.png";

    /** Path to the pet's sleeping state image. */
    private String sleepImage = "/images/blueSprite/BSleep.png";

    /** Path to the pet's angry state image. */
    private String angryImage = "/images/blueSprite/BAngry.png";

    /** Path to the flipped version of the angry image. */
    private String angryFImage = "/images/blueSprite/BAngryF.png";

    /** Path to the pet's dead state image. */
    private String deadImage = "/images/blueSprite/BDead.png";




    /**
     * The constructor. It constructs an {@code EnergeticPet} and initializes its visual representation.
     * Sets the default state to "Normal" and displays the default sprite.
     *
     * @param petImage the ImageView used to render the pet on the screen
     */
    public EnergeticPet(ImageView petImage) {
        this.petImageView = petImage;
        this.petSprite = new PetSprite(petImageView, new Image(getClass().getResource(regularImage).toExternalForm()));
        this.state = "Normal";
    }

    /**
     * Gets the pet's current statistics.
     *
     * @return the {@code PetStatistics} associated with this pet
     */
    @Override
    public PetStatistics getPetStats() {
        return petStats;
    }

    /**
     * Sets the pet's statistics.
     *
     * @param petStats the {@code PetStatistics} to be associated with this pet
     */
    public void setPetStats(PetStatistics petStats) {
        this.petStats = petStats;

    }

    /**
     * Returns the pet's sprite manager for image updates.
     *
     * @return the {@code PetSprite} used to control the pet's images
     */
    @Override
    public PetSprite getPetSprite() {
        return petSprite;
    }

    /**
     * Returns the current state of the pet.
     *
     * @return the pet's current state as a String
     */
    @Override
    public String getState() {
        return state;
    }

    /**
     * Sets the current state of the pet.
     *
     * @param newState the new state of the pet
     */
    @Override
    public void setState(String newState) {
        this.state = newState;
    }

    /**
     * Updates the pet's stats over time. The energetic pets lose fullness faster.
     * This simulates time passing in the game loop.
     */
    @Override
    public void update() {
        petStats.updateStatistics(0, -3, -6, -4); /** The energetic pet needs frequent food since the decay rate for fullness is higher */
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
