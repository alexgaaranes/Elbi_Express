/*
*   MUST BE EXTENDED BY CLASSES THAT MAKES USE OF GRAPHICS
*
* */

package graphics;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Graphics {
    protected Image image;
    protected double xPos;
    protected double yPos;

    public Graphics(Image image, double xPos, double yPos) {
        this.image = image;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(this.image, this.xPos, this.yPos);
    }

}
