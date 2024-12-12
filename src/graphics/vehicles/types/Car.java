/**
 * The Car class represents a specific type of vehicle in the game.
 * It extends the Vehicle class and defines unique properties such as
 * acceleration, maximum velocity, and turning speed, tailored to a car.
 */
package graphics.vehicles.types;

import graphics.map.Map;
import graphics.vehicles.Vehicle;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Car extends Vehicle {
	/**
     * Constructs a Car object with specified properties.
     * 
     * @param xPos the initial x-coordinate of the car.
     * @param yPos the initial y-coordinate of the car.
     * @param id the unique identifier for the car.
     * @param parentScene the scene in which the car operates.
     * @param map the map the car is associated with.
     * @param width the width of the car.
     * @param height the height of the car.
     * @param path the file path to the image representing the car.
     */
    public Car(double xPos, double yPos, String id, Scene parentScene, Map map, double width, double height, String path) {
        super(new Image(path),
                xPos, yPos, id, parentScene, map, width, height, 2, 0.75);
        this.setFrameSize(96,96);
        this.acceleration = 1;
        this.maxVelocity = 250;
        this.turningSpeed = 250;
    }
}
