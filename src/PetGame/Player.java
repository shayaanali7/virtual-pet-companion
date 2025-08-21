package PetGame;

/**
 * <b>Represents the player in the game.</b>
 * <p>
 * Manages the player's attributes such as score, coins, and inventory.
 * </p>
 * 
 * @author Julia Kasperek
 */
public class Player {
    // The player's score.
    public PlayerScore score;
    // The player's coin balance.
    protected double playerCoins;
    // The player's inventory.
    public Inventory inventory;
    
    /**
    * Constructs a {@code Player} object with a specified amount of starting coins.
    * <p>
    * This constructor also initializes the player's {@code PlayerScore} object 
    * and {@code Inventory} object, setting them to default values.
    * </p>
    *
    * @param playerCoins The initial amount of coins the player starts with.
    */
    public Player(double playerCoins) {
        this.score = new PlayerScore();
        this.playerCoins = playerCoins;
        this.inventory = new Inventory();
    }
    
    /**
    * Adds a specified amount of coins to the player's coins balance.
    *
    * @param amount The amount of coins to add to the player's balance.
    */
    public void addCoins(double amount) {
        this.playerCoins += amount;
    }

    /**
    * Subtracts a specified amount of coins from the player's coins balance.
    * <p>
    * If the player does not have enough coins, it will print a warning message.
    * </p>
    *
    * @param amount The amount of coins to subtract from the player's balance.
    */
    public void subtractCoins(double amount) {
        if (playerCoins >= amount) {
            this.playerCoins -= amount;
        } else {
            System.out.println("Not enough coins.");
        }
    }

    /**
    * Get the current amount of coins that the player has.
    *
    *@return The player's current coin balance
    */
    public double getCoins() {
        return playerCoins;
    }

    /**
     * Sets the player's coin balance to a specified amount.
     * 
     * @param amount The new coin balance for the player.
     */
    public void setCoins(double amount) {
        this.playerCoins = amount;

    }

    /**
    * Gets the current score of the player.
    *
    *@return The player's current score
    */
    public PlayerScore getScore() {
        return score;
    }

    /**
     * Gets the player's inventory.
     * 
     * @return The player's {@code Inventory} object, containing the items possessed by the player.
     */
    public Inventory getInventory() {
        return inventory;
    }
}
