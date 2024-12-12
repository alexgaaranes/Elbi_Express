/**
 * The Motorcycle class represents a specific type of vehicle in the game.
 * It extends the Vehicle class and defines unique properties such as
 * acceleration, maximum velocity, and turning speed, tailored to a motorcycle.
 */
package graphics.vehicles.types;

import graphics.map.Map;
import graphics.vehicles.Vehicle;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Motorcycle extends Vehicle {

    /**
     * Constructs a Motorcycle object with specified properties.
     * 
     * @param xPos the initial x-coordinate of the motorcycle
     * @param yPos the initial y-coordinate of the motorcycle
     * @param id the unique identifier for the motorcycle
     * @param parentScene the scene in which the motorcycle operates
     * @param map the map the motorcycle is associated with
     * @param width the width of the motorcycle
     * @param height the height of the motorcycle
     * @param path the file path to the image representing the motorcycle
     */
    public Motorcycle(double xPos, double yPos, String id, Scene parentScene, Map map, double width, double height, String path) {
        super(
            new Image(path), // Load the image of the motorcycle
            xPos, 
            yPos, 
            id, 
            parentScene, 
            map, 
            width, 
            height,
            1, 
            0.55
        );

        this.setFrameSize(96, 96); 
        this.acceleration = 5; 
        this.maxVelocity = 200; 
        this.turningSpeed = 300;
    }
}
