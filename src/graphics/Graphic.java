/*
*   MUST BE EXTENDED BY CLASSES THAT MAKES USE OF GRAPHICS
*
* */

package graphics;


import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Graphic {
    protected Image image;
    protected double xPos;
    protected double yPos;
    protected double width, height;
    protected boolean hasCollision;

    public Graphic(Image image, double xPos, double yPos, double width, double height) {
        this.image = image;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(this.image, this.xPos, this.yPos, this.width, this.width);
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(this.xPos, this.yPos, this.image.getWidth(), this.image.getHeight());
    }

    public boolean collides(Graphic graphic){
        return graphic.getBounds().intersects(this.getBounds());
    }

}
