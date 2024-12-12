/**
 * Represents an earthquake disaster that causes a shaking effect in the game environment.
 * This disaster also triggers earthquake effects for the vehicles and displays an earthquake overlay.
 * The shaking effect is temporary and ends after a set duration.
 */

package graphics.disaster;

import game.Audio;
import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.util.Random;

public class Earthquake extends Disaster {

    //Shake distance for the shaking effect
    private static final int SHAKE_DISTANCE = 3;

    //Static image for the earthquake overlay
    private static Image overlay = new Image(Earthquake.class.getResource("/assets/sprites/earthquake.png").toExternalForm());

    //ImageView for displaying the earthquake overlay
    private final ImageView earthquake;

    //Random number generator for random shaking offsets
    private final Random random = new Random();

    //Group that contains the earthquake overlay image
    private final Group overlayGroup = new Group();

    /**
     * Constructor for initializing the earthquake disaster.
     * @param stage The primary game stage.
     * @param parentScene The current game scene.
     * @param v1 The first vehicle (player 1).
     * @param v2 The second vehicle (player 2).
     */
    public Earthquake(Stage stage, Scene parentScene, Vehicle v1, Vehicle v2) {
        super(stage, parentScene, v1, v2);

        //Initialize the earthquake overlay ImageView
        earthquake = new ImageView(overlay);

        //Add the earthquake overlay to the overlay group
        overlayGroup.getChildren().add(earthquake);
    }

    /**
     * Spawns the earthquake disaster in the game.
     * This function triggers the shaking effect, adds the earthquake overlay to the game scene, and toggles vehicle effects.
     * It also handles the duration of the earthquake and stops the shaking effect after a set time.
     */
    @Override
    public void spawnDisaster() {
		Audio.playClip("shake", 3.0);
        System.out.println("Earthquake spawned");

        //Track the start time for the earthquake duration
        long startTime = System.nanoTime();

        //Get the root of the scene and add the overlay group
        Node root = parentScene.getRoot();
        ((Group) root).getChildren().add(overlayGroup);

        //Toggle the earthquake effect for both vehicles
        this.v1.earthquakeToggleEffect();
        this.v2.earthquakeToggleEffect();

        //Start the shaking effect
        new AnimationTimer() {
			long shakeSoundStartTime = System.nanoTime();
            @Override
            public void handle(long now) {
                //Generate random shaking offsets for the earthquake
                double offsetX = (random.nextDouble() * 2 - 1) * SHAKE_DISTANCE;
                double offsetY = (random.nextDouble() * 2 - 1) * SHAKE_DISTANCE;

                //Apply the shaking effect to the root (game scene)
                root.setTranslateX(offsetX);
                root.setTranslateY(offsetY);

				// Play the shake sound over time
				if(now - shakeSoundStartTime >= 200_000_000L) {
					Audio.playClip("shake", 3.0);
					shakeSoundStartTime = now;
				}

                //Stop the shaking effect after 3 seconds
                if(now - startTime >= 3_000_000_000L) {
                    //Reset the translation to stop shaking
                    root.setTranslateX(0);
                    root.setTranslateY(0);

                    //Disable the earthquake effects on the vehicles
                    v1.earthquakeToggleEffect();
                    v2.earthquakeToggleEffect();

                    //Remove the earthquake overlay from the scene
                    ((Group) root).getChildren().remove(overlayGroup);

                    //Stop the timer
                    this.stop();
                }
            }
        }.start();
    }
}
