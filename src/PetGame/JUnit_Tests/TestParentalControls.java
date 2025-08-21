package PetGame.JUnit_Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import PetGame.ParentalControls;

import static org.junit.jupiter.api.Assertions.*;

public class TestParentalControls {

    private ParentalControls parentalControls;

    @BeforeEach
    public void setUp() {
        parentalControls = new ParentalControls();
    }

    @Test
    public void testAddPlaySessionUpdatesTotalsCorrectly() {
        assertEquals(0, parentalControls.totalPlaytime);
        assertEquals(0, parentalControls.playSessions);

        parentalControls.addPlaySession(2);
        assertEquals(2, parentalControls.totalPlaytime);
        assertEquals(1, parentalControls.playSessions);

        parentalControls.addPlaySession(3);
        assertEquals(5, parentalControls.totalPlaytime);
        assertEquals(2, parentalControls.playSessions);
    }
}
