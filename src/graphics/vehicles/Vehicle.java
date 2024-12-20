/**
 * The Vehicle class represents a player the interacts with the map as vehicles.
 * It handles movement (lateral and rotational) of player vehicles, 
 * loading and unloading of orders.
 */
package graphics.vehicles;

import game.Audio;
import game.Game;
import game.Scoreboard;
import game.panes.PlayPane;
import graphics.Graphic;
import graphics.map.Map;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Vehicle extends Graphic {
    protected final Scene parentScene;
    protected final String playerID;
    protected final PlayPane playPane;
    protected static Set<KeyCode> activeKeys = new HashSet<>();
    protected HashMap<String, KeyCode> keyBinds = new HashMap<>();

    public static final String PLAYER_ONE = "P1";
    public static final String PLAYER_TWO = "P2";

    protected HashMap<String, Integer> orderPerStore = new HashMap<>();

    protected int score = 0;  // Score tracked per vehicle
    protected int currentLoad = 0;
    protected final int MAX_CAPACITY;
    protected Text capText;

    protected double angle;
    protected double velocity;

    protected double acceleration;
    protected double maxVelocity;
    protected double turningSpeed;

    protected double scale;
    protected int frameWidth;
    protected int frameHeight;
    protected final Map map;

    protected boolean onEffect = false;
    
    /**
     * Constructor to initialize the vehicle.
     *
     * @param image The sprite used for the selected vehicle.
     * @param xPos The x-coordinate of the objective on the grid.
     * @param yPos The y-coordinate of the objective on the grid.
     * @param id The identification of the player associated to the vehicle.
     * @param map The game map.
     * @param width The width of image to be used.
     * @param height The height of the image to be used.
     * @param capacity The carrying capacity of the selected vehicle.
     * @param scale The scale of the image relative to the original resolution.
     * @param gc The GraphicsContext for rendering.
     */
    public Vehicle(Image image, double xPos, double yPos, String id, Scene parentScene, Map map, double width, double height, int capacity, double scale) {
        super(image, xPos, yPos, width, height);
        this.parentScene = parentScene;
        this.playerID = id;
        this.map = map;
        this.angle = 0;
        this.MAX_CAPACITY = capacity;
        this.scale = scale;

        this.playPane = (PlayPane) parentScene.getRoot();

        this.defaultKeyBinds();
        this.handleKeyOnPress();
        this.updateCapText();
    }

	/**
	 * Assigns movement controls for each player.
	 */
    public void defaultKeyBinds(){
        if(this.playerID.equals(PLAYER_ONE)){
            keyBinds.put("UP", KeyCode.W);
            keyBinds.put("DOWN", KeyCode.S);
            keyBinds.put("LEFT", KeyCode.A);
            keyBinds.put("RIGHT", KeyCode.D);
        } else if (this.playerID.equals(PLAYER_TWO)){
            keyBinds.put("UP", KeyCode.UP);
            keyBinds.put("DOWN", KeyCode.DOWN);
            keyBinds.put("LEFT", KeyCode.LEFT);
            keyBinds.put("RIGHT", KeyCode.RIGHT);
        }
    }

    /**
	 * Timer that retrieves control from the player.
	 */
    protected void handleKeyOnPress(){
        parentScene.setOnKeyPressed(event -> {
            activeKeys.add(event.getCode());
        });
        parentScene.setOnKeyReleased(event -> {
            activeKeys.remove(event.getCode());
        });
        new AnimationTimer() {
            long prevTime = 0;
            @Override
            public void handle(long nanoTime) {
                //Added delta as another factor for movement which measures the time between frames
                //References: https://en.wikipedia.org/wiki/Delta_timing
                //https://manual.gamemaker.io/lts/en/GameMaker_Language/GML_Reference/Maths_And_Numbers/Date_And_Time/delta_time.htm(Godot also uses delta)
                if(prevTime > 0) {
                    double delta = (nanoTime - prevTime)/1000000000.0;
                    moveVehicle(delta);
                }
                this.prevTime = nanoTime;
            }
        }.start();
    }

    /**
     * Handles the lateral and rotational movement of the vehicle through key event listeners.
     * 
	 * @param delta The delta time that tracks the code-block execution in real-time.
	 */
    protected void moveVehicle(double delta){
        if(activeKeys.contains(keyBinds.get("UP"))){
            velocity = Math.min(velocity + acceleration, maxVelocity);
        } else if(activeKeys.contains(keyBinds.get("DOWN"))){
            velocity = Math.max(velocity - acceleration, -1*maxVelocity);
        } else {
            if(velocity > 0){
                velocity = Math.max(velocity - acceleration, 0);
            } else if(velocity < 0){
                velocity = Math.min(velocity + acceleration, maxVelocity);
            }
        }
        if (activeKeys.contains(keyBinds.get("LEFT"))) {
            angle -= turningSpeed*delta;
        }
        if (activeKeys.contains(keyBinds.get("RIGHT"))) {
            angle += turningSpeed*delta;
        }

        if(angle < 0) angle += 360;
        if(angle > 360) angle -= 360;

        double radAngle = Math.toRadians(angle);
        double displaceX = velocity * Math.cos(radAngle)*delta;
        double displaceY = velocity * Math.sin(radAngle)*delta;

        if(isColliding(this.xPos+displaceX, this.yPos)){
            displaceX = 0;
        }
        if(isColliding(this.xPos, this.yPos+displaceY)){
            displaceY = 0;
        }
        if(displaceX != 0 || displaceY !=0){
        }
        this.xPos += displaceX;
        this.yPos += displaceY;
    }
    
    /**
     * Handles collisions of vehicles relative to map positions
     * 
	 * @param newX The current x-coordinate of the vehicle in the map grid
	 * @param newY The current y-coordinate of the vehicle in the map grid
	 */
    protected boolean isColliding(double newX, double newY){
        // Get the position on grid (get tile uses the screen size so this is accurate)
        int xGridPos = (int) (newX / map.getTileW());
        int yGridPos = (int) (newY / map.getTileH());

        // Window bounds
        if(newX >= Game.WINDOW_WIDTH || newX <= 0 || newY >= Game.WINDOW_HEIGHT || newY <= 0) return true;

        return this.map.getMapMatrix()[yGridPos][xGridPos]==1;
    }

    /**
     * Methods that tracks the order values associated with each player...
	 */
    public boolean isFull() {
        return this.currentLoad == this.MAX_CAPACITY;
    }

    public boolean isEmpty() {
        return this.currentLoad == 0;
    }

    /**
     * @param val The value that tells either to increment or decrement the current order on hand count.
     */
    public void updateLoad(int val){
        this.currentLoad += val;
    }

    public HashMap<String, Integer> getStoreOrder(){
        return this.orderPerStore;
    }
    
    /**
     * ...until here.
     */

    /**
     * Handles the cumulative score tracking.
     * 
     * @param val The value of score added to the current score.
     */
    public void updateScore(int val){
        this.score += val;
    }

    /**
     * @return Getters of private attributes...
     */
    public int getScore(){
        return this.score;
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }
    
    public String getPlayerID(){
        return this.playerID;
    }
    /**
     * ...until here.
     */

    public static Set<KeyCode> getActiveKeys(){
        return Vehicle.activeKeys;
    }

    /**
     * Toggles earthquake effects on the vehicles.
     * Reverses the direction of the controls.
     */
    public void earthquakeToggleEffect(){
        if(onEffect){
            onEffect = false;
            this.defaultKeyBinds();
            return;
        }
        if(this.playerID.equals(PLAYER_ONE)){
            keyBinds.put("UP", KeyCode.S);
            keyBinds.put("DOWN", KeyCode.W);
            keyBinds.put("LEFT", KeyCode.D);
            keyBinds.put("RIGHT", KeyCode.A);
        } else if(this.playerID.equals(PLAYER_TWO)){
            keyBinds.put("UP", KeyCode.DOWN);
            keyBinds.put("DOWN", KeyCode.UP);
            keyBinds.put("LEFT", KeyCode.RIGHT);
            keyBinds.put("RIGHT", KeyCode.LEFT);
        }
        onEffect = true;
    }

    /**
     * Toggles thunderstorm effects on the vehicles.
     * Increases the acceleration by a factor.
     */
    public void thunderstormToggleEffect(){
        if(onEffect){
            onEffect = false;
            this.acceleration *= 4;
            this.maxVelocity /= 4;
            this.turningSpeed /= 2;
            return;
        }
        this.acceleration /= 4;
        this.maxVelocity *= 4;
        this.turningSpeed *= 2;
        onEffect = true;
    }

    
    /**
     * Handles the tracking of current load of the vehicle.
     */
    private void updateCapText(){
        this.capText = new Text(String.format("%d/%d", currentLoad, MAX_CAPACITY));
        this.capText.setFill(Color.WHITE);
        this.capText.setFont(Font.loadFont(getClass().getResource("/assets/sprites/pixelFont.ttf").toExternalForm(), 10));
        this.playPane.getChildren().add(capText);
        
        //Timer that updates capacity indicator position of a vehicle.
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(Scoreboard.activeScoreboard.checkIfLost()){this.stop();}
                capText.setText(String.format("%d/%d", currentLoad, MAX_CAPACITY));
                capText.setX(xPos-20);
                capText.setY(yPos-20);
            }
        }.start();
    }

    /**
     * Handles the visual representation of the vehicle and its movement.
     * @param gc The GraphicsContext for rendering.
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.save();
        // Get Rotation and draw image
        int frame = getFrame(this.angle);
        int col = frame % 4;
        int row = frame / 4;
        gc.drawImage(this.image, col*frameWidth, row*frameHeight,
                frameWidth, frameHeight,
                xPos - (frameWidth*this.scale) / 2,
                yPos - (frameHeight*this.scale) / 2,
                frameWidth*this.scale,
                frameHeight*this.scale
                );

        gc.restore();
    }
    
    /**
     * Retrieves the current angle of image to be used in a specific frame.
     * 
     * @param angle The angle of which the vehicle is oriented.
     * @return The index of the of frame to be used.
     */
    private int getFrame(double angle){
        return (int) (angle/45) % 8;
    }
    
    /**
     * Handles the update in bounds relative to the current orientation of the vehicle.
     * @param w The width
     * @param h The height
     */
    protected void setFrameSize(int w, int h){
        this.frameWidth = w;
        this.frameHeight = h;
    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D(this.xPos, this.yPos, frameWidth*scale/2, frameHeight*scale/2);
    }

}
