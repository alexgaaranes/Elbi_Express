package game.panes;

import game.Game;
import game.GameTimer;
import graphics.map.Map;
import graphics.misc.HUD;
import graphics.vehicles.Vehicle;
import javafx.geometry.Pos;
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
        Vehicle vehicle1 = new Vehicle(new Image("file:src/assets/sprites/testVehicle.png"),
                575,500, Vehicle.PLAYER_TWO,this.parentScene, map, 50, 50);
        Vehicle vehicle2 = new Vehicle(new Image("file:src/assets/sprites/testVehicle.png"),
                600,500, Vehicle.PLAYER_ONE,this.parentScene, map, 50, 50);
        GameTimer gameTimer = new GameTimer(stage, gc, map);
        HUD gameHUD = new HUD(300, 5);
        this.getChildren().add(gameHUD);
        gameHUD.setTranslateX(100);
        gameHUD.startHUDAutoUpdate();
        gameTimer.setPlayers(vehicle1, vehicle2);
        gameTimer.start();
    }

    @Override
    public void setParentScene(Scene scene) {
        this.parentScene = scene;
    }
}
