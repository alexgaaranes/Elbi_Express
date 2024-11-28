/*
 *  Parent Class for more specific graphics.disasters
 */
package graphics.disasters;

import graphics.Graphic;
import javafx.scene.image.Image;

public class Disaster extends Graphic {
    protected Disaster(Image image, double xPos, double yPos, double width, double height) {
        super(image, xPos, yPos, width, height);
    }
}
