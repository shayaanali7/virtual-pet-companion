package PetGame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * The {@code PetSprite} class is responsible for handling the visual
 * representation of a pet on the screen using an {@code ImageView}.
 * When instantiated, it sets the initial image of the pet.
 * 
 * This class can be extended or modified in the future to allow dynamic 
 * image changes like flipping, animations, and emotion-based visuals
 * 
 * @author Ishaan Misra
 */
public class PetSprite {

    /** The ImageView that displays the pet on the screen */
    private final ImageView imageView;

    /** The image currently assigned to ImageView. */
    private final Image image;

    /**
     * The Constructor. It constructs a new {@code PetSprite} with the specified ImageView and initial image
     *
     * @param imageView the {@code ImageView} used to render the pet
     * @param image the initial {@code Image} to be displayed
     */
    public PetSprite(ImageView imageView, Image image) {
        this.imageView = imageView;
        this.image = image;
        this.imageView.setImage(this.image);
    }

    /**
     * Gets the default image path of the sprite.
     *
     * @return the path to the regular image
     * @throws UnsupportedOperationException when called
     */
    public String getRegularImage() {
        throw new UnsupportedOperationException("Unimplemented method 'getRegularImage'");
    }

    /**
     * Gets the flipped image path of the sprite.
     *
     * @return the path to the flipped image
     * @throws UnsupportedOperationException when called
     */
    public String getFlippedImage() {
        throw new UnsupportedOperationException("Unimplemented method 'getFlippedImage'");
    }
}
