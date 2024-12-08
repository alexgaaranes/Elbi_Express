/*
 *  MUST CONTAIN MAIN GAME LOOP
 */

package game;

import game.panes.PlayPane;
import graphics.map.Household;
import graphics.map.Map;
import graphics.map.Store;
import graphics.vehicles.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
        this.randomOrderTimer(); // Send order every 15 sec TODO: Can be changed to random
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
            } else {
                System.out.println("You Won!");
            }
            // Some score recap logic
            gameOverScreen();
            this.stop();
        }
    }

    private void gameOverScreen(){
        Scene playScene = stage.getScene();
        PlayPane playPane = (PlayPane) playScene.getRoot();
        Rectangle darkOverlay = new Rectangle(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        darkOverlay.setFill(Color.BLACK);
        darkOverlay.setOpacity(0.75);
        playPane.getChildren().add(darkOverlay);
    }

    private void randomOrderTimer(){
        new AnimationTimer() {
            long startTime = System.nanoTime();
            @Override
            public void handle(long l) {
                if(l - startTime >=15000000000L){
                    randomOrder();
                    startTime = l;
                };
            }
        }.start();
    }


    private void randomOrder(){
        Random r = new Random();
        ArrayList<Household> houses = map.getHouseList();
        try{
            new AnimationTimer(){
                Household house;
                @Override
                public void handle(long l) {
                    house = houses.get(r.nextInt(houses.size()));
                    if(house.getHasActiveOrder()){return;}

                    house.setActiveOrder();
                    this.stop();
                }
            }.start();
        } catch(Exception e){e.printStackTrace();}
    }
}
