package PetGame.JUnit_Tests;

import com.google.gson.Gson;

import PetGame.RevivePet;

import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestRevivePet {

    private final String testFile = "test_temp_save.json";

    @BeforeAll
    public static void initJavaFX() {
        /**Initializes the JavaFX environment (avoids Toolkit not initialized) */
        new javafx.embed.swing.JFXPanel();
    }

    @BeforeEach
    public void setUp() throws IOException {
        Map<String, Object> petStats = new HashMap<>();
        petStats.put("health", 40.0);
        petStats.put("sleep", 30.0);
        petStats.put("fullness", 20.0);
        petStats.put("happiness", 10.0);
        petStats.put("state", "Sick");

        Map<String, Object> saveData = new HashMap<>();
        saveData.put("petName", "Testy");
        saveData.put("petType", "Balanced");
        saveData.put("petStats", petStats);

        try (FileWriter writer = new FileWriter(testFile)) {
            new Gson().toJson(saveData, writer);
        }
    }

    @Test
    public void testReviveSaveFile() throws Exception {
        /**Bypassing the Alert pop-up by running inside Platform.runLater */
        javafx.application.Platform.runLater(() -> {
            try {
                RevivePet revivePet = new RevivePet();

                Method method = RevivePet.class.getDeclaredMethod("reviveSaveFile", String.class);
                method.setAccessible(true);
                method.invoke(revivePet, testFile);

                /**Reading the file back to confirm */
                try (FileReader reader = new FileReader(testFile)) {
                    Map<String, Object> saveData = new Gson().fromJson(reader, Map.class);
                    Map<String, Double> petStats = (Map<String, Double>) saveData.get("petStats");

                    assertEquals(100, petStats.get("health").intValue());
                    assertEquals(100, petStats.get("sleep").intValue());
                    assertEquals(100, petStats.get("fullness").intValue());
                    assertEquals(100, petStats.get("happiness").intValue());
                    assertEquals("Normal", petStats.get("state"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception during test: " + e.getMessage());
            }
        });

        Thread.sleep(1000);
    }

    @AfterEach
    public void tearDown() {
        new File(testFile).delete();
    }
}
