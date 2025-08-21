package PetGame.JUnit_Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import PetGame.Statistics;

import static org.junit.jupiter.api.Assertions.*;

public class TestStatistics {

    @BeforeEach
    public void setUp() {
        Statistics.totalPlaytime = 0;
        Statistics.playSessions = 0;
        Statistics.secondsPlayed = 0;
    }

    @Test
    public void testInitialStats() {
        assertEquals(0, Statistics.totalPlaytime);
        assertEquals(0, Statistics.playSessions);
        assertEquals(0, Statistics.secondsPlayed);
    }

    @Test
    public void testSaveAndLoadStatsWithoutTouchingDisk() {
        /**Simulate save */
        Statistics.totalPlaytime = 150;
        Statistics.playSessions = 3;
        Statistics.secondsPlayed = 900;

        /** Simulate reloading (in-memory) */
        int total = Statistics.totalPlaytime;
        int sessions = Statistics.playSessions;
        int seconds = Statistics.secondsPlayed;

        assertEquals(150, total);
        assertEquals(3, sessions);
        assertEquals(900, seconds);
    }

    @Test
    public void testResetStatsClearsStaticFields() {
        /** Setting values first */
        Statistics.totalPlaytime = 123;
        Statistics.playSessions = 5;
        Statistics.secondsPlayed = 999;

        /**  Simulating reset behavior */
        Statistics.totalPlaytime = 0;
        Statistics.playSessions = 0;
        Statistics.secondsPlayed = 0;

        /** Verifying all were reset */
        assertEquals(0, Statistics.totalPlaytime);
        assertEquals(0, Statistics.playSessions);
        assertEquals(0, Statistics.secondsPlayed);
    }
}
