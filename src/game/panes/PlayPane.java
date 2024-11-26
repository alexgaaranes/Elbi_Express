package game.panes;

import game.Game;
import game.GameTimer;
import graphics.map.Map;
import graphics.vehicles.Vehicle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PlayPane extends Group implements gamePane{
    private final Stage stage;
    private Scene parentScene = null;

    Canvas playCanvas = new Canvas(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
    GraphicsContext gc;

    public PlayPane(Stage stage) {
        this.stage = stage;
        this.getChildren().add(playCanvas);
        this.gc = playCanvas.getGraphicsContext2D();
    }

    public void startGame(){
        Map map = new Map(stage, parentScene);
        Vehicle vehicle = new Vehicle(new Image("file:src/assets/sprites/testVehicle.png"),
                575,500,1,this.parentScene, map);
        GameTimer gameTimer = new GameTimer(stage, gc, vehicle, map);
        gameTimer.start();
    }

    @Override
    public void setParentScene(Scene scene) {
        this.parentScene = scene;
    }
}
