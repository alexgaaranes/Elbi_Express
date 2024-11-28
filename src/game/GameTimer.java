/*
 *  MUST CONTAIN MAIN GAME LOOP
 */

package game;

import graphics.map.Household;
import graphics.map.Map;
import graphics.map.Store;
import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class GameTimer extends AnimationTimer {
    private final Stage stage;
    GraphicsContext gc;
    private Vehicle v1;
    private Vehicle v2;
    private Map map;

    public GameTimer(Stage stage, GraphicsContext gc, Map map) {
        this.stage = stage;
        this.gc = gc;
        this.map = map;
    }

    public void setPlayers(Vehicle v1, Vehicle v2){
        this.v1 = v1;
        this.v2 = v2;

        // Setup detection for stores
        for(Store store: map.getStoreList()){
            store.trackVehicle(v1, v2);
        }
        // Setup detection for households
        for(Household house: map.getHouseList()){
            house.trackVehicle(v1,v2);
        }
    }

    @Override
    public void handle(long l) {
        gc.clearRect(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        this.map.drawMap(gc);
        this.v1.render(this.gc);
        this.v2.render(this.gc);
    }
}
