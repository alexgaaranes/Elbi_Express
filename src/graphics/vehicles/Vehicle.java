package graphics.vehicles;

import graphics.Graphic;
import graphics.map.Map;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.Rotate;

import java.util.HashSet;
import java.util.Set;

public class Vehicle extends Graphic {
    private double angle;
    private Set<KeyCode> activeKeys = new HashSet<>();
    private final Scene parentScene;
    private double velocity;
    private final double acceleration = 0.005;
    private final double maxVelocity = 0.5;
    private final double turningSpeed = 0.75;
    private final double scale = 0.25;
    private final Map map;

    public Vehicle(Image image, double xPos, double yPos, int id, Scene parentScene, Map map) {
        super(image, xPos, yPos, id);
        this.parentScene = parentScene;
        this.map = map;
        this.angle = 0;

        this.handleKeyOnPress();
    }


    private void handleKeyOnPress(){
        parentScene.setOnKeyPressed(event -> {
            activeKeys.add(event.getCode());
            System.out.println(event.getCode());
        });
        parentScene.setOnKeyReleased(event -> {
            activeKeys.remove(event.getCode());
            System.out.println("Removed:"+event.getCode());
        });
        new AnimationTimer() {
            public void handle(long nanoTime) {
                moveVehicle();
            }
        }.start();
    }

    protected void moveVehicle(){
        if(activeKeys.contains(KeyCode.W)){
            velocity = Math.min(velocity + acceleration, maxVelocity);
        } else if(activeKeys.contains(KeyCode.S)){
            velocity = Math.max(velocity - acceleration, -1*maxVelocity);
        } else {
            if(velocity > 0){
                velocity = Math.max(velocity - acceleration, 0);
            } else if(velocity < 0){
                velocity = Math.min(velocity + acceleration, maxVelocity);
            }
        }

        if (activeKeys.contains(KeyCode.A)) {
            angle -= turningSpeed;
        }
        if (activeKeys.contains(KeyCode.D)) {
            angle += turningSpeed;
        }

        double radAngle = Math.toRadians(angle);
        double displaceX = velocity * Math.cos(radAngle);
        double displaceY = velocity * Math.sin(radAngle);

        if(isColliding(this.xPos+displaceX, this.yPos)){
            displaceX = 0;
        }
        if(isColliding(this.xPos, this.yPos+displaceY)){
            displaceY = 0;
        }
        this.xPos += displaceX;
        this.yPos += displaceY;
    }

    // Wall Collision
    private boolean isColliding(double newX, double newY){
        // Get the position on grid (get tile uses the screen size so this is accurate)
        int xGridPos = (int) (newX / map.getTileW());
        int yGridPos = (int) (newY / map.getTileH());

        return map.getMapMatrix()[yGridPos][xGridPos]==0;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.save();
        // Get Rotation and draw image
        Rotate r = new Rotate(this.angle, this.xPos, this.yPos);
        gc.setTransform(r.getMxx(),r.getMyx(),r.getMxy(),r.getMyy(),r.getTx(),r.getTy());
        gc.drawImage(this.image,
                xPos - (this.image.getWidth()*this.scale) / 2,
                yPos - (this.image.getHeight()*this.scale) / 2,
                this.image.getWidth()*this.scale,
                this.image.getHeight()*this.scale
                );

        gc.restore();
    }

    /*Rotation reference: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/canvas/GraphicsContext.html#setTransform-double-double-double-double-double-double-*/

}
