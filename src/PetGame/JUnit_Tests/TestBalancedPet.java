package PetGame.JUnit_Tests;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.*;

import PetGame.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestBalancedPet {

    private BalancedPet pet;

    /**Mock stats to avoid triggering decay/hunger animations */
    private static class DummyStats extends PetStatistics {
        public DummyStats() {
            super(100, 100, 100, 100, 0, 0, 0, null, null, null);
        }

        @Override
        public void updateStatistics(int h, int s, int f, int hap) {
            health.set(health.get() + h);
            sleep.set(sleep.get() + s);
            fullness.set(fullness.get() + f);
            happiness.set(happiness.get() + hap);
        }
    }

    /** Launch JavaFX toolkit once before any tests run */
    @BeforeAll
    public static void initToolkit() {
        new JFXPanel(); /** Initializing JavaFX environment*/
    }

    @BeforeEach
    public void setUp() {
        ImageView dummyView = new ImageView(); /** Mock to satisfy constructor */
        pet = new BalancedPet(dummyView);
        pet.setPetStats(new DummyStats());
    }

    @Test
    public void testInitialStateIsNormal() {
        assertEquals("Normal", pet.getState(), "Initial state should be Normal");
    }

    @Test
    public void testSetStateChangesState() {
        pet.setState("Angry");
        assertEquals("Angry", pet.getState(), "State should change to Angry");
    }

    @Test
    public void testUpdateLowersStats() {
        int beforeSleep = pet.getPetStats().getSleep();
        int beforeFull = pet.getPetStats().getFullness();
        int beforeHappy = pet.getPetStats().getHappiness();

        pet.update();

        assertEquals(beforeSleep - 3, pet.getPetStats().getSleep(), "Sleep should drop by 3");
        assertEquals(beforeFull - 3, pet.getPetStats().getFullness(), "Fullness should drop by 3");
        assertEquals(beforeHappy - 3, pet.getPetStats().getHappiness(), "Happiness should drop by 3");
    }

    @Test
    public void testImagePathGetters() {
        assertEquals("/images/GreenSprite/GNormal.png", pet.getRegularImage());
        assertEquals("/images/GreenSprite/GNormalF.png", pet.getFlippedImage());
        assertEquals("/images/GreenSprite/GHappy.png", pet.getHappyImage());
        assertEquals("/images/GreenSprite/GHungry.png", pet.getHungryImage());
        assertEquals("/images/GreenSprite/GHungryF.png", pet.getHungryFImage());
        assertEquals("/images/GreenSprite/GSleep.png", pet.getSleepImage());
        assertEquals("/images/GreenSprite/GAngry.png", pet.getAngryImage());
        assertEquals("/images/GreenSprite/GAngryF.png", pet.getAngryFImage());
        assertEquals("/images/GreenSprite/GDead.png", pet.getDeadImage());
    }
}
