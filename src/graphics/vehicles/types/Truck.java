package graphics.vehicles.types;

import graphics.map.Map;
import graphics.vehicles.Vehicle;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Truck extends Vehicle {

    public Truck(Image image, double xPos, double yPos, String id, Scene parentScene, Map map, double width, double height) {
        super(image, xPos, yPos, id, parentScene, map, width, height, 3);
        this.acceleration = 2;
        this.maxVelocity = 300;
        this.turningSpeed = 150;
    }
}
