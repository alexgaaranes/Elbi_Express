package graphics.disaster;

import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Thunderstorm extends Disaster {

    protected Thunderstorm(Stage stage, Scene parentScene, Vehicle v1, Vehicle v2) {
        super(stage, parentScene, v1, v2);
    }

    @Override
    protected void spawnDisaster() {
        System.out.println("Thunderstorm spawned");
        long startTime = System.nanoTime();
        this.v1.thunderstormEffect();
        this.v2.thunderstormEffect();
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                if(l-startTime >= 10000000000L){
                    v1.defaultMaxVelocity();
                    v2.defaultMaxVelocity();
                    this.stop();
                }
            }
        }.start();
    }
}
