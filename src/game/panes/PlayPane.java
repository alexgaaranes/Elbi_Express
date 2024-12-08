package game.panes;

import game.Game;
import game.GameTimer;
import game.Scoreboard;
import graphics.disaster.DisasterManager;
import graphics.map.Map;
import graphics.misc.HUD;
import graphics.vehicles.Vehicle;
import graphics.vehicles.types.Car;
import graphics.vehicles.types.Motorcycle;
import graphics.vehicles.types.Truck;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class PlayPane extends Group implements gamePane{
    private final Stage stage;
    private Scene parentScene = null;
    private int xSize = (Game.WINDOW_WIDTH/40);
    private int ySize = (Game.WINDOW_HEIGHT/30);
    private Vehicle v1, v2;

    Canvas playCanvas = new Canvas(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
    GraphicsContext gc;

    public PlayPane(Stage stage) {
        this.stage = stage;
        this.getChildren().add(playCanvas);
        this.gc = playCanvas.getGraphicsContext2D();
    }

    public void startGame(int v1Ind, int v2Ind) {
        Scoreboard scoreboard = new Scoreboard();
        Map map = new Map(stage, parentScene, scoreboard, gc);
        // SETUP ELEMENTS
        createVehicle(v1Ind, v2Ind, map);

        GameTimer gameTimer = new GameTimer(stage, gc, map, scoreboard);

        // SETUP DISASTERS
        DisasterManager disasterManager = new DisasterManager(stage, parentScene, v1, v2);
        disasterManager.autoRandomDisaster();

        // SETUP HUD
        HUD gameHUD = new HUD(300, scoreboard);
        this.getChildren().add(gameHUD);
        gameHUD.setTranslateX(0);
        gameHUD.setTranslateY(0);
        gameHUD.startHUDAutoUpdate();

        // PREPARE FOR START
        gameTimer.setUpGame(v1, v2);
        gameTimer.start();
    }

    private void createVehicle(int p1Index, int p2Index, Map map) {
        switch(p1Index){
            case 0:
                v1 = new Car(xSize*18, ySize*16, Vehicle.PLAYER_ONE, parentScene, map, 50, 50);
                break;
            case 1:
                v1 = new Motorcycle(xSize*18, ySize*16, Vehicle.PLAYER_ONE, parentScene, map, 50, 50);
                break;
            case 2:
                v1 = new Truck(xSize*18, ySize*16, Vehicle.PLAYER_ONE, parentScene, map, 50, 50);
                break;
        }
        switch (p2Index){
            case 0:
                v2 = new Car(xSize*22, ySize*16, Vehicle.PLAYER_TWO, parentScene, map, 50, 50);
                break;
            case 1:
                v2 = new Motorcycle(xSize*22, ySize*16, Vehicle.PLAYER_TWO, parentScene, map, 50, 50);
                break;
            case 2:
                v2 = new Truck(xSize*22, ySize*16, Vehicle.PLAYER_TWO, parentScene, map, 50, 50);
                break;
        }
    }

    @Override
    public void setParentScene(Scene scene) {
        this.parentScene = scene;
    }
}
