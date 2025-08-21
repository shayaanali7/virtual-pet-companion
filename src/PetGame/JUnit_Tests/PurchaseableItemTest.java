package PetGame.JUnit_Tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import PetGame.NotEnoughCoinsException;
import PetGame.Player;
import PetGame.PurchasableItem;


public class PurchaseableItemTest {
	
	@Test
	public void testGetName() {
		PurchasableItem item = new PurchasableItem("Cake", 20);
		String res = item.getName();
		assertEquals("Cake", res);
	}
	
	@Test
	public void testGetPrice() {
		PurchasableItem item = new PurchasableItem("Cake", 20);
		double res = item.getPrice();
		assertEquals(20, res);
	}
	
	@Test
	public void testDeductCoins() throws NotEnoughCoinsException {
		Player player = new Player(30);
		PurchasableItem item = new PurchasableItem("Cake", 20);
		assertDoesNotThrow(() -> item.deductCoins(player));
	}
}
