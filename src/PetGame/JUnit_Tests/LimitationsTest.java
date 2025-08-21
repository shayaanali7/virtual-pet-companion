package PetGame.JUnit_Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import PetGame.Limitations;

public class LimitationsTest {
	private Limitations limitations;
	private static final String test = "test.json";

	@BeforeEach
    public void setUp() {
		limitations = new Limitations();
        
        File file = new File(test);
        if (!file.exists()) {
        	try {
        		file.createNewFile();
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        }
        
        Limitations.enabled = false;
        Limitations.startHour = 10;
        Limitations.endHour = 16;
    }

	@Test
    public void testIsBlockTimeNow() {
        // Get the current system time
        LocalTime currentTime = LocalTime.now();
        
        // Simulate the system time during the test (if currentTime is between 10 AM to 4 PM)
        if (currentTime.isAfter(LocalTime.of(10, 0)) && currentTime.isBefore(LocalTime.of(16, 0))) {
            assertTrue(Limitations.isBlockedTimeNow());
        }
    }
	
	@Test
	public void testSaveAndLoad() {
		Limitations.enabled = true;
		Limitations.startHour = 5;
		Limitations.endHour = 6;
		Limitations.save();
		
		Limitations.load();
		
		assertEquals(true, Limitations.enabled);
		assertEquals(5, Limitations.startHour);
		assertEquals(6, Limitations.endHour);

	}
}
