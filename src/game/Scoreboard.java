package game;

import graphics.map.Store;
import graphics.vehicles.Vehicle;

import java.util.HashMap;

public class Scoreboard {
    private int happinessLvl;
    private int totalScore;

    public static final int MAX_HAPPINESS_LVL = 10;

    public Scoreboard() {
        this.totalScore = 0;
        this.happinessLvl = MAX_HAPPINESS_LVL;
    }

    // Methods
    public void updateScore(int score) {
        this.totalScore += score;
    }

    public void reduceHappiness(int val) {
        if(this.happinessLvl <= val) {this.happinessLvl = 0; return;}
        this.happinessLvl -= val;
    }

    public int getHappinessLvl() {
        return this.happinessLvl;
    }
    public int getTotalScore() {
        return this.totalScore;
    }
}
