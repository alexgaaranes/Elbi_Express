/*
 *  MUST CONTAIN MAIN GAME LOOP
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
    private final Stage stage;
    GraphicsContext gc;
    private Vehicle v1;
    private Vehicle v2;
    private Map map;
    private Scoreboard scoreboard;
    private static Image gameOver= new Image("file:src/assets/sprites/won.png");

    public GameTimer(Stage stage, GraphicsContext gc, Map map, Scoreboard scoreboard) {
        this.stage = stage;
        this.gc = gc;
        this.map = map;
        this.scoreboard = scoreboard;
    }

    public void setUpGame(Vehicle v1, Vehicle v2){
        this.v1 = v1;
        this.v2 = v2;

        // Setup detection for stores
        for(Store store: map.getStoreList()){
            store.trackVehicle(v1, v2);
        }
        // Setup detection for households
        for(Household house: map.getHouseList()){
            house.trackVehicle(v1,v2);
        }

        // Initial Order
        randomOrder();
        this.randomOrderTimer(); // get a random household to take order
    }

    @Override
    public void handle(long l) {
        // Mostly for rendering and game checking only ( Avoid adding slow processes here )
        gc.clearRect(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        this.map.drawMap(gc);
        this.v1.render(this.gc);
        this.v2.render(this.gc);
        this.map.drawHouse(this.gc);

        if(this.scoreboard.checkIfLost()){
            System.out.println("Game Over!");
            if(scoreboard.getHappinessLvl() <= 0){
                System.out.println("You Lost!");
                gameOver= new Image("file:src/assets/sprites/lose.png");
            } else {
                System.out.println("You Won!");
                gameOver= new Image("file:src/assets/sprites/won.png");
            }
            // Some score recap logic
            gameOverScreen();
            this.stop();
        }
    }

    // GAME OVER SETUP
    private void gameOverScreen(){
        Scene playScene = stage.getScene();
        PlayPane playPane = (PlayPane) playScene.getRoot();
        Rectangle darkOverlay = new Rectangle(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        darkOverlay.setFill(Color.BLACK);
        darkOverlay.setOpacity(0.75);
        // Button Setup
        ImageView gOver = new ImageView(gameOver);
        ImageView restartBtn = new ImageView(new Image("file:src/assets/sprites/restartBtn.png"));
        ImageView mainMenuBtn = new ImageView(new Image("file:src/assets/sprites/sandwichMenu.png"));
        setUpButtons(restartBtn, mainMenuBtn);
        // Score text Setup
        Text totalScore = new Text(
        	    String.format("Total Score: %.2f", (scoreboard.getTotalScore() +
                        (scoreboard.getTotalScore() * scoreboard.getHappinessLvl())))
        	);
        Text player1Label = new Text("Player 1: "+ v1.getScore());
        Text player2Label = new Text("Player 2: "+ v2.getScore());
        setUpTexts(totalScore, player1Label, player2Label);


        playPane.getChildren().addAll(darkOverlay, restartBtn, mainMenuBtn, totalScore, player1Label, player2Label, gOver);
    }

    private void setUpButtons(ImageView restartBtn, ImageView mainMenuBtn){
        // Buttons
        restartBtn.setPickOnBounds(true);
        mainMenuBtn.setPickOnBounds(true);
        restartBtn.setX(Game.WINDOW_WIDTH/2 - restartBtn.getBoundsInLocal().getWidth() - 75);
        restartBtn.setY(800);
        mainMenuBtn.setX(Game.WINDOW_WIDTH/2 + 75);
        mainMenuBtn.setY(800);

        // Click Events
        restartBtn.setOnMouseClicked(event -> {
            MenuPane.activeMenuPane.setSelection();
        });
        mainMenuBtn.setOnMouseClicked(event -> {
            stage.setScene(MenuPane.activeMenuPane.getScene());
        });

        // Hover Effects
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

    private void setUpTexts(Text score, Text p1, Text p2){
        String fontPath = "file:src/assets/sprites/pixelFont.ttf";
        score.setX(500);
        score.setY(600);
        p1.setX(745);
        p1.setY(500);
        p2.setX(345);
        p2.setY(500);

        score.setFont(Font.loadFont(fontPath, 30));
        p1.setFont(Font.loadFont(fontPath, 30));
        p2.setFont(Font.loadFont(fontPath, 30));

        score.setFill(Color.GOLD);
        p1.setFill(Color.BLUE);
        p2.setFill(Color.RED);
    }

    // Set order on random household after given interval
    private void randomOrderTimer(){
        new AnimationTimer() {
            long startTime = System.nanoTime();
            @Override
            public void handle(long l) {
                if(scoreboard.checkIfLost()){this.stop();}
                if(l - startTime >=15000000000L){ // order every 15sec
                    randomOrder();
                    startTime = l;
                };
            }
        }.start();
    }

    // Set an order
    private void randomOrder(){
        Random r = new Random();
        ArrayList<Household> houses = map.getHouseList();
        try{
            new AnimationTimer(){
                Household house;
                @Override
                public void handle(long l) {
                    if(scoreboard.checkIfLost()){this.stop();}
                    house = houses.get(r.nextInt(houses.size()));
                    if(house.getHasActiveOrder()){return;} // never stop until there's a household who can order

                    house.setActiveOrder();
                    this.stop();
                }
            }.start();
        } catch(Exception e){e.printStackTrace();}
    }
}
