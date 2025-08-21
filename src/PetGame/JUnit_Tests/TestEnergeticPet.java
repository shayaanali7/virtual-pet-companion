 package PetGame.JUnit_Tests;


import javafx.embed.swing.JFXPanel;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.*;

import PetGame.EnergeticPet;
import PetGame.PetStatistics;

import static org.junit.jupiter.api.Assertions.*;

public class TestEnergeticPet {

    private EnergeticPet pet;

    /** Mock PetStatistics to avoid actual decay and threading */
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
        new JFXPanel(); /** Initializing JavaFX */
    }

    @BeforeEach
    public void setUp() {
        ImageView dummyImageView = new ImageView();
        pet = new EnergeticPet(dummyImageView);
        pet.setPetStats(new DummyStats());
    }

    @Test
    public void testInitialStateIsNormal() {
        assertEquals("Normal", pet.getState(), "Initial state should be Normal");
    }

    @Test
    public void testSetStateChangesState() {
        pet.setState("Hungry");
        assertEquals("Hungry", pet.getState(), "State should be Hungry");
    }

    @Test
    public void testUpdateLowersStatsFaster() {
        int beforeSleep = pet.getPetStats().getSleep();
        int beforeFullness = pet.getPetStats().getFullness();
        int beforeHappiness = pet.getPetStats().getHappiness();

        pet.update();

        assertEquals(beforeSleep - 3, pet.getPetStats().getSleep(), "Sleep should drop by 3");
        assertEquals(beforeFullness - 6, pet.getPetStats().getFullness(), "Fullness should drop by 6");
        assertEquals(beforeHappiness - 4, pet.getPetStats().getHappiness(), "Happiness should drop by 4");
    }

    @Test
    public void testImagePathGetters() {
        assertEquals("/images/blueSprite/BNormal.png", pet.getRegularImage());
        assertEquals("/images/blueSprite/BNormalF.png", pet.getFlippedImage());
        assertEquals("/images/blueSprite/BHappy.png", pet.getHappyImage());
        assertEquals("/images/blueSprite/BHungry.png", pet.getHungryImage());
        assertEquals("/images/blueSprite/BHungryF.png", pet.getHungryFImage());
        assertEquals("/images/blueSprite/BSleep.png", pet.getSleepImage());
        assertEquals("/images/blueSprite/BAngry.png", pet.getAngryImage());
        assertEquals("/images/blueSprite/BAngryF.png", pet.getAngryFImage());
        assertEquals("/images/blueSprite/BDead.png", pet.getDeadImage());
    }
}
