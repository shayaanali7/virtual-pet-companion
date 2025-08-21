package PetGame;

/**
 * The {@code Pet} interface defines the necessary attributes and behaviors for all virtual pets in the game.
 * It ensures that every pet type has:
 * <ul>
 *   <li>Statistics (health, happiness, fullness, sleep)</li>
 *   <li>A visual representation (sprite)</li>
 *   <li>State changes based on gameplay interactions</li>
 * </ul>
 * This interface will be implemented by specific pet classes.
 *
 * @author Ishaan Misra - Group 18
 */
public interface Pet {

    /** Pet statistics, managing health, happiness, fullness, and sleep. */
    
    /** The pet's sprite, used for visual representation. */
    PetSprite PetSprite = null;
    
    /** The current state of the pet (e.g., "Happy", "Hungry", "Sleeping"). */
    String state = "";

    /**
     * Retrieves the pet's current statistics.
     *
     * @return the {@code PetStatistics} object containing the pet's stats
     */
    PetStatistics getPetStats();

    void setPetStats(PetStatistics petStats);

    /**
     * Retrieves the pet's sprite object used for its visual representation.
     *
     * @return the {@code PetSprite} object associated with this pet
     */
    PetSprite getPetSprite();

    /**
     * Retrieves the pet's current state (e.g., "Happy", "Hungry", "Sleeping").
     *
     * @return the current state of the pet as a {@code String}
     */
    String getState();

    /**
     * Updates the pet's state.
     *
     * @param newState the new state to set for the pet
     */
    void setState(String newState);

    /**
     * Handles stat decay over time, updating the pet's statistics accordingly.
     */
    void update();

    String getRegularImage();

    String getFlippedImage();

    String getHappyImage();
    
    String getHungryImage();

    String getHungryFImage();

    String getSleepImage();

    String getDeadImage();

    String getAngryImage();

    String getAngryFImage();

}