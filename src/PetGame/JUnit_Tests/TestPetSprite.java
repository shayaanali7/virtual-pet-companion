package PetGame.JUnit_Tests;

import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.*;

import PetGame.PetSprite;

import static org.junit.jupiter.api.Assertions.*;

public class TestPetSprite {

    private ImageView imageView;
    private Image testImage;
    private PetSprite petSprite;

    @BeforeAll
    public static void initToolkit() {
        new JFXPanel(); 
    }

    @BeforeEach
    public void setUp() {
        imageView = new ImageView();
        testImage = new Image("https://via.placeholder.com/100"); /** Simple image for testing*/
        petSprite = new PetSprite(imageView, testImage);
    }

    @Test
    public void testImageIsSetOnConstruction() {
        assertNotNull(imageView.getImage(), "Image should be set on ImageView");
        assertEquals(testImage, imageView.getImage(), "ImageView should contain the provided image");
    }

    @Test
    public void testGetRegularImageThrowsException() {
        Exception exception = assertThrows(UnsupportedOperationException.class, petSprite::getRegularImage);
        assertEquals("Unimplemented method 'getRegularImage'", exception.getMessage());
    }

    @Test
    public void testGetFlippedImageThrowsException() {
        Exception exception = assertThrows(UnsupportedOperationException.class, petSprite::getFlippedImage);
        assertEquals("Unimplemented method 'getFlippedImage'", exception.getMessage());
    }
}
