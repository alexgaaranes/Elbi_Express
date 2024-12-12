/**
 * HUD (Heads-Up Display) class is responsible for rendering and updating the 
 * visual elements that are displayed during the gameplay, including:
 * - Timer
 * - Score
 * - Happiness level bar
 * The HUD updates automatically during the game and provides real-time feedback 
 * to the player about these elements.
 */

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
    private static String hexCode1 = "#8a1538"; //Hex color code for the red bar
    private static String hexCode2 = "#00573f"; //Hex color code for the green bar
    private static String hexCode3 = "#ffb81c"; //Hex color code for text and borders
    private DropShadow dropShadow = new DropShadow();

    private final static float BAR_HEIGHT = 30;
    private final static float BAR_WIDTH = 200; 
    
    //HUD ELEMENTS (VISUALS)
    private Text timeText; 
    private Text scoreText; 
    private Text happyText; 
    private Rectangle redBar; 
    private Rectangle greenBar; //Green part of the happiness bar (changes width)
    private Rectangle barBorder; //Border around the happiness bar
    private Group happinessBar; 

    /**
     * Constructor to initialize the HUD with a given time and scoreboard.
     * 
     * @param time The game time in seconds
     * @param scoreboard The scoreboard to track and display the player's score
     */
    public HUD(int time, Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
        this.gameTimer = new Timer(time);   // Time in seconds
        this.hudCanvas = new Canvas(Game.WINDOW_HEIGHT, Game.WINDOW_WIDTH);
        this.gc = this.hudCanvas.getGraphicsContext2D();

        //Setup the HUD elements
        setUpTimer();
        setUpScore();
        setUpHappiness();
    }

    /**
     * Starts the automatic updates for the HUD elements, such as the timer, score, 
     * and happiness bar. It uses an AnimationTimer for continuous updates.
     */
    public void startHUDAutoUpdate(){
        gameTimer.start();
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                //Update the HUD elements
                updateTimer();
                updateScore();
                updateHappinessBar();
                scoreboard.checkTimeLeft(gameTimer.getTime());
            }
        }.start();
    }

    //TIMER SECTION
    /**
     * Sets up the timer text display on the HUD.
     */
    private void setUpTimer(){
        timeText = new Text();
        timeText.setFont(Font.loadFont(getClass().getResource("/assets/sprites/bitFont.TTF").toExternalForm(), 20));
        timeText.setFill(Color.web(hexCode3.trim()));
        timeText.setX((double) Game.WINDOW_WIDTH / 2 - timeText.getBoundsInLocal().getWidth() / 2 - 25);
        timeText.setY(30);
        
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setBlurType(BlurType.ONE_PASS_BOX);
        dropShadow.setRadius(10);
        dropShadow.setColor(Color.BLACK);
        
        timeText.setEffect(dropShadow);
        this.getChildren().add(timeText);
    }

    /**
     * Updates the timer text based on the current game time.
     */
    private void updateTimer(){
        int minutes = (int) gameTimer.getTimeSec() / 60;
        int seconds = (int) gameTimer.getTimeSec() - (minutes * 60);
        timeText.setText(String.format("%02d:%02d", minutes, seconds));
    }

    //SCORE SECTION
    /**
     * Sets up the score display on the HUD.
     */
    private void setUpScore(){
        scoreText = new Text();
        scoreText.setFont(Font.loadFont(getClass().getResource("/assets/sprites/bitFont.TTF").toExternalForm(), 20));
        scoreText.setFill(Color.web(hexCode3.trim()));
        scoreText.setX(30);
        scoreText.setY(30);
        
        scoreText.setEffect(dropShadow);
        this.getChildren().add(scoreText);
    }

    /**
     * Updates the score text based on the current score.
     */
    private void updateScore(){
        int totalScore = scoreboard.getTotalScore();
        scoreText.setText(String.format("Score: %d", totalScore));
    }

    //HAPPINESS BAR SECTION
    /**
     * Sets up the happiness level text and the associated bar on the HUD.
     */
    private void setUpHappiness(){
        happyText = new Text();
        happyText.setText("Happiness Level: ");
        happyText.setFont(Font.loadFont(getClass().getResource("/assets/sprites/bitFont.TTF").toExternalForm(), 20));
        happyText.setFill(Color.web(hexCode3.trim()));
        happyText.setX(Game.WINDOW_WIDTH - BAR_WIDTH * 3 + 120);
        happyText.setY(30);

        //Set up the happiness bar (red, green, and border)
        happinessBar = new Group();
        redBar = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.web(hexCode1.trim()));
        greenBar = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.web(hexCode2.trim()));
        barBorder = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.web(hexCode3.trim()));
        barBorder.setFill(Color.TRANSPARENT);
        barBorder.setStroke(Color.web(hexCode3.trim()));
        happinessBar.getChildren().addAll(redBar, greenBar, barBorder);
        happinessBar.setTranslateX(Game.WINDOW_WIDTH - BAR_WIDTH - 20);
        happinessBar.setTranslateY(10);

        happyText.setEffect(dropShadow);
        this.getChildren().addAll(happyText, happinessBar);
    }

    /**
     * Updates the width of the green portion of the happiness bar based on the 
     * current happiness level.
     */
    private void updateHappinessBar(){
        greenBar.setWidth(
                BAR_WIDTH - ((double) (Scoreboard.MAX_HAPPINESS_LVL - scoreboard.getHappinessLvl()) /
                        Scoreboard.MAX_HAPPINESS_LVL) * BAR_WIDTH
        );
    }
}
