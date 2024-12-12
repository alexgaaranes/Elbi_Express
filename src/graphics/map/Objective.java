/**
 * Abstract class representing an objective on the map that vehicles can interact with.
 * The Objective class handles tracking player vehicles, rendering interaction prompts,
 * and processing the objective logic when a vehicle interacts with it.
 */

package graphics.map;

import game.Audio;
import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Random;

abstract public class Objective {

    //Reference to the game map and graphics context for drawing
    protected final Map map;
    protected final GraphicsContext gc;

    //Grid position of the objective
    protected int xGridPos, yGridPos;

    //Position in pixels based on grid
    protected double xPos, yPos;

    //Player vehicles
    protected Vehicle p1, p2;

    //Vehicle currently interacting with the objective
    protected Vehicle occupiedVehicle = null;

    //Scale factor for the prompt images
    protected final float PROMPT_SCALE = 0.15F;
    protected Random r = new Random();

    /**
     * Constructor to initialize an Objective with its grid position, map, and graphics context.
     * @param xGridPos The x-coordinate of the objective on the grid.
     * @param yGridPos The y-coordinate of the objective on the grid.
     * @param map The map in which the objective exists.
     * @param gc The graphics context used for rendering.
     */
    Objective(int xGridPos, int yGridPos, Map map, GraphicsContext gc) {
        this.xGridPos = xGridPos;
        this.yGridPos = yGridPos;
        this.map = map;
        this.gc = gc;

        //Convert grid position to pixel position
        this.xPos = xGridPos * map.getTileW();
        this.yPos = yGridPos * map.getTileH();
    }

    /**
     * Returns the bounds of the objective on the map, used for collision detection.
     * @return The bounding box of the objective.
     */
    public Rectangle2D getBounds() {
        return new Rectangle2D(this.xPos, this.yPos, map.getTileW() * 3, map.getTileH() * 2);
    }

    /**
     * Tracks both vehicles and checks if either enters the objective's area.
     * If a vehicle enters, it is marked as the occupied vehicle and the objective is opened.
     * @param v1 The first vehicle (player 1).
     * @param v2 The second vehicle (player 2).
     */
    public void trackVehicle(Vehicle v1, Vehicle v2) {
        this.p1 = v1;
        this.p2 = v2;

        new AnimationTimer() {
            @Override
            public void handle(long l) {
                //Check if any of the vehicles enter the objective's bounds
                if(bodyEnters(p1)) {
                    if (occupiedVehicle == null) {
                        setVehicle(p1);  //Set the occupying vehicle to p1
                        openObjective();  //Open the objective for interaction
                    }
                } else if (bodyEnters(p2)) {
                    if (occupiedVehicle == null) {
                        setVehicle(p2);  //Set the occupying vehicle to p2
                        openObjective();  //Open the objective for interaction
                    }
                } else {
                    occupiedVehicle = null;  //Reset occupied vehicle if neither enters
                }
            }
        }.start();
    }

    /**
     * Checks if a given vehicle's bounds intersect with the objective's bounds.
     * @param vehicle The vehicle to check.
     * @return true if the vehicle's bounds intersect with the objective.
     */
    public boolean bodyEnters(Vehicle vehicle) {
        return vehicle.getBounds().intersects(this.getBounds());
    }

    /**
     * Sets the vehicle that is occupying the objective.
     * @param vehicle The vehicle to set as occupied.
     */
    public synchronized void setVehicle(Vehicle vehicle) {
        this.occupiedVehicle = vehicle;
    }

    /**
     * Opens the objective for interaction (prints which vehicle is interacting).
     */
    public void openObjective() {
        System.out.println("In objective: " + this.occupiedVehicle);
    }

    /**
     * Abstract method to be implemented by subclasses for processing specific objective logic.
     * This method is called when the objective is successfully interacted with.
     */
    abstract protected void doProcess();

    /**
     * Displays a prompt to the player, showing keys that the player must press to interact with the objective.
     * The keys displayed depend on which vehicle (player 1 or player 2) is interacting.
     */
    protected void showPrompt() {
        Random r = new Random();
        ArrayList<KeyCode> promptKeys = new ArrayList<>();

        //Determine which prompt keys to display based on the player
        if (this.occupiedVehicle.getPlayerID().equals(Vehicle.PLAYER_ONE)) {
            KeyCode[] possibleKeys = {KeyCode.Q, KeyCode.E};
            for (int i = 0; i < 4; i++) {
                promptKeys.add(possibleKeys[r.nextInt(possibleKeys.length)]);
            }
        } else if (this.occupiedVehicle.getPlayerID().equals(Vehicle.PLAYER_TWO)) {
            KeyCode[] possibleKeys = {KeyCode.PERIOD, KeyCode.SLASH};
            for (int i = 0; i < 4; i++) {
                promptKeys.add(possibleKeys[r.nextInt(possibleKeys.length)]);
            }
        }

        //Key input handler: checks for correct key presses
        new AnimationTimer() {
            KeyCode lastKey = null;
            @Override
            public void handle(long l) {
                if (occupiedVehicle == null) {
                    this.stop();  //Stop timer if no vehicle is occupying
                }
                if (promptKeys.isEmpty()) {
                    doProcess();  //Process the objective interaction once all keys are pressed
                    this.stop();
                }
                if (lastKey != null && !Vehicle.getActiveKeys().contains(lastKey)) {
                    lastKey = null;  //Reset last key if it is no longer active
                }
                if (lastKey == null && Vehicle.getActiveKeys().contains(promptKeys.get(0))) {
                    Audio.playClip("keyHit", 5.0);  //Play sound when the correct key is pressed
                    lastKey = promptKeys.remove(0);  //Remove the pressed key from the prompt
                }
            }
        }.start();

        //Render the prompt keys on the screen
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (occupiedVehicle == null) {
                    this.stop();  //Stop the timer if no vehicle is occupying
                }
                for (int i = 0; i < promptKeys.size(); i++) {
                    //Load and render key images for the prompt
                    Image keyImage = new Image(getClass().getResource("/assets/sprites/keys/key" + promptKeys.get(i).getName().toUpperCase() + ".png").toExternalForm());
                    gc.drawImage(keyImage,
                            xPos - (map.getTileW() / 2) + i * keyImage.getWidth() * PROMPT_SCALE,
                            yPos - map.getTileH() / 2,
                            keyImage.getWidth() * PROMPT_SCALE, keyImage.getHeight() * PROMPT_SCALE);
                }
            }
        }.start();
    }
}
