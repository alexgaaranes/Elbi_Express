/**
 * Represents a blackout disaster that affects the game environment by reducing visibility.
 * A blackout causes the screen to go dark, and the players' vehicles will have flashlight effects.
 * This disaster is temporarily active and stops after a predefined duration.
 */

package graphics.disaster;

import game.Game;
import game.panes.PlayPane;
import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Blackout extends Disaster {
    
    //The PlayPane where the blackout will be displayed
    PlayPane playPane;
    
    //Visual elements for blackout effects
    private Rectangle blackOutRect; // The blackout screen that dims the game window
    private Circle v1Flash, v2Flash; // Flashlight effects for the vehicles
    
    //Boolean to track if the blackout is currently active
    private boolean isActive = false;

    //Pane for layering the blackout effect
    Pane layer = new Pane();

    /**
     * Constructor for initializing the blackout disaster.
     * 
     * @param stage The primary game stage.
     * @param parentScene The current game scene.
     * @param v1 The first vehicle (player 1).
     * @param v2 The second vehicle (player 2).
     */
    protected Blackout(Stage stage, Scene parentScene, Vehicle v1, Vehicle v2) {
        super(stage, parentScene, v1, v2);
        
        //Initialize the PlayPane from the scene
        playPane = (PlayPane) parentScene.getRoot();
        
        //Set up the blackout rectangle to cover the entire window
        blackOutRect = new Rectangle(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        blackOutRect.setFill(Color.valueOf("#000000")); // Set the blackout color to black
        
        //Set up the flashlight effects for each vehicle
        v1Flash = new Circle(50, Color.valueOf("e5e5e5"));
        v2Flash = new Circle(50, Color.valueOf("e5e5e5"));
        
        //Add all elements to the layer
        layer.getChildren().addAll(blackOutRect, v1Flash, v2Flash);
        
        //Set blend mode for the blackout effect
        layer.setBlendMode(BlendMode.MULTIPLY);
    }

    /**
     * Spawns the blackout disaster in the game.
     * This function activates the blackout effect and tracks the vehicles' flashlight positions.
     * It also handles the duration of the blackout and removes it after a set time.
     */
    @Override
    protected void spawnDisaster() {
        System.out.println("Blackout Spawned");
        
        //Add the blackout effect layer to the PlayPane
        playPane.getChildren().addAll(layer);
        
        //Track the start time for the blackout duration
        long startTime = System.nanoTime();
        isActive = true;

        //Track the position of vehicle 1's flashlight
        trackFlashVehicle(v1Flash, v1);
        
        //Track the position of vehicle 2's flashlight
        trackFlashVehicle(v2Flash, v2);

        //AnimationTimer to remove blackout after 10 seconds
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                //Check if the blackout has exceeded 10 seconds
                if(l - startTime >= 10000000000L){
                    // Remove the blackout effect layer from the PlayPane
                    playPane.getChildren().removeAll(layer);
                    isActive = false;
                    this.stop(); // Stop the timer
                }
            }
        }.start();
    }

    /**
     * Tracks the position of the vehicle and moves the flashlight accordingly.
     * The flashlight follows the vehicle’s x and y position and applies a blur effect.
     * @param flash The flashlight effect (circle) to move.
     * @param vehicle The vehicle to track for position.
     */
    private void trackFlashVehicle(Circle flash, Vehicle vehicle) {
        flash.setEffect(new GaussianBlur(20)); // Apply blur effect to flashlight
        
        //AnimationTimer to update flashlight position in each frame
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                if(!isActive) this.stop(); //Stop the animation if the blackout is no longer active
                
                // Set the x position of the flashlight relative to the vehicle position
                flash.setCenterX(vehicle.getxPos()); 
                flash.setCenterY(vehicle.getyPos());
            }
        }.start();
    }
}
