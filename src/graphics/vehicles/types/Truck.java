/**
 * The Truck class represents a specific type of vehicle in the game.
 * It extends the Vehicle class and defines unique properties such as
 * acceleration, maximum velocity, and turning speed, tailored to a truck.
 */

package graphics.vehicles.types;

import graphics.map.Map;
import graphics.vehicles.Vehicle;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Truck extends Vehicle {
	
	/**
     * Constructs a Truck object with specified properties.
     * 
     * @param xPos the initial x-coordinate of the truck.
     * @param yPos the initial y-coordinate of the truck.
     * @param id the unique identifier for the truck.
     * @param parentScene the scene in which the truck operates.
     * @param map the map the truck is associated with.
     * @param width the width of the truck.
     * @param height the height of the truck.
     * @param path the file path to the image representing the truck.
     */
    public Truck(double xPos, double yPos, String id, Scene parentScene, Map map, double width, double height, String path) {
        super(new Image(path),
                xPos, yPos, id, parentScene, map, width, height, 3, 1);
        this.setFrameSize(96,96);
        this.acceleration = 0.75;
        this.maxVelocity = 300;
        this.turningSpeed = 150;
    }
}
