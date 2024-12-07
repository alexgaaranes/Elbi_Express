package graphics.disaster;

import game.Game;
import game.panes.PlayPane;
import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Blackout extends Disaster {
    PlayPane playPane;
    Rectangle blackOutRect;
    Circle v1Flash, v2Flash;
    private boolean isActive = false;

    Pane layer = new Pane();

    protected Blackout(Stage stage, Scene parentScene, Vehicle v1, Vehicle v2) {
        super(stage, parentScene, v1, v2);
        playPane = (PlayPane) parentScene.getRoot();
        blackOutRect = new Rectangle(Game.WINDOW_WIDTH,Game.WINDOW_HEIGHT);
        blackOutRect.setFill(Color.valueOf("#000000"));
        v1Flash = new Circle(50, Color.valueOf("e5e5e5"));
        v2Flash = new Circle(50, Color.valueOf("e5e5e5"));
        layer.getChildren().addAll(blackOutRect,v1Flash,v2Flash);
        layer.setBlendMode(BlendMode.MULTIPLY);
    }

    @Override
    protected void spawnDisaster() {
        System.out.println("Blackout Spawned");
        playPane.getChildren().addAll(layer);
        long startTime = System.nanoTime();
        isActive = true;
        trackFlashVehicle(v1Flash, v1);
        trackFlashVehicle(v2Flash, v2);
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                if(l-startTime >= 10000000000L){
                    playPane.getChildren().removeAll(layer);
                    isActive = false;
                    this.stop();
                }
            }
        }.start();
    }

    // TRACK VEHICLE POS FOR FLASHLIGHT
    private void trackFlashVehicle(Circle flash, Vehicle vehicle) {
        flash.setEffect(new GaussianBlur(20));
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                if(!isActive) this.stop();
                flash.setCenterX(vehicle.getxPos());
                flash.setCenterY(vehicle.getyPos());
            }
        }.start();
    }
}
