package PetGame.JUnit_Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import PetGame.FoodItem;
import PetGame.GiftItem;

public class GiftItemTest {
	
	@Test
	public void testGetHappiness() {
		GiftItem gift = new GiftItem("Gift", 10, 10, "Bow");
		double result = gift.getHappinessValue();
		assertEquals(10, result);
	}
	
	@Test
	public void testGetGiftType() {
		GiftItem gift = new GiftItem("Gift", 10, 10, "Bow");
		String result = gift.getGiftType();
		assertEquals("Bow", result);
	}

}
