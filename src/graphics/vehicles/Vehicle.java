package graphics.vehicles;

import game.Game;
import graphics.Graphic;
import graphics.map.Map;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.Rotate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Vehicle extends Graphic {
    private final Scene parentScene;
    private final String playerID;
    private static Set<KeyCode> activeKeys = new HashSet<>();
    private HashMap<String, KeyCode> keyBinds = new HashMap<>();

    public static final String PLAYER_ONE = "P1";
    public static final String PLAYER_TWO = "P2";

    private double angle;
    private double velocity;
    private final double acceleration = 0.005;
    private final double maxVelocity = 0.5;
    private final double turningSpeed = 0.75;
    private final double scale = 0.5;
    private final Map map;

    public Vehicle(Image image, double xPos, double yPos, String id, Scene parentScene, Map map) {
        super(image, xPos, yPos);
        this.parentScene = parentScene;
        this.playerID = id;
        this.map = map;
        this.angle = 0;

        this.setKeyBinds();
        this.handleKeyOnPress();
    }

    private void setKeyBinds(){
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


    private void handleKeyOnPress(){
        parentScene.setOnKeyPressed(event -> {
            activeKeys.add(event.getCode());
            System.out.println(event.getCode());
        });
        parentScene.setOnKeyReleased(event -> {
            activeKeys.remove(event.getCode());
            System.out.println("Removed:"+event.getCode());
        });
        new AnimationTimer() {
            public void handle(long nanoTime) {
                moveVehicle();
            }
        }.start();
    }

    protected void moveVehicle(){
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
            angle -= turningSpeed;
        }
        if (activeKeys.contains(keyBinds.get("RIGHT"))) {
            angle += turningSpeed;
        }

        double radAngle = Math.toRadians(angle);
        double displaceX = velocity * Math.cos(radAngle);
        double displaceY = velocity * Math.sin(radAngle);

        if(isColliding(this.xPos+displaceX, this.yPos)){
            displaceX = 0;
        }
        if(isColliding(this.xPos, this.yPos+displaceY)){
            displaceY = 0;
        }
        this.xPos += displaceX;
        this.yPos += displaceY;
    }

    // Wall Collision
    private boolean isColliding(double newX, double newY){
        // Get the position on grid (get tile uses the screen size so this is accurate)
        int xGridPos = (int) (newX / map.getTileW());
        int yGridPos = (int) (newY / map.getTileH());

        // Window bounds
        if(newX >= Game.WINDOW_WIDTH || newX <= 0 || newY >= Game.WINDOW_HEIGHT || newY <= 0) return true;

        // Store Area
        if(map.getMapMatrix()[yGridPos][xGridPos] == 2){
            map.getStoreMap().get(xGridPos+"-"+yGridPos).openObjective(this);
            return false;
        }

        return map.getMapMatrix()[yGridPos][xGridPos]==1;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.save();
        // Get Rotation and draw image
        Rotate r = new Rotate(this.angle, this.xPos, this.yPos);
        gc.setTransform(r.getMxx(),r.getMyx(),r.getMxy(),r.getMyy(),r.getTx(),r.getTy());
        gc.drawImage(this.image,
                xPos - (this.image.getWidth()*this.scale) / 2,
                yPos - (this.image.getHeight()*this.scale) / 2,
                this.image.getWidth()*this.scale,
                this.image.getHeight()*this.scale
                );

        gc.restore();
    }

    /*Rotation reference: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/canvas/GraphicsContext.html#setTransform-double-double-double-double-double-double-*/

}
