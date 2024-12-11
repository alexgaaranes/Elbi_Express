package graphics.map;

import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Random;

abstract public class Objective {
    protected final Map map;
    protected final GraphicsContext gc;
    protected int xGridPos, yGridPos;
    protected double xPos, yPos;
    protected Vehicle p1, p2;
    protected Vehicle occupiedVehicle = null;
    protected final float PROMPT_SCALE = 0.15F;

    Objective(int xGridPos, int yGridPos, Map map, GraphicsContext gc) {
        this.xGridPos = xGridPos;
        this.yGridPos = yGridPos;
        this.map = map;
        this.gc = gc;

        this.xPos = xGridPos * map.getTileW();
        this.yPos = yGridPos * map.getTileH();

    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(this.xPos, this.yPos, map.getTileW()*3, map.getTileH()*2);
    }

    public void trackVehicle(Vehicle v1, Vehicle v2) {
        this.p1 = v1;
        this.p2 = v2;
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(bodyEnters(p1)){
                    if(occupiedVehicle == null){
                        setVehicle(p1);
                        openObjective();
                    }
                } else if(bodyEnters(p2)){
                    if(occupiedVehicle == null){
                        setVehicle(p2);
                        openObjective();
                    }
                } else {
                    occupiedVehicle = null;
                }
            }
        }.start();
    }

    public boolean bodyEnters(Vehicle vehicle) {
        return vehicle.getBounds().intersects(this.getBounds());
    }

    public synchronized void setVehicle(Vehicle vehicle) {
        this.occupiedVehicle = vehicle;
    }

    public void openObjective() {
        System.out.println("In objective: "+this.occupiedVehicle);
    }

    abstract protected void doProcess();

    protected void showPrompt() {
        Random r = new Random();
        ArrayList<KeyCode> promptKeys = new ArrayList<>();

        // Get Prompt Keys
        if(this.occupiedVehicle.getPlayerID().equals(Vehicle.PLAYER_ONE)){
            KeyCode[] possibleKeys = {KeyCode.Q, KeyCode.E};
            for(int i=0; i<4; i++){
                promptKeys.add(possibleKeys[r.nextInt(possibleKeys.length)]);
            }
        } else if(this.occupiedVehicle.getPlayerID().equals(Vehicle.PLAYER_TWO)){
            KeyCode[] possibleKeys = {KeyCode.PERIOD, KeyCode.SLASH};
            for(int i=0; i<4; i++){
                promptKeys.add(possibleKeys[r.nextInt(possibleKeys.length)]);
            }
        }

        // Key Input Handler
        new AnimationTimer() {
            KeyCode lastKey = null;
            @Override
            public void handle(long l) {
                if(occupiedVehicle == null){this.stop();}
                if(promptKeys.isEmpty()){
                    doProcess();
                    this.stop();
                }
                if(lastKey != null && !Vehicle.getActiveKeys().contains(lastKey)){lastKey = null;}
                if(lastKey == null && Vehicle.getActiveKeys().contains(promptKeys.getFirst())){
                    lastKey = promptKeys.removeFirst();
                }
            }
        }.start();

        // Render Prompt
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                if(occupiedVehicle == null){this.stop();}
                for(int i=0; i<promptKeys.size(); i++){
                    Image keyImage = new Image(
                    		getClass().getResource("file:src/assets/sprites/keys/key"+promptKeys.get(i).getName().toUpperCase()+".png").toExternalForm(
                    ));
                    gc.drawImage(keyImage,
                            xPos-(map.getTileW()/2)
                            +i*keyImage.getWidth()*PROMPT_SCALE,
                            yPos-map.getTileH()/2,
                            keyImage.getWidth()*PROMPT_SCALE, keyImage.getHeight()*PROMPT_SCALE
                    );
                }
            }
        }.start();

    }

}
