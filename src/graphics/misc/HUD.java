package graphics.misc;

import game.Game;
import game.Timer;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HUD extends HBox{
    private Timer gameTimer;
    private Canvas hudCanvas;
    private GraphicsContext gc;

    // HUD ELEMENTS (VISUALS)
    private Text timeText;

    public HUD(int time, int spacing){
        super(spacing);
        this.gameTimer = new Timer(time);   // Time in sec
        this.hudCanvas = new Canvas(Game.WINDOW_HEIGHT, Game.WINDOW_WIDTH);
        this.gc = this.hudCanvas.getGraphicsContext2D();

        setUpTimer();
    }

    // Methods
    public void startHUDAutoUpdate(){
        gameTimer.start();
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                // Element Updates
                updateTimer();
            }
        }.start();
    }

    private void setUpTimer(){
        timeText = new Text();
        timeText.setFont(new Font(20));
        timeText.setFill(Color.BLACK);
        this.getChildren().add(timeText);
    }

    private void updateTimer(){
        int minutes = (int) gameTimer.getTimeSec()/60;
        int seconds = (int) gameTimer.getTimeSec()-(minutes*60);
        timeText.setText(String.format("%02d:%02d",
                    minutes, seconds
                ));
    }
}
