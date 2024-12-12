/**
 * Manages the spawning of random disasters in the game.
 * The DisasterManager handles the creation and timing of various disaster events such as earthquakes, thunderstorms, and blackouts.
 * It periodically checks if a disaster should be spawned based on a random chance and timer, and ensures that disasters are triggered at random intervals.
 */

package graphics.disaster;

import game.Scoreboard;
import game.Timer;
import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Random;

public class DisasterManager {
    // Array of possible disasters
    private Disaster[] disasterList;
    
    // Timer for managing spawn intervals
    private Timer spawnTimer;

    /**
     * Constructor for initializing the DisasterManager.
     * @param stage The primary game stage.
     * @param ParentScene The current game scene.
     * @param v1 The first vehicle (player 1).
     * @param v2 The second vehicle (player 2).
     */
    public DisasterManager(Stage stage, Scene ParentScene, Vehicle v1, Vehicle v2) {
        spawnTimer = new Timer(Disaster.maxRandTime);  // Initialize the spawn timer with a maximum random time
        disasterList = new Disaster[3];  // Array to hold the different disasters
        disasterList[0] = new Earthquake(stage, ParentScene, v1, v2);
        disasterList[1] = new Thunderstorm(stage, ParentScene, v1, v2);
        disasterList[2] = new Blackout(stage, ParentScene, v1, v2);
    }

    /**
     * Starts the automatic spawning of random disasters.
     * This method continuously checks if a disaster should be spawned based on a random chance.
     * It ensures that disasters are spawned at random intervals, with the timing controlled by the spawn timer.
     */
    public void autoRandomDisaster() {
        spawnTimer.start();  //Start the spawn timer
        new AnimationTimer() {
            Random r = new Random();  //Random number generator
            long startTime = System.nanoTime();  //Start time for calculating intervals

            @Override
            public void handle(long l) {
                //Check if the player has lost the game and stop the timer if so
                if (Scoreboard.activeScoreboard.checkIfLost()) {
                    this.stop();
                }

                //Restart the spawn timer if it's not running
                if (!spawnTimer.getStatus()) {
                    spawnTimer.start();
                }

                //Check if enough time has passed and the random time condition is met
                if (spawnTimer.getElapsedTimeInSec() > Disaster.minRandTime && l - startTime >= 1_000_000_000L) {
                    int spawnChance = r.nextInt(10);  // Generate a random spawn chance (0-9)
                    
                    //If the spawn chance is divisible by 4, spawn a disaster
                    if (spawnChance % 4 == 0) {
                        int index = r.nextInt(disasterList.length);  //Randomly select a disaster from the list
                        disasterList[index].spawnDisaster();  // Spawn the selected disaster
                        spawnTimer.restart();  // Restart the timer for the next spawn
                    }
                    startTime = l;  //Update the start time for the next check
                }
            }
        }.start();  //Start the animation timer
    }
}
