/**
 * Represents a thunderstorm disaster that affects the game environment with fog and rain overlays.
 * This disaster adds visual effects of fog and rain, and toggles effects for the vehicles during the storm.
 * The thunderstorm is temporary and ends after a set duration.
 */

package graphics.disaster;

import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Thunderstorm extends Disaster {

    //Static images for visual effects: fog and rain
    private static Image overlay1 = new Image(Thunderstorm.class.getResource("/assets/sprites/fog.png").toExternalForm());
    private static Image overlay2 = new Image(Thunderstorm.class.getResource("/assets/sprites/rain.gif").toExternalForm());

    //ImageViews for displaying the fog and rain effects
    private final ImageView fog;
    private final ImageView rain;
    
    //Group that contains the overlay images
    private final Group overlayGroup = new Group();

    /**
     * Constructor for initializing the thunderstorm disaster.
     * @param stage The primary game stage.
     * @param parentScene The current game scene.
     * @param v1 The first vehicle (player 1).
     * @param v2 The second vehicle (player 2).
     */
    protected Thunderstorm(Stage stage, Scene parentScene, Vehicle v1, Vehicle v2) {
        super(stage, parentScene, v1, v2);
        
        //Initialize fog and rain ImageViews with their respective images
        fog = new ImageView(overlay1);
        rain = new ImageView(overlay2);
        
        //Add both fog and rain effects to the overlay group
        overlayGroup.getChildren().addAll(fog, rain);
    }

    /**
     * Spawns the thunderstorm disaster in the game.
     * This function activates the storm effects, adds them to the game scene, and toggles vehicle effects.
     * It also handles the duration of the thunderstorm and removes it after a set time.
     */
    @Override
    protected void spawnDisaster() {
        System.out.println("Thunderstorm spawned");
        
        //Track the start time for the storm duration
        long startTime = System.nanoTime();
        
        //Get the root of the scene and add the overlay group
        Node root = parentScene.getRoot();
        ((Group) root).getChildren().add(overlayGroup);
        
        //Toggle the thunderstorm effect for both vehicles
        this.v1.thunderstormToggleEffect();
        this.v2.thunderstormToggleEffect();
        
        //AnimationTimer to remove the thunderstorm effects after 5 seconds (5 billion nanoseconds)
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                if(l - startTime >= 5000000000L){
                    //Toggle off the thunderstorm effect for both vehicles
                    v1.thunderstormToggleEffect();
                    v2.thunderstormToggleEffect();
                    
                    //Remove the thunderstorm overlay from the scene
                    ((Group) root).getChildren().remove(overlayGroup);
                    this.stop(); //Stop the timer
                }
            }
        }.start();
    }
}
