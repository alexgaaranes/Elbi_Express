package graphics.vehicles.types;

import graphics.map.Map;
import graphics.vehicles.Vehicle;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Car extends Vehicle {

    public Car(Image image, double xPos, double yPos, String id, Scene parentScene, Map map, double width, double height) {
        super(image, xPos, yPos, id, parentScene, map, width, height, 2);
        this.acceleration = 5;
        this.maxVelocity = 200;
        this.turningSpeed = 250;
    }
}
