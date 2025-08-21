package PetGame;

/**
 * <b>Represents a gift item that can be purchased from the Item Shop.</b>
 * <p>
 * Gift items increase the pet's happiness levels.
 * This class extends the {@code PurchasableItem} class, inheriting its properties while adding additional attributes specific to gift items.
 * </p>
 * 
 * @author Alessia Pilla
 */
public class GiftItem extends PurchasableItem {

    private int happinessValue;
    private String giftType;

    /**
     * Constructs a {@code GiftItem} with the specified name, cost, happiness value, and gift type
     *
     * @param itemName        The name of the gift item
     * @param coinsRequired   The number of coins required to purchase the item
     * @param happinessValue  The happiness value provided by the gift
     * @param giftType        The type of gift (e.g., "Bow", "Trampoline")
     */
    public GiftItem(String itemName, int coinsRequired, int happinessValue, String giftType) {
        super(itemName, coinsRequired);
        this.happinessValue = happinessValue;
        this.giftType = giftType;
    }

    /**
     * Gets the happiness value of the gift item
     *
     * @return The happiness value as an {@code int}
     */
    public int getHappinessValue() {
        return happinessValue;
    }

    /**
     * Gets the type of gift
     *
     * @return The type of gift as a {@code String}
     */
    public String getGiftType() {
        return giftType;
    }
}
