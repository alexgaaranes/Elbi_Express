package graphics.disaster;

import graphics.vehicles.Vehicle;
import javafx.scene.Scene;
import javafx.stage.Stage;

abstract class Disaster {
    final static int minRandTime = 30;
    final static int maxRandTime = 120;

    protected Stage stage;
    protected Scene parentScene;
    protected Vehicle v1, v2;

    protected Disaster(Stage stage, Scene parentScene, Vehicle v1, Vehicle v2) {
        this.stage = stage;
        this.parentScene = parentScene;
        this.v1 = v1;
        this.v2 = v2;
    }

    abstract protected void spawnDisaster();
}
