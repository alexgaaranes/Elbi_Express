package graphics.vehicles.types;

import graphics.map.Map;
import graphics.vehicles.Vehicle;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Car extends Vehicle {

    public Car(double xPos, double yPos, String id, Scene parentScene, Map map, double width, double height) {
        super(new Image("file:src/assets/sprites/vehicle-sheets/car/red-car.png"),
                xPos, yPos, id, parentScene, map, width, height, 2, 0.75);
        this.setFrameSize(96,96);
        this.acceleration = 1;
        this.maxVelocity = 225;
        this.turningSpeed = 250;
    }
}
