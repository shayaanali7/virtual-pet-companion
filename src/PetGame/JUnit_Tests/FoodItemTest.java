package PetGame.JUnit_Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import PetGame.FoodItem;

public class FoodItemTest {
	
	@Test
	public void testGetFoodType() {
		FoodItem food = new FoodItem("Burger", 20, "Burger", 10);
		String result = food.getFoodType();
		assertEquals("Burger", result);
	}
	
	@Test
	public void testNutritionValue() {
		FoodItem food = new FoodItem("Burger", 20, "Burger", 10);
		double result = food.getNutritionalValue();
		assertEquals(10, result);
	}

}
