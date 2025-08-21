package PetGame;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>The SaveGame class handles saving the current game state to a file.
 * It collects data about the pet, player, and game statistics, serializes it into JSON format,
 * and writes it to a specified file.
 * 
 * Features:
 * - Extracts pet statistics, player statistics, and game data.
 * - Converts the game state into a JSON representation.
 * - Saves the JSON data to a file for later retrieval.
 * 
 * Dependencies:
 * - Gson library for JSON serialization.</p>
 * 
 * Usage:
 * 1. Call {@link #initializeSaveGame(Game)} to populate the SaveGame object with the current game state.
 * 2. Call {@link #saveToFile(String)} to save the game state to a file.
 * 
 * @author Novak Vukojicic
 */
public class SaveGame {

    public int petType; // The type of pet selected by the player
    public String petName; // The name of the pet
    public Map<String, Object> petStats; // A map containing the pet's statistics
    public Map<String, Object> player; // A map containing the player's statistics
    public Map<String, Object> gameStats; // A map containing additional game statistics (if needed)

    /**
     * Initializes the SaveGame object with the current game state.
     * 
     * @param game The current game instance containing the pet, player, and game data.
     */
    public void initializeSaveGame(Game game) {
        this.petType = game.petType; // Set the pet type
        this.petName = game.petName; // Set the pet name
        this.petStats = getPetStats(game.pet.getPetStats()); // Extract pet statistics
        this.player = getPlayerStats(game.player); // Extract player statistics
    }

    /**
     * Extracts the pet's statistics and stores them in a map.
     * If the pet is in a "Sleeping" state, it is converted to "Normal" for saving purposes.
     * 
     * @param statistics The PetStatistics object containing the pet's current stats.
     * @return A map containing the pet's health, sleep, fullness, happiness, and state.
     */
    public Map<String, Object> getPetStats(PetStatistics statistics) {
        Map<String, Object> statsMap = new HashMap<>();
        statsMap.put("health", statistics.getHealth());
        statsMap.put("sleep", statistics.getSleep());
        statsMap.put("fullness", statistics.getFullness());
        statsMap.put("happiness", statistics.getHappiness());
        if (statistics.getState().equals("Sleeping")) {
            statsMap.put("state", "Normal"); // Convert "Sleeping" state to "Normal"
        } else {
            statsMap.put("state", statistics.getState());
        }
        return statsMap;
    }

    /**
     * Extracts the player's statistics and stores them in a map.
     * 
     * @param player The Player object containing the player's current stats and inventory.
     * @return A map containing the player's money, score, and inventory items.
     */
    public Map<String, Object> getPlayerStats(Player player) {
        Map<String, Object> playerMap = new HashMap<>();
        playerMap.put("money", player.getCoins()); // Add player's money
        playerMap.put("score", player.getScore().getScore()); // Add player's score
        playerMap.put("inventory", player.inventory.items); // Add player's inventory
        return playerMap;
    }

    /**
     * Saves the current game state to a file in JSON format.
     * 
     * @param filename The name of the file to save the game state to.
     */
    public void saveToFile(String filename) {
        Gson gson = new Gson(); // Create a Gson instance for JSON serialization
        String json = gson.toJson(this); // Convert the SaveGame object to JSON

        try (java.io.FileWriter writer = new java.io.FileWriter(filename)) {
            writer.write(json); // Write the JSON data to the specified file
        } catch (java.io.IOException e) {
            e.printStackTrace(); // Print the stack trace if an error occurs
        }
    }
}
