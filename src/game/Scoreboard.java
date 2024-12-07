package game;

import graphics.map.Store;
import graphics.vehicles.Vehicle;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;

import java.util.HashMap;

public class Scoreboard {
    private float happinessLvl;
    private int totalScore;
    private boolean isGameOver = false;

    public static final float MAX_HAPPINESS_LVL = 10F;

    public Scoreboard() {
        this.totalScore = 0;
        this.happinessLvl = MAX_HAPPINESS_LVL;
        this.checkHappiness();
    }

    // Methods
    private void checkHappiness() {
        new AnimationTimer(){
            @Override
            public void handle(long l) {
                if(happinessLvl <= 0) {
                    isGameOver = true;
                    this.stop();
                }
            }
        }.start();
    }

    public void updateScore(int score) {
        this.totalScore += score;
    }

    public void reduceHappiness(float val) {
        if(this.happinessLvl <= val) {this.happinessLvl = 0; return;}
        this.happinessLvl -= val;
    }

    public float getHappinessLvl() {
        return this.happinessLvl;
    }

    public int getTotalScore() {
        return this.totalScore;
    }

    public boolean checkIfLost(){
        return isGameOver;
    }

}
