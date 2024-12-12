/**
 * Base class for graphical elements in the game.
 * This class provides common properties and methods for managing graphics,
 * such as rendering, positioning, and collision detection.
 * 
 * Note: Must be extended by classes that utilize graphics.
 */

package graphics;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Graphic {
    //The image representing the graphic. 
    protected Image image;

    //The x-coordinate position of the graphic.
    protected double xPos;

    //The y-coordinate position of the graphic.
    protected double yPos;

    //The width of the graphic.
    protected double width;

    //The height of the graphic.
    protected double height;

    //Indicates whether the graphic has collision properties.
    protected boolean hasCollision;

    /**
     * Constructs a Graphic object with specified image, position, and size.
     *
     * @param image  The image to be displayed.
     * @param xPos   The x-coordinate position.
     * @param yPos   The y-coordinate position.
     * @param width  The width of the graphic.
     * @param height The height of the graphic.
     */
    public Graphic(Image image, double xPos, double yPos, double width, double height) {
        this.image = image;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    /**
     * Renders the graphic on the specified GraphicsContext.
     *
     * @param gc The GraphicsContext to draw on.
     */
    public void render(GraphicsContext gc) {
        gc.drawImage(this.image, this.xPos, this.yPos, this.width, this.height);
    }

    /**
     * Retrieves the bounding box of the graphic.
     *
     * @return A Rectangle2D representing the bounds of the graphic.
     */
    public Rectangle2D getBounds() {
        return new Rectangle2D(this.xPos, this.yPos, this.width, this.height);
    }

    /**
     * Checks if this graphic collides with another graphic.
     *
     * @param graphic The other graphic to check collision against.
     * @return True if the graphics collide, otherwise false.
     */
    public boolean collides(Graphic graphic) {
        return graphic.getBounds().intersects(this.getBounds());
    }
}
