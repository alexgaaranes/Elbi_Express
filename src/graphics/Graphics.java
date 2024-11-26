/*
*   MUST BE EXTENDED BY CLASSES THAT MAKES USE OF GRAPHICS
*
* */

package graphics;


import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Graphics {
    protected Image image;
    protected double xPos;
    protected double yPos;
    protected boolean hasCollision;

    public Graphics(Image image, double xPos, double yPos, boolean hasCollision) {
        this.image = image;
        this.xPos = xPos;
        this.yPos = yPos;
        this.hasCollision = hasCollision;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(this.image, this.xPos, this.yPos);
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(this.xPos, this.yPos, this.image.getWidth(), this.image.getHeight());
    }

    public boolean collides(Graphics graphic){
        return graphic.getBounds().intersects(this.getBounds());
    }

}
