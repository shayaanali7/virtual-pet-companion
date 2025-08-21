package PetGame.JUnit_Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import PetGame.PlayerScore;

public class PlayerScoreTest {

    private PlayerScore playerScore;

    @BeforeEach
    public void setUp() {
        // Initialize a new PlayerScore before each test
        playerScore = new PlayerScore();
    }

    @Test
    public void testIncreaseScore() {
        // Increase the score by 10
        playerScore.increaseScore(10);

        // Verify that the score is 10
        assertEquals(10, playerScore.getScore(), "The player's score should be 10 after increasing by 10.");
    }

    @Test
    public void testDecreaseScore() {
        // Set the score to 20 and decrease it by 5
        playerScore.setScore(20);
        playerScore.decreaseScore(5);

        // Verify that the score is now 15
        assertEquals(15, playerScore.getScore(), "The player's score should be 15 after decreasing by 5.");
    }

    @Test
    public void testDecreaseScoreCannotGoNegative() {
        // Set the score to 5 and decrease it by 10
        playerScore.setScore(5);
        playerScore.decreaseScore(10);

        // Verify that the score does not go below 0
        assertEquals(0, playerScore.getScore(), "The player's score should not be negative.");
    }

    @Test
    public void testResetScore() {
        // Set the score to 50 and reset it
        playerScore.setScore(50);
        playerScore.resetScore();

        // Verify that the score is reset to 0
        assertEquals(0, playerScore.getScore(), "The player's score should be reset to 0.");
    }

    @Test
    public void testSetScore() {
        // Set the score to 100
        playerScore.setScore(100);

        // Verify that the score is correctly set to 100
        assertEquals(100, playerScore.getScore(), "The player's score should be set to 100.");
    }

    @Test
    public void testInitialScore() {
        // Verify that the initial score is 0 (as defined in the constructor)
        assertEquals(0, playerScore.getScore(), "The player's initial score should be 0.");
    }
}
