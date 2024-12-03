package game;

import graphics.map.Store;
import graphics.vehicles.Vehicle;

import java.util.HashMap;

public class Scoreboard {
    private float happinessLvl;
    private int totalScore;

    public static final float MAX_HAPPINESS_LVL = 10F;

    public Scoreboard() {
        this.totalScore = 0;
        this.happinessLvl = MAX_HAPPINESS_LVL;
    }

    // Methods
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
}
