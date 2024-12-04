package graphics.disaster;

import game.Game;
import game.panes.PlayPane;
import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;

public class Blackout extends Disaster {
    PlayPane playPane;
    Rectangle blackOutRect;
    Rectangle whiteBox;

    Pane layer = new Pane();

    protected Blackout(Stage stage, Scene parentScene, Vehicle v1, Vehicle v2) {
        super(stage, parentScene, v1, v2);
        playPane = (PlayPane) parentScene.getRoot();
        blackOutRect = new Rectangle(Game.WINDOW_WIDTH,Game.WINDOW_HEIGHT);
        blackOutRect.setFill(Color.valueOf("#0d0d0d"));
        whiteBox = new Rectangle(500,500);
        whiteBox.setFill(Color.valueOf("#e5e5e5"));
        whiteBox.setEffect(new GaussianBlur(30));

        layer.getChildren().addAll(blackOutRect,whiteBox);
        layer.setBlendMode(BlendMode.MULTIPLY);
    }

    @Override
    protected void spawnDisaster() {
        System.out.println("Blackout Spawned");
        playPane.getChildren().addAll(layer);
        long startTime = System.nanoTime();
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                if(l-startTime >= 10000000000L){
                    playPane.getChildren().removeAll(layer);
                    this.stop();
                }
            }
        }.start();
    }
}
