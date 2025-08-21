package PetGame;

/**
 * <b>Manages the player's score.</b>
 * <p>
 * This class allows for incrasing, decreasing, resetting, and retrieving the plauer's score.
 * The score cannot be negative.
 * </p>
 * 
 * @author Julia Kasperek
 */
public class PlayerScore {
    // The player's score.
    public int score;

    /**
     * Constructs a {@code PlayerScore} object with an initial score of 0.
     */
    public PlayerScore() {
        this.score = 0;
    }

    /**
     * Increases the player's score by the specified value.
     * 
     * @param value the amount to increase the score by
     */
    public void increaseScore(int value) {
        score += value;
    }

    /**
     * Decreases the player's score by the specified value, ensuring the score does not become negative.
     * 
     * @param value the amount to decrease the score by
     */
    public void decreaseScore(int value) {
        score = Math.max(0, score - value);
    }

    /**
     * Resets the player's score to 0.
     */
    public void resetScore() {
        this.score = 0;
    }

    /**
     * Retrieves the current score of the player.
     * 
     * @return score The player's current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the player's score to the specified value.
     * 
     * @param score the new score to set
     */
    public void setScore(int score) {
        this.score = score;
    }
}
