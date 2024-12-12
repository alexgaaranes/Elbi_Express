/**
 * GameTimer.java
 * This class is responsible for managing the main game loop.
 * It extends AnimationTimer to handle per-frame updates.
 * It includes logic for rendering, game state updates, and game-over handling.
 */

package game;

import game.panes.MenuPane;
import game.panes.PlayPane;
import graphics.map.Household;
import graphics.map.Map;
import graphics.map.Store;
import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class GameTimer extends AnimationTimer {
    //Stage reference for scene management
    private final Stage stage;

    //GraphicsContext for rendering objects on the canvas
    GraphicsContext gc;

    //Game components
    private Vehicle v1;
    private Vehicle v2;
    private Map map;
    private Scoreboard scoreboard;

    //Game over image
    private static Image gameOver = new Image(GameTimer.class.getResource("/assets/sprites/won.png").toExternalForm());

    /**
     * Constructor to initialize the GameTimer.
     * @param stage The main stage for the game.
     * @param gc The GraphicsContext for rendering.
     * @param map The game map.
     * @param scoreboard The scoreboard to track game progress.
     */
    public GameTimer(Stage stage, GraphicsContext gc, Map map, Scoreboard scoreboard) {
        this.stage = stage;
        this.gc = gc;
        this.map = map;
        this.scoreboard = scoreboard;
    }

    /**
     * Set up the game by initializing vehicles and tracking logic for stores and households.
     * @param v1 Vehicle controlled by Player 1.
     * @param v2 Vehicle controlled by Player 2.
     */
    public void setUpGame(Vehicle v1, Vehicle v2) {
        this.v1 = v1;
        this.v2 = v2;

        //Setup detection logic for stores
        for(Store store: map.getStoreList()) {
            store.trackVehicle(v1, v2);
        }

        //Setup detection logic for households
        for(Household house: map.getHouseList()) {
            house.trackVehicle(v1, v2);
        }

        //Initialize random orders for households
        randomOrder();
        randomOrderTimer();
    }

    /**
     * Main game loop: Updates rendering and checks game state.
     * ! Avoid adding slow processes here !
     * @param l The current timestamp (nanoseconds).
     */
    @Override
    public void handle(long l) {
        //Clear the canvas
        gc.clearRect(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);

        //Render map, vehicles, and houses
        this.map.drawMap(gc);
        this.v1.render(this.gc);
        this.v2.render(this.gc);
        this.map.drawHouse(this.gc);

        // Check for game-over conditions
        if(this.scoreboard.checkIfLost()) {
            System.out.println("Game Over!");
            Audio.stopSound();
            if(scoreboard.getHappinessLvl() <= 0) {
                Audio.playSound("lose", 0.25, true); //Sound cue for losing the game
                System.out.println("You Lost!");
                gameOver = new Image(getClass().getResource("/assets/sprites/lose.png").toExternalForm());
            } else {
                Audio.playSound("win", 0.25, true);
                System.out.println("You Won!"); //Sound cue for winning the game
                gameOver = new Image(getClass().getResource("/assets/sprites/won.png").toExternalForm());
            }

            // Display the game-over screen
            gameOverScreen();
            this.stop();
        }
    }

    /**
     * Display the game-over screen with score recap and navigation options.
     */
    private void gameOverScreen() {
        Scene playScene = stage.getScene();
        PlayPane playPane = (PlayPane) playScene.getRoot();

        //Create a dark overlay
        Rectangle darkOverlay = new Rectangle(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        darkOverlay.setFill(Color.BLACK);
        darkOverlay.setOpacity(0.75);

        //Set up game-over elements
        ImageView gOver = new ImageView(gameOver);
        ImageView restartBtn = new ImageView(new Image(getClass().getResource("/assets/sprites/restartBtn.png").toExternalForm()));
        ImageView mainMenuBtn = new ImageView(new Image(getClass().getResource("/assets/sprites/sandwichMenu.png").toExternalForm()));

        //Configure buttons and texts
        setUpButtons(restartBtn, mainMenuBtn);

        Text totalScore = new Text(
            String.format("Total Score: %.2f", (scoreboard.getTotalScore() +
                    (scoreboard.getTotalScore() * scoreboard.getHappinessLvl())))
        );
        Text player1Label = new Text("Player 1: " + v1.getScore());
        Text player2Label = new Text("Player 2: " + v2.getScore());
        setUpTexts(totalScore, player1Label, player2Label);

        //Add elements to the play pane
        playPane.getChildren().addAll(darkOverlay, restartBtn, mainMenuBtn, totalScore, player1Label, player2Label, gOver);
    }

    /**
     * Configure the restart and main menu buttons with click and hover effects.
     * Sets up audio cue for button events without interruption of background track
     * @param restartBtn The restart button.
     * @param mainMenuBtn The main menu button.
     */
    private void setUpButtons(ImageView restartBtn, ImageView mainMenuBtn) {
        restartBtn.setPickOnBounds(true);
        mainMenuBtn.setPickOnBounds(true);
        restartBtn.setX(Game.WINDOW_WIDTH / 2 - restartBtn.getBoundsInLocal().getWidth() - 75);
        restartBtn.setY(800);
        mainMenuBtn.setX(Game.WINDOW_WIDTH / 2 + 75);
        mainMenuBtn.setY(800);

        // Click Events
        restartBtn.setOnMouseClicked(event -> {
            Audio.playClip("menuButton", 1.5);
            MenuPane.activeMenuPane.setSelection();
        });
        mainMenuBtn.setOnMouseClicked(event -> {
            Audio.playClip("menuButton", 1.5);
            stage.setScene(MenuPane.activeMenuPane.getScene());
            Audio.playSound("menu", 0.25, true);
        });

        restartBtn.setOnMouseEntered(event -> {
            restartBtn.setScaleX(1.2);
            restartBtn.setScaleY(1.2);
        });
        mainMenuBtn.setOnMouseEntered(event -> {
            mainMenuBtn.setScaleX(1.2);
            mainMenuBtn.setScaleY(1.2);
        });

        restartBtn.setOnMouseExited(event -> {
            restartBtn.setScaleX(1.0);
            restartBtn.setScaleY(1.0);
        });
        mainMenuBtn.setOnMouseExited(event -> {
            mainMenuBtn.setScaleX(1.0);
            mainMenuBtn.setScaleY(1.0);
        });
    }

    /**
     * Configure the text elements displayed on the game-over screen.
     * @param score The total score text.
     * @param p1 Player 1 score text.
     * @param p2 Player 2 score text.
     */
    private void setUpTexts(Text score, Text p1, Text p2) {
        String fontPath = getClass().getResource("/assets/sprites/pixelFont.ttf").toExternalForm();

        score.setX(500);
        score.setY(600);
        p1.setX(345);
        p1.setY(500);
        p2.setX(745);
        p2.setY(500);

        score.setFont(Font.loadFont(fontPath, 30));
        p1.setFont(Font.loadFont(fontPath, 30));
        p2.setFont(Font.loadFont(fontPath, 30));

        score.setFill(Color.GOLD);
        p1.setFill(Color.BLUE);
        p2.setFill(Color.RED);
    }

    /**
     * Timer to generate random orders for households at fixed intervals.
     */
    private void randomOrderTimer() {
        new AnimationTimer() {
            long startTime = System.nanoTime();

            @Override
            public void handle(long l) {
                if(scoreboard.checkIfLost()) {
                    this.stop();
                }
                if(l - startTime >= 10000000000L) { // Generate an order every 10 seconds
                    randomOrder();
                    startTime = l;
                }
            }
        }.start();
    }

    /**
     * Select a random household to place an order.
     */
    private void randomOrder() {
        Random r = new Random();
        ArrayList<Household> houses = map.getHouseList();
        try {
            new AnimationTimer() {
                Household house;

                @Override
                public void handle(long l) {
                    if(scoreboard.checkIfLost()) {
                        this.stop();
                    }
                    house = houses.get(r.nextInt(houses.size()));
                    if(house.getHasActiveOrder()) {
                        return; // Skip if the household already has an active order
                    }

                    house.setActiveOrder();
                    this.stop();
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}