/*
 *  MUST CONTAIN MAIN GAME LOOP
 */

package game;

import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class GameTimer extends AnimationTimer {
    private final Stage stage;
    GraphicsContext gc;
    private Vehicle vehicle;

    public GameTimer(Stage stage, GraphicsContext gc,Vehicle vehicle) {
        this.stage = stage;
        this.gc = gc;
        this.vehicle = vehicle;
    }

    @Override
    public void handle(long l) {
        gc.clearRect(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        this.vehicle.render(this.gc);
    }
}
