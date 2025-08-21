package PetGame.JUnit_Tests;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import PetGame.PetStatistics;

import static org.junit.jupiter.api.Assertions.*;

public class TestPetStatistics {

    private PetStatistics stats;

    /**Initializing JavaFX Toolkit (runs once before everything) */
    @BeforeAll
    public static void initJFX() {
        new JFXPanel(); /**Initializing the JavaFX runtime */
    }

    @BeforeEach
    public void setUp() {
        /** Constructing with valid initial values */
        stats = new PetStatistics(
                10, 
                10, 
                10, 
                10, 
                1,  
                1,  
                1,  
                null, /** Game */ 
                new Timeline(), /**Timeline that won't play since it's not started */
                null  /**Pet */
        );

        /**Setting initial values directly */
        stats.healthProperty().set(5);
        stats.sleepProperty().set(5);
        stats.fullnessProperty().set(5);
        stats.happinessProperty().set(5);
    }

    @Test
    public void testInitialStatValues() {
        assertEquals(5, stats.getHealth());
        assertEquals(5, stats.getSleep());
        assertEquals(5, stats.getFullness());
        assertEquals(5, stats.getHappiness());
    }

    @Test
        public void testStatValidationLowerBound() {
        stats.healthProperty().set(-100);
        stats.sleepProperty().set(-10);
        stats.fullnessProperty().set(-20);
        stats.happinessProperty().set(-50);

        assertEquals(-100, stats.getHealth());
        assertEquals(-10, stats.getSleep());
        assertEquals(-20, stats.getFullness());
        assertEquals(-50, stats.getHappiness());
    }

    @Test
    public void testStatValidationUpperBound() {
        stats.healthProperty().set(1000);
        stats.sleepProperty().set(999);
        stats.fullnessProperty().set(500);
        stats.happinessProperty().set(777);

        assertEquals(1000, stats.getHealth());
        assertEquals(999, stats.getSleep());
        assertEquals(500, stats.getFullness());
        assertEquals(777, stats.getHappiness());
    }

    @Test
    public void testMaxStatAccessors() {
        assertEquals(10, stats.getMaxHealth());
        assertEquals(10, stats.getMaxSleep());
        assertEquals(10, stats.getMaxFullness());
        assertEquals(10, stats.getMaxHappiness());
    }

    @Test
    public void testStatChangesDirectly() {
        stats.healthProperty().set(9);
        stats.sleepProperty().set(4);
        stats.fullnessProperty().set(8);
        stats.happinessProperty().set(6);

        assertEquals(9, stats.getHealth());
        assertEquals(4, stats.getSleep());
        assertEquals(8, stats.getFullness());
        assertEquals(6, stats.getHappiness());
    }

    @Test
    public void testStateBinding() {
        assertNotNull(stats.healthProperty());
        assertNotNull(stats.sleepProperty());
        assertNotNull(stats.fullnessProperty());
        assertNotNull(stats.happinessProperty());
    }
}
