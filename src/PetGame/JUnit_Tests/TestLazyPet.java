 package PetGame.JUnit_Tests;


import javafx.embed.swing.JFXPanel;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.*;

import PetGame.LazyPet;
import PetGame.PetStatistics;

import static org.junit.jupiter.api.Assertions.*;

public class TestLazyPet {

    private LazyPet pet;

    /** Mock stats to prevent actual animation or game logic from interfering with tests */
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

    @BeforeAll
    public static void initToolkit() {
        new JFXPanel(); /** Initializing JavaFX runtime for ImageView support */
    }

    @BeforeEach
    public void setUp() {
        ImageView dummyImageView = new ImageView();
        pet = new LazyPet(dummyImageView);
        pet.setPetStats(new DummyStats());
    }

    @Test
    public void testInitialStateIsNormal() {
        assertEquals("Normal", pet.getState(), "Initial state should be Normal");
    }

    @Test
    public void testSetStateChangesState() {
        pet.setState("Sleeping");
        assertEquals("Sleeping", pet.getState(), "State should change to Sleeping");
    }

    @Test
    public void testUpdateLowersStatsProperly() {
        int beforeSleep = pet.getPetStats().getSleep();
        int beforeFull = pet.getPetStats().getFullness();
        int beforeHappy = pet.getPetStats().getHappiness();

        pet.update();

        assertEquals(beforeSleep - 2, pet.getPetStats().getSleep(), "Sleep should decrease by 2");
        assertEquals(beforeFull - 3, pet.getPetStats().getFullness(), "Fullness should decrease by 3");
        assertEquals(beforeHappy - 5, pet.getPetStats().getHappiness(), "Happiness should decrease by 5");
    }

    @Test
    public void testImagePathGetters() {
        assertEquals("/images/yellowSprite/YNormalSprite.png", pet.getRegularImage());
        assertEquals("/images/yellowSprite/YNormalSpriteFlipped.png", pet.getFlippedImage());
        assertEquals("/images/yellowSprite/YHappySprite.png", pet.getHappyImage());
        assertEquals("/images/yellowSprite/YHungrySprite.png", pet.getHungryImage());
        assertEquals("/images/yellowSprite/YHungrySpriteFlipped.png", pet.getHungryFImage());
        assertEquals("/images/yellowSprite/YSleepSprite.png", pet.getSleepImage());
        assertEquals("/images/yellowSprite/YAngrySprite.png", pet.getAngryImage());
        assertEquals("/images/yellowSprite/YAngrySpriteFlipped.png", pet.getAngryFImage());
        assertEquals("/images/yellowSprite/YDeadSprite.png", pet.getDeadImage());
    }
}
