package graphics.vehicles.types;

import graphics.map.Map;
import graphics.vehicles.Vehicle;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Motorcycle extends Vehicle {

    public Motorcycle(double xPos, double yPos, String id, Scene parentScene, Map map, double width, double height) {
        super(new Image("file:src/assets/sprites/vehicle-sheets/motorcycle/red-motor.png"),
                xPos, yPos, id, parentScene, map, width, height, 1);
        this.acceleration = 5;
        this.maxVelocity = 175;
        this.turningSpeed = 300;
    }
}
