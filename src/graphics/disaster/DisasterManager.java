package graphics.disaster;

import game.Timer;
import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Random;

public class DisasterManager {
    private Stage stage;
    private Scene ParentScene;
    private Vehicle v1, v2;
    private Disaster[] disasterList;
    private Timer spawnTimer;

    public DisasterManager(Stage stage, Scene ParentScene, Vehicle v1, Vehicle v2) {
        spawnTimer = new Timer(Disaster.maxRandTime);
        disasterList = new Disaster[3];
        disasterList[0] = new Earthquake(stage, ParentScene, v1, v2);
        disasterList[1] = new Thunderstorm(stage, ParentScene, v1, v2);
        disasterList[2] = new Blackout(stage, ParentScene, v1, v2);
    }

    public void autoRandomDisaster() {
        spawnTimer.start();
        new AnimationTimer() {
            Random r = new Random();
            long startTime = System.nanoTime();
            @Override
            public void handle(long l) {
                if(!spawnTimer.getStatus()) spawnTimer.start();
                if(spawnTimer.getElapsedTimeInSec() > Disaster.minRandTime && l-startTime >= 1000000000L){
                    int spawnChance = r.nextInt(10);
                    if(spawnChance%4==0){
                        int index = r.nextInt(disasterList.length);
                        disasterList[index].spawnDisaster();
                        spawnTimer.restart();
                    }
                    startTime = l;
                }
            }
        }.start();
    }
}
