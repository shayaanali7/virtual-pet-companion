package PetGame;

/**
 * <b>Represents an item to give your virtual pet in the game.</b>
 * <p>
 * An item has a name and a price.
 * This interface defines the properties that all items should have.
 * </p>
 * 
 * @author Alessia Pilla
 */
public interface Item {

    /**
     * Gets the name of the item
     *
     * @return The name of the item as a String
     */
    String getName();

    /**
     * Gets the price of the item
     *
     * @return The price of the item as a double
     */
    double getPrice();
    
}

