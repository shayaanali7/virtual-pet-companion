
package PetGame;
/**
 * <b>Custom exception for insufficient funds during a transaction.</b>
 * <p>
 * Custom exception thrown when a player attempts to make a purchase but does not have enough coins.
 * This exception is used to indicate insufficient funds for a transaction.
 * </p>
 * 
 * @author Alessia Pilla
 */
public class NotEnoughCoinsException extends Exception {
    /**
     * Constructs a new NotEnoughCoinsException with the specified message.
     * 
     * @param message The message which provides additional information about the exception.
     */
    public NotEnoughCoinsException(String message) {
        super(message);
    }
}