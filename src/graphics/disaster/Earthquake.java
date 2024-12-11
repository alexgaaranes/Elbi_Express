package graphics.disaster;

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

	private static final int SHAKE_DISTANCE = 3;
	private static Image overlay = new Image(Earthquake.class.getResource("/assets/sprites/earthquake.png").toExternalForm());
	private final ImageView earthquake;
	private final Random random = new Random();
	private final Group overlayGroup = new Group();
	
	public Earthquake(Stage stage, Scene parentScene, Vehicle v1, Vehicle v2) {
	    super(stage, parentScene, v1, v2);
		earthquake = new ImageView(overlay);
		
		overlayGroup.getChildren().add(earthquake);
	}

	@Override
	public void spawnDisaster() {
	    System.out.println("Earthquake spawned");
	    long startTime = System.nanoTime();
	
	    Node root = parentScene.getRoot();
	   
	    ((Group) root).getChildren().add(overlayGroup);
	
	    this.v1.earthquakeToggleEffect();
	    this.v2.earthquakeToggleEffect();
	
		// Start the shaking effect
		new AnimationTimer() {
		    @Override
		    public void handle(long now) {
		        double offsetX = (random.nextDouble() * 2 - 1) * SHAKE_DISTANCE;
		        double offsetY = (random.nextDouble() * 2 - 1) * SHAKE_DISTANCE;
		        root.setTranslateX(offsetX);
		        root.setTranslateY(offsetY);
		
				// Stop the effect after 3 seconds
				if (now - startTime >= 3_000_000_000L) {
				    root.setTranslateX(0);
				    root.setTranslateY(0);
				    
				    // Disable earthquake effects on vehicles
					v1.earthquakeToggleEffect();
					v2.earthquakeToggleEffect();
					((Group) root).getChildren().remove(overlayGroup);
				    this.stop();
		        }
		    }
		}.start();
	}
}
