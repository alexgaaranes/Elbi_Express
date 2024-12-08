package graphics.misc;

import game.Game;
import game.Scoreboard;
import game.Timer;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
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
    private static String hexCode1 = "#8a1538";
    private static String hexCode2 = "#00573f";
    private static String hexCode3 = "#ffb81c";
    private DropShadow dropShadow = new DropShadow();

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
                scoreboard.checkTimeLeft(gameTimer.getTime());
            }
        }.start();
    }

    // TIMER
    private void setUpTimer(){
        timeText = new Text();
        timeText.setFont(Font.loadFont("file:src/assets/sprites/bitFont.TTF", 20));
        timeText.setFill(Color.web(hexCode3.trim()));
        timeText.setX((double) Game.WINDOW_WIDTH /2 - timeText.getBoundsInLocal().getWidth()/2 - 25);
        timeText.setY(30);
        
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setBlurType(BlurType.ONE_PASS_BOX);
        dropShadow.setRadius(10);
        dropShadow.setColor(Color.BLACK);
        
        timeText.setEffect(dropShadow);
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
        scoreText.setFont(Font.loadFont("file:src/assets/sprites/bitFont.TTF", 20));
        scoreText.setFill(Color.web(hexCode3.trim()));
        scoreText.setX(30);
        scoreText.setY(30);
        
        scoreText.setEffect(dropShadow);
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
        happyText.setFont(Font.loadFont("file:src/assets/sprites/bitFont.TTF", 20));
        happyText.setFill(Color.web(hexCode3.trim()));
        happyText.setX(Game.WINDOW_WIDTH-BAR_WIDTH*3+120);
        happyText.setY(30);
        // SETUP BAR
        happinessBar = new Group();
        redBar = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.web(hexCode1.trim()));
        greenBar = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.web(hexCode2.trim()));
        barBorder = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.web(hexCode3.trim()));
        barBorder.setFill(Color.TRANSPARENT);
        barBorder.setStroke(Color.web(hexCode3.trim()));
        happinessBar.getChildren().addAll(redBar,greenBar,barBorder);
        happinessBar.setTranslateX(Game.WINDOW_WIDTH-BAR_WIDTH-20);
        happinessBar.setTranslateY(10);
        
        happyText.setEffect(dropShadow);
        this.getChildren().addAll(happyText,happinessBar);
    }

    private void updateHappinessBar(){
        greenBar.setWidth(
                BAR_WIDTH - ((double) (Scoreboard.MAX_HAPPINESS_LVL - scoreboard.getHappinessLvl()) /
                        Scoreboard.MAX_HAPPINESS_LVL)*BAR_WIDTH
        );
    }

}
