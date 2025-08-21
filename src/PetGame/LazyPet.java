package PetGame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The {@code LazyPet} class represents a laid back pet in the game.
 * Lazy pets sleep often and tend to lose happiness quickly if not taken care of.
 * This pet is suited for players who prefer a pet that requires less frequent interaction but more attention to emotional needs
 * 
 * @author Ishaan Misra
 */
public class LazyPet implements Pet {

    /** The ImageView used to visually render the pet */
    transient ImageView petImageView;

    /** The statistics for this pet, including the health, sleep, fullness, and happiness */
    private PetStatistics petStats;

    /** Controls the visual sprite updates for this pet. */
    private PetSprite petSprite;

    /** Represents the current behavioral/emotional state of the pet. */
    private String state;

    /** Path to the pet's regular (default) image. */
    private String regularImage = "/images/yellowSprite/YNormalSprite.png";

    /** Path to the flipped version of the regular image. */
    private String flippedImage = "/images/yellowSprite/YNormalSpriteFlipped.png";

    /** Path to the pet's happy state image. */
    private String happyImage = "/images/yellowSprite/YHappySprite.png";

    /** Path to the pet's hungry state image. */
    private String hungryImage = "/images/yellowSprite/YHungrySprite.png";

    /** Path to the flipped version of the hungry image. */
    private String hungryFImage = "/images/yellowSprite/YHungrySpriteFlipped.png";

    /** Path to the pet's sleeping state image. */
    private String sleepImage = "/images/yellowSprite/YSleepSprite.png";

    /** Path to the pet's angry state image. */
    private String angryImage = "/images/yellowSprite/YAngrySprite.png";

    /** Path to the flipped version of the angry image. */
    private String angryFImage = "/images/yellowSprite/YAngrySpriteFlipped.png";

    /** Path to the pet's dead state image. */
    private String deadImage = "/images/yellowSprite/YDeadSprite.png";

    /**
     * The constructor. It constructs a {@code LazyPet} and sets up its sprite and default state.
     *
     * @param petImage the {@code ImageView} used to display the pet
     */
    public LazyPet(ImageView petImage) {
        this.petImageView = petImage;
        this.petSprite = new PetSprite(petImageView, new Image(getClass().getResource(regularImage).toExternalForm()));
        this.state = "Normal";
    }

    /**
     * Returns the pet's statistics object.
     *
     * @return the {@code PetStatistics} for this pet
     */
    @Override
    public PetStatistics getPetStats() {
        return petStats;
    }

    /**
     * Sets the pet's statistics
     *
     * @param petStats the {@code PetStatistics} to assign to this pet
     */
    public void setPetStats(PetStatistics petStats) {
        this.petStats = petStats;

    }

    /**
     * Returns the pet's visual sprite handler
     *
     * @return the {@code PetSprite} associated with this pet
     */
    @Override
    public PetSprite getPetSprite() {
        return petSprite;
    }

    /**
     * Returns the pet's current state as a string.
     *
     * @return the current state of the pet
     */
    @Override
    public String getState() {
        return state;
    }

    /**
     * Updates the pet's state.
     *
     * @param newState the new state to set
     */
    @Override
    public void setState(String newState) {
        this.state = newState;
    }

    /**
     * Applies stat decay for the lazy pet type.
     * Lazy pets lose happiness more rapidly and need to sleep frequently.
     */
    @Override
    public void update() {
        petStats.updateStatistics(0, -2, -3, -5); /** The lazy pet needs attention or loses happiness because of the high happiness decay rate */
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
