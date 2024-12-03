package graphics.misc;

import game.Game;
import game.Scoreboard;
import game.Timer;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HUD extends Group {
    private Timer gameTimer;
    private Canvas hudCanvas;
    private GraphicsContext gc;
    private Scoreboard scoreboard;

    private final static float BAR_HEIGHT = 30;
    private final static float BAR_WIDTH = 200;
    // HUD ELEMENTS (VISUALS)
    private Text timeText;
    private Text scoreText;
    private Text happyText;
    private Rectangle redBar;
    private Rectangle greenBar;
    private Rectangle barBorder;
    private Group happinessBar;

    public HUD(int time, Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
        this.gameTimer = new Timer(time);   // Time in sec
        this.hudCanvas = new Canvas(Game.WINDOW_HEIGHT, Game.WINDOW_WIDTH);
        this.gc = this.hudCanvas.getGraphicsContext2D();

        // SETUP
        setUpTimer();
        setUpScore();
        setUpHappiness();
    }

    // Methods
    // AUTO UPDATE HUD
    public void startHUDAutoUpdate(){
        gameTimer.start();
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                // Element Updates
                updateTimer();
                updateScore();
                updateHappinessBar();
            }
        }.start();
    }

    // TIMER
    private void setUpTimer(){
        timeText = new Text();
        timeText.setFont(new Font(20));
        timeText.setFill(Color.BLACK);
        timeText.setX((double) Game.WINDOW_WIDTH /2 - timeText.getBoundsInLocal().getWidth()/2 - 25);
        timeText.setY(30);
        this.getChildren().add(timeText);
    }

    private void updateTimer(){
        int minutes = (int) gameTimer.getTimeSec()/60;
        int seconds = (int) gameTimer.getTimeSec()-(minutes*60);
        timeText.setText(String.format("%02d:%02d",
                    minutes, seconds
                ));
    }

    // SCORE
    private void setUpScore(){
        scoreText = new Text();
        scoreText.setFont(new Font(20));
        scoreText.setFill(Color.BLACK);
        scoreText.setX(30);
        scoreText.setY(30);
        this.getChildren().add(scoreText);
    }

    private void updateScore(){
        int totalScore = scoreboard.getTotalScore();
        scoreText.setText(String.format("Score: %d", totalScore));
    }

    // HAPPINESS
    private void setUpHappiness(){
        happyText = new Text();
        happyText.setText("Happiness Level: ");
        happyText.setFont(new Font(20));
        happyText.setFill(Color.BLACK);
        happyText.setX(Game.WINDOW_WIDTH-BAR_WIDTH*2+10);
        happyText.setY(30);
        // SETUP BAR
        happinessBar = new Group();
        redBar = new Rectangle(BAR_WIDTH,BAR_HEIGHT,Color.RED);
        greenBar = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.GREEN);
        barBorder = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.BLUE);
        barBorder.setFill(Color.TRANSPARENT);
        barBorder.setStroke(Color.BLUE);
        happinessBar.getChildren().addAll(redBar,greenBar,barBorder);
        happinessBar.setTranslateX(Game.WINDOW_WIDTH-BAR_WIDTH-20);
        happinessBar.setTranslateY(10);

        this.getChildren().addAll(happyText,happinessBar);
    }

    private void updateHappinessBar(){
        greenBar.setWidth(
                BAR_WIDTH - ((double) (Scoreboard.MAX_HAPPINESS_LVL - scoreboard.getHappinessLvl()) /
                        Scoreboard.MAX_HAPPINESS_LVL)*BAR_WIDTH
        );
    }
}
