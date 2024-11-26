/*
 *  MUST CONTAIN MAIN GAME LOOP
 */

package game;

import graphics.map.Map;
import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class GameTimer extends AnimationTimer {
    private final Stage stage;
    GraphicsContext gc;
    private Vehicle vehicle;
    private Map map;

    public GameTimer(Stage stage, GraphicsContext gc,Vehicle vehicle, Map map) {
        this.stage = stage;
        this.gc = gc;
        this.vehicle = vehicle;
        this.map = map;
    }

    @Override
    public void handle(long l) {
        gc.clearRect(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        this.map.drawMap(gc);
        this.vehicle.render(this.gc);
    }
}
