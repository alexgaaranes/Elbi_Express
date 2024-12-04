package graphics.disaster;

import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Earthquake extends Disaster {

    public Earthquake(Stage stage, Scene parentScene, Vehicle v1, Vehicle v2) {
        super(stage, parentScene, v1, v2);
    }

    @Override
    public void spawnDisaster() {
        System.out.println("Earthquake spawned");
        long startTime = System.nanoTime();
        this.v1.earthquakeToggleEffect();
        this.v2.earthquakeToggleEffect();
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(l-startTime >= 3000000000L){
                    v1.earthquakeToggleEffect();
                    v2.earthquakeToggleEffect();
                    this.stop();
                }
            }
        }.start();
    }
}
