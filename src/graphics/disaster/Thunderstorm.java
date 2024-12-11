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

	private static Image overlay1 = new Image(Thunderstorm.class.getResource("/assets/sprites/fog.png").toExternalForm());
	private static Image overlay2 = new Image(Thunderstorm.class.getResource("/assets/sprites/rain.gif").toExternalForm());
	private final ImageView fog;
	private final ImageView rain;
	private final Group overlayGroup = new Group();
	
    protected Thunderstorm(Stage stage, Scene parentScene, Vehicle v1, Vehicle v2) {
        super(stage, parentScene, v1, v2);
        fog = new ImageView(overlay1);
        rain = new ImageView(overlay2);
    	
    	overlayGroup.getChildren().addAll(fog, rain);
    }

    @Override
    protected void spawnDisaster() {
        System.out.println("Thunderstorm spawned");
        long startTime = System.nanoTime();
        
        Node root = parentScene.getRoot();
        ((Group) root).getChildren().add(overlayGroup);
	    
        this.v1.thunderstormToggleEffect();
        this.v2.thunderstormToggleEffect();
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                if(l-startTime >= 5000000000L){
                    v1.thunderstormToggleEffect();
                    v2.thunderstormToggleEffect();
                    ((Group) root).getChildren().remove(overlayGroup);
                    this.stop(); 
                }
            }
        }.start();
    }
}
