package PetGame;

/**
 * <b>Represents a food item that can be purchased from the item shop.</b>
 * <p>
 * Each food item has a type and a nutritional value that affects the pet's energy levels.
 * This class extends {@link PurchasableItem}, inheriting purchasing properties while
 * adding attributes specific to food items.
 * </p>
 * 
 * @author Alessia Pilla
 */
public class FoodItem extends PurchasableItem {

    private String foodType; 
    private double nutritionalValue;

    /**
     * Constructs a new FoodItem with the specified attributes.
     *
     * @param itemName The name of the food item
     * @param coinsRequired The number of coins needed to purchase the food item
     * @param foodType The type of food (e.g., "Cake", "Orange", "Burger")
     * @param nutritionalValue The nutritional value of the food item (how the food item affects the energy stats bar)
     */

    public FoodItem(String itemName, int coinsRequired, String foodType, double nutritionalValue){
        super(itemName, coinsRequired);
        this.foodType = foodType;
        this.nutritionalValue = nutritionalValue;
    }

     /**
     * Gets the type of food
     *
     * @return The food type
     */

    public String getFoodType(){
        return foodType;
    }

    /**
     * Gets the nutritional value of the food item
     *
     * @return The nutritional value as a double
     */

    public double getNutritionalValue(){
        return nutritionalValue;
    }
}
