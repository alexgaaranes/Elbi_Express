package graphics.vehicles.types;

import graphics.map.Map;
import graphics.vehicles.Vehicle;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Truck extends Vehicle {

    public Truck(double xPos, double yPos, String id, Scene parentScene, Map map, double width, double height) {
        super(new Image("file:src/assets/sprites/vehicle-sheets/motorcycle/red-motor.png"),
                xPos, yPos, id, parentScene, map, width, height, 3, 0.75);
        this.setFrameSize(96,96);
        this.acceleration = 0.75;
        this.maxVelocity = 300;
        this.turningSpeed = 150;
    }
}
