/**
 * PlayPane is the main gameplay panel that manages the game's visual elements and interactions.
 * It contains the game loop, vehicle management, disaster management, scoreboard, and HUD.
 */
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

public class PlayPane extends Group implements gamePane {
    private final Stage stage;
    private Scene parentScene = null; 
    private int xSize = (Game.WINDOW_WIDTH / 40); 
    private int ySize = (Game.WINDOW_HEIGHT / 32);
    private Vehicle v1, v2;

    //Canvas for rendering the game elements
    Canvas playCanvas = new Canvas(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
    GraphicsContext gc; // GraphicsContext for drawing to the canvas

    /**
     * Constructor for the PlayPane.
     * Initializes the canvas and the graphics context used for rendering the game.
     * 
     * @param stage The stage for the game
     */
    public PlayPane(Stage stage) {
        this.stage = stage;
        this.getChildren().add(playCanvas);
        this.gc = playCanvas.getGraphicsContext2D();
    }

    /**
     * Starts the game by initializing the vehicles, scoreboard, map, and other gameplay elements.
     * It also sets up the disaster manager and the HUD, and begins the game timer.
     * 
     * @param v1Ind Index for Player One's vehicle type
     * @param v2Ind Index for Player Two's vehicle type
     * @param v1ColorInd Color index for Player One's vehicle
     * @param v2ColorInd Color index for Player Two's vehicle
     */
    public void startGame(int v1Ind, int v2Ind, int v1ColorInd, int v2ColorInd) {
        Scoreboard scoreboard = new Scoreboard();
        Map map = new Map(stage, parentScene, scoreboard, gc);
        
        //Set up vehicles for the players
        createVehicle(v1Ind, v2Ind, v1ColorInd, v2ColorInd, map);

        //Initialize and start the game timer
        GameTimer gameTimer = new GameTimer(stage, gc, map, scoreboard);
        
        //Set up and manage disasters in the game
        DisasterManager disasterManager = new DisasterManager(stage, parentScene, v1, v2);
        disasterManager.autoRandomDisaster(); // Automatically trigger a random disaster

        //Initialize and display the HUD (Heads-Up Display)
        HUD gameHUD = new HUD(300, scoreboard);
        this.getChildren().add(gameHUD); 
        gameHUD.setTranslateX(0);
        gameHUD.setTranslateY(0);
        gameHUD.startHUDAutoUpdate();

        //Start game
        gameTimer.setUpGame(v1, v2);
        gameTimer.start();
    }

    /**
     * Creates the vehicles for Player One and Player Two based on the provided indexes and colors.
     * 
     * @param p1Index    Vehicle type index for Player One
     * @param p2Index    Vehicle type index for Player Two
     * @param p1ColorInd Color index for Player One's vehicle
     * @param p2ColorInd Color index for Player Two's vehicle
     * @param map        The game map
     */
    private void createVehicle(int p1Index, int p2Index, int p1ColorInd, int p2ColorInd, Map map) {
        String path;
        
        //Create Player One's vehicle based on the selected index and color
        switch (p1Index) {
            case 0: // Car
                path = getClass().getResource("/assets/sprites/vehicle-sheets/car/" + p1ColorInd + "-car.png").toExternalForm();
                v1 = new Car(xSize * 18, ySize * 16, Vehicle.PLAYER_ONE, parentScene, map, 50, 50, path);
                break;
            case 1: // Motorcycle
                path = getClass().getResource("/assets/sprites/vehicle-sheets/motor/" + p1ColorInd + "-motor.png").toExternalForm();
                v1 = new Motorcycle(xSize * 18, ySize * 16, Vehicle.PLAYER_ONE, parentScene, map, 50, 50, path);
                break;
            case 2: // Truck
                path = getClass().getResource("/assets/sprites/vehicle-sheets/truck/" + p1ColorInd + "-truck.png").toExternalForm();
                v1 = new Truck(xSize * 18, ySize * 16, Vehicle.PLAYER_ONE, parentScene, map, 50, 50, path);
                break;
        }

        // Create Player Two's vehicle based on the selected index and color
        switch (p2Index) {
            case 0: //Car
                path = getClass().getResource("/assets/sprites/vehicle-sheets/car/" + p2ColorInd + "-car.png").toExternalForm();
                v2 = new Car(xSize * 22, ySize * 16, Vehicle.PLAYER_TWO, parentScene, map, 50, 50, path);
                break;
            case 1: //Motorcycle
                path = getClass().getResource("/assets/sprites/vehicle-sheets/motor/" + p2ColorInd + "-motor.png").toExternalForm();
                v2 = new Motorcycle(xSize * 22, ySize * 16, Vehicle.PLAYER_TWO, parentScene, map, 50, 50, path);
                break;
            case 2: //Truck
                path = getClass().getResource("/assets/sprites/vehicle-sheets/truck/" + p2ColorInd + "-truck.png").toExternalForm();
                v2 = new Truck(xSize * 22, ySize * 16, Vehicle.PLAYER_TWO, parentScene, map, 50, 50, path);
                break;
        }
    }

    /**
     * Sets the parent scene for the PlayPane.
     * 
     * @param scene The parent scene to be set
     */
    @Override
    public void setParentScene(Scene scene) {
        this.parentScene = scene;
    }
}
