/*
 *  Parent Class for more specific graphics.disasters
 */
package graphics.disasters;

import graphics.Graphics;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Disaster extends Graphics {
    protected Disaster(Image image, double xPos, double yPos) {
        super(image, xPos, yPos, true);
    }
}
