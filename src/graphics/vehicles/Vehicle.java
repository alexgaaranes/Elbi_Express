package graphics.vehicles;

import graphics.Graphics;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.Rotate;

import java.util.HashSet;
import java.util.Set;

public class Vehicle extends Graphics {
    private int id;
    private double angle;
    private Set<KeyCode> activeKeys = new HashSet<>();
    private final Scene parentScene;
    private double velocity;
    private double acceleration = 0.005;
    private final double maxVelocity = 1;
    private final double turningSpeed = 0.5;

    public Vehicle(Image image, double xPos, double yPos, int id, Scene parentScene) {
        super(image, xPos, yPos);
        this.id = id;
        this.parentScene = parentScene;
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
        double newX = this.xPos;

        if(activeKeys.contains(KeyCode.W)){
            velocity = Math.min(velocity + acceleration, maxVelocity);
        } else if(activeKeys.contains(KeyCode.S)){
            velocity = Math.max(velocity - acceleration, maxVelocity);
        } else {
            if(velocity > 0){
                velocity = Math.max(velocity - acceleration, 0);
            } else if(velocity < 0){
                velocity = Math.min(velocity + acceleration, maxVelocity);
            }
        }

        if (activeKeys.contains(KeyCode.A)) {
            angle += turningSpeed;
        }
        if (activeKeys.contains(KeyCode.D)) {
            angle -= turningSpeed;
        }

        double radAngle = Math.toRadians(angle);
        this.xPos += velocity * Math.cos(radAngle);
        this.yPos += velocity * Math.sin(radAngle);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.save();
        // Get Rotation and draw image
        Rotate r = new Rotate(this.angle, this.xPos, this.yPos);
        gc.setTransform(r.getMxx(),r.getMyx(),r.getMxy(),r.getMyy(),r.getTx(),r.getTy());
        gc.drawImage(this.image, xPos - this.image.getWidth() / 2, yPos - this.image.getHeight() / 2);

        gc.restore();
    }
    /*Rotation reference: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/canvas/GraphicsContext.html#setTransform-double-double-double-double-double-double-*/

}
